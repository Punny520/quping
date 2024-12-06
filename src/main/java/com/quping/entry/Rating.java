package com.quping.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 评分类
 * @author: Punny
 * @date: 2024/9/10 21:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 图片链接
     */
    private String imageUrl;
    /**
     * 评分
     */
    private Float score;
    /**
     * 标题
     */
    private String title;
    /**
     * 正文
     */
    private String text;
    /**
     * 评分人数
     */
    private Integer count;

    /**
     * 创建人
     */
    private Long createBy;

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
