package com.quping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/15 0:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingMappingDTO {
    private Integer id;
    private Integer userId;
    private Integer ratingId;
    private Integer score;
}
