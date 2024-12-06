package com.quping.utils;


import cn.hutool.json.JSONUtil;
import com.quping.entity.User;

/**
 * @description:用户会话存储
 * @author: Punny
 * @date: 2024/9/9 21:25
 */
public class UserHolder {
    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();
    public static User getUserSession(){
        return USER_HOLDER.get();
    }

    public static void saveUserSession(User user){
        System.out.println("保存用户Session:\n"+ JSONUtil.toJsonPrettyStr(user));
        USER_HOLDER.set(user);
    }

    public static void remove(){
        USER_HOLDER.remove();
    }
}
