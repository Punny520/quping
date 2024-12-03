package com.quping.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Integer id;
    private Integer userId;
    private Integer ratingId;
    private Integer score;

    /**
     * 公共字段
     */
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Boolean deleted;
}
