package com.quping.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName comment_like_info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeInfoDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long commentId;

    /**
     * 点赞状态，0未点赞，1已经点赞
     */
    private Integer status;

    /**
     * 公共字段
     */
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean deleted;
}