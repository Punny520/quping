package com.quping.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.quping.common.Constants;
import com.quping.common.Result;
import com.quping.dto.UserDTO;
import com.quping.entry.User;
import com.quping.dao.mapper.UserMapper;
import com.quping.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
     * 用户验登录
     * @param userDTO
     * @return
     */
    @Override
    public Result doLogin(UserDTO userDTO) {
        if(userDTO.getPassword() != null && !userDTO.getPassword().equals("")){
            return doLoginByPassword(userDTO);
        }else return doLoginByCode(userDTO);
    }

    /**
     * 通过验证码注册或登录
     * @param userDTO
     * @return
     */
    private Result doLoginByCode(UserDTO userDTO) {
        String loginKey = Constants.VERIFICATION_CODE_PREFIX + userDTO.getPhoneNumber();
        String code = redisTemplate.opsForValue().get(loginKey);
        if(!userDTO.getCode().equals(code)){
            return Result.failWithMsg("验证码错误");
        }
        User user = userMapper.getUserByPhoneNumber(userDTO.getPhoneNumber());
        if(user == null){
            //创建新用户设置初始值
            user = new User();
            user.setPassword("123456");
            user.setNickName(RandomUtil.randomString(10));
            userMapper.insertUser(user);
        }
        return Result.ok(getUserToken(user));
    }

    /**
     * 存储用户会话信息并返回token
     * @param user
     * @return
     */
    private String getUserToken(User user){
        //TODO 逻辑优化防止相同用户生成不同token等
        String token = UUID.randomUUID().toString(true);
        String userSession = JSONUtil.toJsonPrettyStr(user);
        redisTemplate.opsForValue().set( Constants.USER_SESSION_PREFIX + token,userSession,1,TimeUnit.DAYS);
        return token;
    }

    /**
     * 通过密码登录
     * @param userDTO
     * @return
     */
    private Result doLoginByPassword(UserDTO userDTO) {
        User user = userMapper.getUserByPhoneNumber(userDTO.getPhoneNumber());
        if(user == null) return Result.failWithMsg("账号或密码错误");
        //TODO 密码明文加密
        if(!userDTO.getPassword().equals(user.getPassword())) return Result.failWithMsg("账号或密码错误");
        return Result.ok(getUserToken(user));
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

    /**
     * 返回用户个人信息
     * @return
     */
    @Override
    public Result showProfile() {
        //TODO 暂时先返回用户全部信息
        return Result.ok(UserHolder.getUserSession());
    }
}
