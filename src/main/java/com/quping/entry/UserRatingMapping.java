package com.quping.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 用户和评分关联表
 * @author: Punny
 * @date: 2024/9/15 0:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingMapping {
    private Integer id;
    private Integer userId;
    private Integer ratingId;
    private Integer score;
}
