package com.quping.common;

/**
 * @description: 常量类
 * @author: Punny
 * @date: 2024/9/1 17:08
 */
public final class Constants {
    private Constants(){}

    /**
     * 验证码前缀
     */
    public static final String VERIFICATION_CODE_PREFIX = "quping:verificationCode:";
    /**
     * 用户会话信息前缀
     */
    public static final String USER_SESSION_PREFIX = "quping:session:";

    public static final String RATING_CACHE_PREFIX = "quping:cache:rating:";

    public static final String USER_RATING_MAPPING_CACHE_PREFIX = "quping:cache:userRatingMapping:";
    /**
     * 记录用户登入的集合
     */
    public static final String USER_LOGIN_TOKEN = "quping:token:";

    public static final String RATING_STATUS = "quping:status:";
}
