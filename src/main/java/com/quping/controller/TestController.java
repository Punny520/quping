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
    private UserService userService;
    /**
     * 批量生成用户Token和请求体，保存
     */
    @GetMapping("token/{num}")
    public void generateTokens(@PathVariable("num") Integer num){
        try(FileOutputStream tokenFile = new FileOutputStream("src/main/resources/tokens.csv");
                FileOutputStream urmFile = new FileOutputStream("src/main/resources/urmParam.csv")){
            urmFile.write("ratingId,score".getBytes());
            urmFile.write(System.lineSeparator().getBytes());
            while((num--)>0){
                String phoneNumber = getRandomPhoneNumber();
                //TODO 有问题待修改
                String code = (String) userService.getCodeByPhone(phoneNumber).getData();
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
     * 根据参数生成对应UserRatingMapping
     * @param num 生成个数
     * @param ratingId
     * @param score
     * @param random 是否分数随机
     */
    @GetMapping("/urm_gen")
    public void UserRatingMappingGenerator(
            @RequestParam("num") Integer num,
            @RequestParam("ratingId") Integer ratingId,
            @RequestParam("score") Integer score,
            @RequestParam("random") Boolean random){
        double expectFinalScore = 0;
        int total = num;
        int totalScore = 0;
        try(FileOutputStream urmFile = new FileOutputStream("src/main/resources/urmParam.csv")){
            urmFile.write("ratingId,score".getBytes());
            urmFile.write(System.lineSeparator().getBytes());
            while((num--)>0){
                int tmpScore = random ? RandomUtil.randomInt(1,11) : score;
                totalScore+=tmpScore;
                String param = ratingId+","+tmpScore;
                urmFile.write(param.getBytes());
                if(num>0) urmFile.write(System.lineSeparator().getBytes());
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            expectFinalScore = totalScore/(total*1.0);
            log.info("生成完成!" +
                    "生成个数:{}" +
                    "总评分:{}" +
                    "期望平均评分:{}",total,totalScore,expectFinalScore);
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
        urmDTO.setRatingId(9L);
        Integer score = RandomUtil.randomInt(1,6);
        urmDTO.setScore(score);
        String jsonStr = JSONUtil.toJsonStr(urmDTO);
        log.info("生成随机JSON:{}",jsonStr);
        return jsonStr;
    }
    @Autowired
    private FileService fileService;

    @PutMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        return fileService.upload(file);
    }
}
