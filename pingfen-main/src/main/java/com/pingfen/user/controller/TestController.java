package com.pingfen.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试
 * @author: Punny
 * @date: 2024/7/23 0:43
 */
@RestController
public class TestController {
    @GetMapping
    public String hello(){
        return "hello!";
    }

    @GetMapping("/login")
    public void login(){
        StpUtil.login("123456");
    }

    @GetMapping("/check-login")
    public String checkLogin(){
        return StpUtil.isLogin() ? "YES" : "NO";
    }
}
