package com.quping.utils;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @description: Redis相关工具类
 * @author: Punny
 * @date: 2024/9/24 13:45
 */
@Component
public class RedisUtil {
    private static StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisUtil(StringRedisTemplate stringRedisTemplate){
        RedisUtil.stringRedisTemplate = stringRedisTemplate;
    }
    /**
     * 通过缓存获取数据
     * @param key
     * @param clzz
     * @return
     * @param <T>
     */
    public static <T> T getByCache(String key, Class<T> clzz, T entry, Function<T,T> getByEntry){
        String JSON = stringRedisTemplate.opsForValue().get(key);
        if(JSON == null){
            entry = cacheReBuild(key,clzz,entry,getByEntry);
            if(entry == null){
                stringRedisTemplate.opsForValue().set(key,"");
                return null;
            }else return entry;
        }else if(JSON.isEmpty()){
            return null;
        }
        entry = JSONUtil.toBean(JSON,clzz);
        return entry;
    }

    /**
     * 缓存重建
     * @param key
     * @param clzz
     * @return
     * @param <T>
     */
    private static synchronized <T> T cacheReBuild(String key, Class<T> clzz,T entry,Function<T,T> getByEntry) {
        //TODO 使用Redisson分布式锁
        String JSON = stringRedisTemplate.opsForValue().get(key);
        if(JSON!=null){
            if(JSON.isEmpty()) return null;//防止缓存穿透
            return JSONUtil.toBean(JSON,clzz);
        }else {
            entry = getByEntry.apply(entry);
            if(entry == null) stringRedisTemplate.opsForValue().set(key,"");
            else stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonPrettyStr(entry),1, TimeUnit.DAYS);
            return entry;
        }
    }
}
