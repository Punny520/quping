package com.quping.utils;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
     * 通过id获取实体，走缓存路线
     * @param key 缓存key
     * @param id 实体id
     * @param entryClass 实例类类型
     * @param baseMapper 缓存重建mapper
     * @return
     * @param <T>
     */
    public static <T> T getFromCacheById(String key, Long id, Class<T> entryClass, BaseMapper<T> baseMapper){
        T entity;
        String entryJson = stringRedisTemplate.opsForValue().get(key);
        //如果缓存中查出来的是空串，则触发防缓存穿透机制，直接返回空
        if(entryJson != null  && entryJson.isEmpty())return null;
        if(entryJson == null){
            //如果为null尝试缓存重建
            entity = cacheRebuild(key,id,entryClass,baseMapper);
        }else{
            //如果不是null,且不是空串，则命中缓存，反序列化
            entity = JSONUtil.toBean(entryJson,entryClass);
        }
        return entity;
    }

    //TODO 后续可采用分布式锁

    /**
     * 缓存重建，同时防止缓存穿透
     * @param key
     * @param id
     * @param entryClass
     * @param baseMapper
     * @return
     * @param <T>
     */
    private static synchronized <T> T cacheRebuild(String key, Long id, Class<T> entryClass, BaseMapper<T> baseMapper) {
        String entryJson = stringRedisTemplate.opsForValue().get(key);
        if(entryJson != null){
            //双重校验如果发现缓存已经重建则直接返回
            if(!entryJson.isEmpty()){
                return JSONUtil.toBean(entryJson,entryClass);
            }else return null;//空串，触发防缓存穿透，直接返回空
        }
        //如果为null则需要去数据库中尝试重建缓存
        T entity = baseMapper.selectById(id);
        if(entity == null){
            //如果数据库中也是空的，则缓存空串，防止缓存穿透
            stringRedisTemplate.opsForValue().set(key,"");
            return null;
        }
        //重建缓存
        stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonPrettyStr(entity),1, TimeUnit.DAYS);
        return entity;
    }
}
