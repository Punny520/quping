package com.quping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 评分类DTO
 * @author: Punny
 * @date: 2024/9/10 21:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 图片链接
     */
    private String imageUrl;
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
     * 用户打分
     */
    private Integer myScore;

    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 总评分
     */
    private Integer totalScore;
    /**
     * 图片文件
     */
    private MultipartFile image;
}
