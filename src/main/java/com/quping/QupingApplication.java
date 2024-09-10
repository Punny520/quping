package com.quping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: SpringBoot启动类
 * @author: Punny
 * @date: 2024/9/10 22:23
 */
@SpringBootApplication
@MapperScan("com.quping.dao.mapper")
public class QupingApplication {
    public static void main(String[] args) {
        SpringApplication.run(QupingApplication.class,args);
    }
}
