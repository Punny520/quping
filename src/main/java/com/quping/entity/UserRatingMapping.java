package com.quping.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 用户和评分关联表
 * @author: Punny
 * @date: 2024/9/15 0:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingMapping {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long ratingId;
    private Integer score;

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
