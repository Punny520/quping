package com.quping.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞数量
     */
    private Integer likeCount;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 评分的id
     */
    private Long ratingId;
    /**
     * 用户是否点赞
     */
    private Boolean liked;
    /**
     * 公共字段
     */
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean deleted;
}
