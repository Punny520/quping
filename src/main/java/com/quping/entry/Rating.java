package com.quping.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer id;
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
    private Integer createBy;

    /**
     * 公共字段
     */
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Boolean deleted;
}
