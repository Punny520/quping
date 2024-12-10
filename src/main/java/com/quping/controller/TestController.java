package com.quping.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quping.common.Result;
import com.quping.dao.mapper.UserMapper;
import com.quping.dto.UserDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entity.User;
import com.quping.service.FileService;
import com.quping.service.UserService;
import com.quping.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @description: 测试
 * @author: Punny
 * @date: 2024/7/23 0:43
 */
@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

    @PutMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        return fileService.upload(file);
    }

    /**
     * 方便测试用，快速注册一个用户生成一个token
     */
    @SneakyThrows
    @GetMapping("/root")
    public String root(){
        User root = new User();
        root.setNickName("root");
        root.setFirstLogin(false);
        root.setPassword("111111");
        root.setEmail("root@quping.com");
        root.setPhoneNumber("18070403876");
        User user = userMapper
                .selectOne(new QueryWrapper<User>()
                        .eq("phone_number", root.getPhoneNumber()));
        if(user != null){
            root.setId(user.getId());
            userMapper.updateById(root);
        }else{
            userMapper.insert(root);
        }
        Method getUserToken = userService
                .getClass()
                .getDeclaredMethod("getUserToken", User.class);
        getUserToken.setAccessible(true);
        String token = (String) getUserToken.invoke(userService, root);
        return "phone:18070403876,password:111111,token:"+token;
    }
}
