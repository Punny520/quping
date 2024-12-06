package com.quping.utils;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.quping.common.Constants;
import com.quping.dao.mapper.UserMapper;
import com.quping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:用户会话存储
 * @author: Punny
 * @date: 2024/9/9 21:25
 */
@Component
public class UserHolder {
    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();
    private static StringRedisTemplate stringRedisTemplate;
    private static UserMapper userMapper;
    @Autowired
    public UserHolder(StringRedisTemplate stringRedisTemplate,
                      UserMapper userMapper) {
        UserHolder.stringRedisTemplate = stringRedisTemplate;
        UserHolder.userMapper = userMapper;
    }
    public static User getUserSession(){
        return USER_HOLDER.get();
    }

    public static void saveUserSession(User user){
        System.out.println("保存用户Session:\n"+ JSONUtil.toJsonPrettyStr(user));
        USER_HOLDER.set(user);
    }

    /**
     * 删除redis中的session，重新从数据库中获取
     * @param id 用户id
     */
    public static void updateById(Long id){
        String token = stringRedisTemplate
                .opsForValue()
                .get(Constants.USER_LOGIN_TOKEN+id);
        stringRedisTemplate
                .opsForValue()
                .set(Constants.USER_SESSION_PREFIX+token
                        ,JSONUtil.toJsonPrettyStr(userMapper.selectById(id)));
    }

    public static void remove(){
        USER_HOLDER.remove();
    }
}
