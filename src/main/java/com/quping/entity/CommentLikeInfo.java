package com.quping.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName comment_like_info
 */
@TableName(value ="comment_like_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeInfo implements Serializable {
    @TableId(type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Boolean deleted;
}