package com.quping.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.quping.common.Result;
import com.quping.dto.UserDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private UserService userService;
    /**
     * 批量生成用户Token和请求体，保存
     */
    @GetMapping("/{num}")
    public void generateTokens(@PathVariable("num") Integer num){
        try(FileOutputStream tokenFile = new FileOutputStream("src/main/resources/tokens.csv");
                FileOutputStream urmFile = new FileOutputStream("src/main/resources/urmParam.csv")){
            urmFile.write("ratingId,score".getBytes());
            urmFile.write(System.lineSeparator().getBytes());
            while((num--)>0){
                String phoneNumber = getRandomPhoneNumber();
                String code = userService.getCode(phoneNumber);
                UserDTO userDTO = new UserDTO();
                userDTO.setCode(code);
                userDTO.setPhoneNumber(phoneNumber);
                Result result = userService.doLogin(userDTO);
                String token = (String) result.getData();
                log.info("电话号码:{}",phoneNumber);
                log.info("生成Token:{}",token);
                tokenFile.write(token.getBytes());
                if(num>0) tokenFile.write(System.lineSeparator().getBytes());//回车
                String parm = 9+","+3;
                urmFile.write(parm.getBytes());
                if(num>0) urmFile.write(System.lineSeparator().getBytes());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取随机13位电话号码
     * @return
     */
    private String getRandomPhoneNumber(){
        //TODO 号码格式
        return RandomUtil.randomNumbers(11);
    }

    /**
     * 生成随机评分请求体
     * @return
     */
    private String randomPostBody(){
        UserRatingMappingDTO urmDTO = new UserRatingMappingDTO();
        urmDTO.setRatingId(9);
        Integer score = RandomUtil.randomInt(1,6);
        urmDTO.setScore(score);
        String jsonStr = JSONUtil.toJsonStr(urmDTO);
        log.info("生成随机JSON:{}",jsonStr);
        return jsonStr;
    }
}
