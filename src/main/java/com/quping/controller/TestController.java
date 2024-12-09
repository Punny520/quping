package com.quping.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.quping.common.Result;
import com.quping.dto.UserDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.service.FileService;
import com.quping.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

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

    @PutMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        return fileService.upload(file);
    }
}
