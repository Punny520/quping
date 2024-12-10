package com.quping;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.quping.dao.mapper.RatingMapper;
import com.quping.dao.mapper.UserMapper;
import com.quping.entity.Rating;
import com.quping.entity.User;
import com.quping.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.HashMap;
@SpringBootTest
@Slf4j
public class DataGenerator {
    @Autowired
    private RatingMapper ratingMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    /**
     * 生成用户评分的数据
     * 包括token和请求体
     */
    @SneakyThrows
    @Test
    void doRatingData(){
        int userCount = 100;
        Long ratingId = null;
        int totalRating = 0;
        //创建一个测试评分
        log.info("创建测试用评分...");
        Rating rating = new Rating();
        rating.setTitle("测试用评分");
        ratingMapper.insert(rating);
        log.info("创建完毕,评分id为:{}",rating.getId());
        ratingId = rating.getId();
        log.info("开始生成{}个用户token与随机请求体",userCount);
        Method getUserToken = userService.getClass().getDeclaredMethod("getUserToken", User.class);
        getUserToken.setAccessible(true);
        try(FileOutputStream fos = new FileOutputStream("src/main/resources/doRatingData.csv");
            FileOutputStream tokenFos = new FileOutputStream("src/main/resources/token.csv")){
            fos.write("token,requestBody\n".getBytes());
            for(int i=1;i<=userCount;i++){
                User user = new User();
                user.setNickName(String.format("test-%s", RandomUtil.randomString(5)));
                user.setPassword("111111");
                user.setFirstLogin(false);
                userMapper.insert(user);
                String token = (String) getUserToken.invoke(userService, user);
                tokenFos.write((token+"\n").getBytes());
                int score = RandomUtil.randomInt(1, 6);//[1,6)
                HashMap<String, String> map = new HashMap<>();
                map.put("ratingId", ratingId.toString());
                map.put("score", String.valueOf(score));
                String requestBody = JSONUtil.toJsonStr(map);
                fos.write(String.format("%s %s\n",token,requestBody).getBytes());
                totalRating+=score;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.info("生成完毕,总评分{},评分人数{},期望结果(保留2位小数){}",
                totalRating
                ,userCount
                ,Math.round((double) totalRating / userCount * 100) / 100.0);
    }

    @Test
    void changeRequestBody(){
        String ratingId = "1";
        int totalRating = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/token.csv"));
            FileOutputStream fos = new FileOutputStream("src/main/resources/doRatingData.csv")){
            String line = null;
            fos.write("token,requestBody\n".getBytes());
            while((line = br.readLine())!=null){
                int score = RandomUtil.randomInt(1, 6);//[1,6)
                HashMap<String, String> map = new HashMap<>();
                map.put("ratingId", ratingId);
                map.put("score", String.valueOf(score));
                String requestBody = JSONUtil.toJsonStr(map);
                fos.write(String.format("%s %s\n",line,requestBody).getBytes());
                totalRating+=score;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.info("totalRating:{}",totalRating);
    }
}