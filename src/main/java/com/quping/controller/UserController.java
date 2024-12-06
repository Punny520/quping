package com.quping.controller;

import com.quping.common.Result;
import com.quping.dto.UserDTO;
import com.quping.entity.User;
import com.quping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: UserController
 * @author: Punny
 * @date: 2024/8/25 23:09
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }
    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/getCodeByPhone")
    public Result<?> getCodeByPhone(@RequestParam("phoneNumber") String phone){
        return userService.getCodeByPhone(phone);
    }

    /**
     * 获取邮箱验证码
     * @param email
     * @return
     */
    @GetMapping("/getCodeByEmail")
    public Result<?> getCodeByEmail(@RequestParam("email") String email){
        return userService.getCodeByEmail(email);
    }

    /**
     * 用户验证码登录
     * @param userDTO
     * @return
     */
    @PostMapping("/doLogin")
    public Result<String> doLogin(@RequestBody UserDTO userDTO){
        //TODO 参数验证
        return userService.doLogin(userDTO);
    }

    /**
     * 添加新用户
     * @param userDTO
     * @return
     */
    @PostMapping("/add")
    public Result<?> addUser(@RequestBody UserDTO userDTO){
        return userService.addUser(userDTO);
    }

    /**
     * 查看个人资料
     * @return
     */
    @GetMapping("/me")
    public Result<User> me(){
        return userService.showProfile();
    }

    @GetMapping("/loginOut")
    public Result<Void> loginOut(){
        return userService.loginOut();
    }
}
