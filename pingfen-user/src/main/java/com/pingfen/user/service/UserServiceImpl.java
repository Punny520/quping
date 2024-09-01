package com.pingfen.user.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.pingfen.user.common.Constants;
import com.pingfen.user.common.Result;
import com.pingfen.user.dto.UserDTO;
import com.pingfen.user.entry.User;
import com.pingfen.user.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Punny
 * @date: 2024/8/25 23:32
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;
    /**
     * 获取登录验证码
     * @param phoneNumber
     */
    @Override
    public void getCode(String phoneNumber) {
        String code = RandomUtil.randomNumbers(6);
        log.info("验证码为：{} 5分钟内有效",code);
        //构造key存入Redis中设置5分钟过期
        String loginKey = Constants.VERIFICATION_CODE_PREFIX + phoneNumber;
        redisTemplate.opsForValue().set(loginKey,code,5, TimeUnit.MINUTES);
    }

    /**
     * 用户验证码登录
     * @param userDTO
     * @return
     */
    @Override
    public Result doLogin(UserDTO userDTO) {
        String loginKey = Constants.VERIFICATION_CODE_PREFIX + userDTO.getPhoneNumber();
        String code = redisTemplate.opsForValue().get(loginKey);
        if(!userDTO.getCode().equals(code)){
            return Result.failWithMsg("验证码错误");
        }
        User user = new User();
        user.setPhoneNumber(userDTO.getPhoneNumber());
        List<User> userList = userMapper.getUser(user);
        if(userList == null || userList.isEmpty()){
            user.setPassword("123456");
            user.setNickName(RandomUtil.randomString(10));
            userMapper.insertUser(user);
        }else user = userList.get(0);
        String token = UUID.randomUUID().toString(true);
        String userSession = JSONUtil.toJsonPrettyStr(user);
        redisTemplate.opsForValue().set( Constants.USER_SESSION_PREFIX + token,userSession,1,TimeUnit.DAYS);
        return Result.ok(token);
    }

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    @Override
    public Result addUser(UserDTO userDTO) {
        User user = new User();
        user.setNickName(userDTO.getNickName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        int result = userMapper.insertUser(user);
        return result != 1?Result.failWithMsg("添加用户失败"):Result.ok();
    }
}
