package com.pingfen.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pingfen.user.repository")
public class PingfenUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(PingfenUserApplication.class, args);
    }

}
