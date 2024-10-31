package com.quping.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
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
    @Autowired
    MailService mailService;
    /**
     * 获取登录验证码
     * @param phoneOrMail 传入手机或者邮箱
     */
    @Override
    public Result<?> getCode(String phoneOrMail) {
        String code = RandomUtil.randomNumbers(6);
        //构造key存入Redis中设置5分钟过期
        String loginKey = Constants.VERIFICATION_CODE_PREFIX + phoneOrMail;
        Boolean status = redisTemplate.opsForValue().setIfAbsent(loginKey,code,5, TimeUnit.MINUTES);
        if(Boolean.FALSE.equals(status)){
            return Result.fail("发送验证码失败，请勿频繁请求！");
        }
        //TODO 验证码发送
        if(Validator.isEmail(phoneOrMail)){
            String msg = String.format("验证码为：%s 5分钟内有效",code);
            mailService.sendTo(phoneOrMail,msg);
        }else{
            log.info("验证码为：{} 5分钟内有效",code);
        }
        return Result.ok(phoneOrMail);
    }

    /**
     * 用户验登录
     * @param userDTO
     * @return
     */
    @Override
    public Result<String> doLogin(UserDTO userDTO) {
        if(userDTO.getPassword() != null && !userDTO.getPassword().equals("")){
            return doLoginByPassword(userDTO);
        }else return doLoginByCode(userDTO);
    }

    /**
     * 通过验证码注册或登录
     * @param userDTO
     * @return
     */
    private Result<String> doLoginByCode(UserDTO userDTO) {
        String loginKey = null;
        if(!StrUtil.isBlank(userDTO.getPhoneNumber())){
            loginKey = Constants.VERIFICATION_CODE_PREFIX + userDTO.getPhoneNumber();
        }else loginKey = Constants.VERIFICATION_CODE_PREFIX + userDTO.getEmail();

        String code = redisTemplate.opsForValue().get(loginKey);
        if(!userDTO.getCode().equals(code)){
            return Result.failWithMsg("验证码错误");
        }
        User user = new User();
        BeanUtil.copyProperties(userDTO,user);
        List<User> userList = userMapper.getUser(user);
        if(userList!=null&&userList.size()>0){
            return Result.ok(getUserToken(userList.get(0)));
        }
        //注册用户，设置默认值
        user.setPassword("123456");
        user.setNickName(RandomUtil.randomString(10));
        userMapper.insertUser(user);
        return Result.ok(getUserToken(user));
    }

    /**
     * 存储用户会话信息并返回token
     * 
     * @param user
     * @return
     */
    private String getUserToken(User user){
        String token = null;
        String idTokenKey = Constants.USER_LOGIN_TOKEN+user.getId();
        Boolean keyExists = redisTemplate.hasKey(idTokenKey);
        if(Boolean.TRUE.equals(keyExists)){
            //表示用户之前登入过，判断该token是否已经过期，没过期就返回，防止用户一直登录一直生成新的token
            token = redisTemplate.opsForValue().get(idTokenKey);
            Boolean tokenExists = redisTemplate.hasKey(Constants.USER_SESSION_PREFIX + token);
            if(Boolean.TRUE.equals(tokenExists)){
                return token;
            }
        }
        //生成新token
        token = UUID.randomUUID().toString(true);
        String userSession = JSONUtil.toJsonPrettyStr(user);
        redisTemplate.opsForValue().set(idTokenKey,token,1,TimeUnit.DAYS);
        redisTemplate.opsForValue().set( Constants.USER_SESSION_PREFIX + token,userSession,1,TimeUnit.DAYS);
        return token;
    }

    /**
     * 通过密码登录
     * @param userDTO
     * @return
     */
    private Result<String> doLoginByPassword(UserDTO userDTO) {
        User user = new User();
        BeanUtil.copyProperties(userDTO,user);
        List<User> userList = userMapper.getUser(user);
        if(userList==null||userList.size()==0){
            return Result.failWithMsg("账号或密码错误");
        }
        return Result.ok(getUserToken(userList.get(0)));
    }

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    @Override
    public Result<?> addUser(UserDTO userDTO) {
        User user = new User();
        BeanUtil.copyProperties(userDTO,user);
        int result = userMapper.insertUser(user);
        return result != 1?Result.failWithMsg("添加用户失败"):Result.ok();
    }

    /**
     * 返回用户个人信息
     * @return
     */
    @Override
    public Result<User> showProfile() {
        //TODO 暂时先返回用户全部信息
        return Result.ok(UserHolder.getUserSession());
    }

    /**
     * 登出
     * @return
     */
    @Override
    public Result<Void> loginOut() {
        User user = UserHolder.getUserSession();
        String idTokenKey = Constants.USER_LOGIN_TOKEN+user.getId();
        Boolean keyExists = redisTemplate.hasKey(idTokenKey);
        if(Boolean.TRUE.equals(keyExists)){
            String tokenKey = Constants.USER_SESSION_PREFIX + redisTemplate.opsForValue().get(idTokenKey);
            redisTemplate.delete(tokenKey);
        }
        redisTemplate.delete(idTokenKey);
        return Result.ok();
    }
}
