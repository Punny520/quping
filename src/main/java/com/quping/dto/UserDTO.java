package com.quping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 用户数据传输类
 * @author: Punny
 * @date: 2024/8/25 22:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 头像url
     */
    private String avatarUrl;
    /**
     * 主页描述消息
     */
    private String description;
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
    /**
     * 验证码
     */
    private String code;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像文件
     */
    private MultipartFile avatarFile;
}
