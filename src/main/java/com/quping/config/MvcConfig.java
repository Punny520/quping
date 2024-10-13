package com.quping.config;

import com.quping.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: MVC配置类
 * @author: Punny
 * @date: 2024/9/9 21:56
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册token解析拦截器
        registry.addInterceptor(new TokenInterceptor(stringRedisTemplate))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/getCode",
                        "/doLogin",
                        "/test/**");
    }
}
