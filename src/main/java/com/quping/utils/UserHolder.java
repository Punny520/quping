package com.quping.utils;


import com.quping.entry.User;

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
        USER_HOLDER.set(user);
    }

    public static void remove(){
        USER_HOLDER.remove();
    }
}
