package com.pingfen.user.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户实体类
 * @author: Punny
 * @date: 2024/8/25 22:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * id
     */
    private Integer id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 密码
     */
    private String password;

}
