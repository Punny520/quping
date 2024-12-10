package com.quping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quping.common.Constants;
import com.quping.common.Result;
import com.quping.dto.UserDTO;
import com.quping.entity.User;
import com.quping.dao.mapper.UserMapper;
import com.quping.service.FileService;
import com.quping.service.MailService;
import com.quping.service.UserService;
import com.quping.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Punny
 * @date: 2024/8/25 23:32
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final StringRedisTemplate redisTemplate;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final FileService fileService;
    @Autowired
    public UserServiceImpl(StringRedisTemplate redisTemplate,
                           UserMapper userMapper,
                           MailService mailService,
                           FileService fileService) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.fileService = fileService;
    }

    /**
     * 用户验登录
     * @param userDTO
     * @return
     */
    @Override
    public Result<String> doLogin(UserDTO userDTO) {
        if(!StrUtil.isEmpty(userDTO.getPassword())){
            return doLoginByPassword(userDTO);
        }else return doLoginByCode(userDTO);
    }

    /**
     * 通过验证码注册或登录
     * @param userDTO
     * @return
     */
    private Result<String> doLoginByCode(UserDTO userDTO) {
        String loginKey;
        if(!StrUtil.isBlank(userDTO.getPhoneNumber())){
            loginKey = Constants.VERIFICATION_CODE_PREFIX + userDTO.getPhoneNumber();
        }else loginKey = Constants.VERIFICATION_CODE_PREFIX + userDTO.getEmail();

        String code = redisTemplate.opsForValue().get(loginKey);
        if(!userDTO.getCode().equals(code)){
            return Result.failWithMsg("验证码错误或已过期");
        }
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("phone_number",userDTO.getPhoneNumber());
        User user = userMapper.selectOne(userWrapper);
        if(user!=null){
            return Result.ok(getUserToken(user));
        }else{
            //创建新用户设置默认值
            User newUser = new User();
            BeanUtil.copyProperties(userDTO,newUser);
            newUser.setPassword("123456");
            newUser.setNickName(RandomUtil.randomString(10));
            newUser.setFirstLogin(true);
            userMapper.insert(newUser);
            return Result.ok(getUserToken(newUser));
        }
    }

    /**
     * 存储用户会话信息并返回token
     * 
     * @param user
     * @return
     */
    private String getUserToken(User user){
        String token;
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
        String value = redisTemplate.opsForValue().get(idTokenKey);
        if(value!=null&&value.equals(token)){
            log.info("插入token成功");
        }else{
            log.info("插token入失败");
            throw new RuntimeException();
        }
        redisTemplate.opsForValue().set( Constants.USER_SESSION_PREFIX + token,userSession,1,TimeUnit.DAYS);
        String session = redisTemplate.opsForValue().get(Constants.USER_SESSION_PREFIX + token);
        if(session!=null&&session.equals(userSession)){
            log.info("插入session成功");
        }else{
            log.info("插入session失败");
            throw new RuntimeException();
        }
        return token;
    }

    /**
     * 通过密码登录
     * @param userDTO
     * @return
     */
    private Result<String> doLoginByPassword(UserDTO userDTO) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("phone_number",userDTO.getPhoneNumber())
                .eq("password",userDTO.getPassword()));
        if(user == null){
            return Result.failWithMsg("账号或密码错误");
        }
        return Result.ok(getUserToken(user));
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
        int result = userMapper.insert(user);
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

    @Override
    public Result<?> getCodeByPhone(String phone) {
        String code = RandomUtil.randomNumbers(6);
        Boolean status = buildKey(phone,code,1L,TimeUnit.MINUTES);
        if(Boolean.FALSE.equals(status)){
            return Result.fail("发送验证码失败，请勿频繁请求！");
        }
        //TODO 验证码发送
        log.info("验证码为：{} 1分钟内有效",code);
        return Result.ok(phone);
    }

    @Override
    public Result<?> getCodeByEmail(String email) {
        String code = RandomUtil.randomNumbers(6);
        Boolean status = buildKey(email,code,1L,TimeUnit.MINUTES);
        if(Boolean.FALSE.equals(status)){
            return Result.fail("发送验证码失败，请勿频繁请求！");
        }
        if(Validator.isEmail(email)){
            String msg = String.format("验证码为：%s 1分钟内有效",code);
            mailService.sendTo(email,msg);
        }
        log.info("From email: 验证码为：{} 1分钟内有效",code);
        return Result.ok(email);
    }

    /**
     * 检查用户是否第一次登录
     * @return
     */
    @Override
    public Result<Boolean> checkIfFirstLogin() {
        User user = UserHolder.getUserSession();
        if(user == null) return Result.ok(Boolean.FALSE);
        return Result.ok(user.getFirstLogin());
    }

    @Override
    public Result<?> firstSetting(UserDTO userDTO) {
        User user = UserHolder.getUserSession();
        if(user == null) return Result.fail();
        User entity = userMapper.selectById(user.getId());
        entity.setFirstLogin(false);
        entity.setNickName(userDTO.getNickName());
        entity.setPassword(userDTO.getPassword());
        userMapper.updateById(entity);
        UserHolder.updateById(entity.getId());
        return Result.ok();
    }

    @Override
    public Result<String> uploadAvatar(MultipartFile file) {
        User user = UserHolder.getUserSession();
        if(user == null) return Result.failWithMsg("请登录");
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setAvatarUrl(fileService.upload(file));
        userMapper.updateById(updateUser);
        return Result.ok();
    }

    /**
     * 构造登录验证码key方法
     * @param key
     * @param time
     * @param unit
     * @return
     */
    private Boolean buildKey(String key,String code,Long time,TimeUnit unit){
        String loginKey = Constants.VERIFICATION_CODE_PREFIX + key;
        return redisTemplate.opsForValue().setIfAbsent(loginKey,code,time, unit);
    }
}
