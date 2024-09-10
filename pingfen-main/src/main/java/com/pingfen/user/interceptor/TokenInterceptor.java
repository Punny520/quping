package com.pingfen.user.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.pingfen.user.common.Constants;
import com.pingfen.user.entry.User;
import com.pingfen.user.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 判断用户凭证，拦截Token并介解析
 * @author: Punny
 * @date: 2024/9/9 21:22
 */
public class TokenInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;
    public TokenInterceptor(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = UserHolder.getUserSession();
        if(user != null) return true;
        String token = request.getHeader("Token");
        if (StrUtil.isBlank(token)){
            response.setStatus(401);
            return false;
        }
        String userJSON = stringRedisTemplate.opsForValue().get(Constants.USER_SESSION_PREFIX + token);
        if(StrUtil.isBlank(userJSON)){
            response.setStatus(401);
            return false;
        }
        user = JSONUtil.toBean(userJSON,User.class);
        UserHolder.saveUserSession(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
