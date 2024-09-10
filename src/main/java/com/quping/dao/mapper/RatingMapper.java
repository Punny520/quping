package com.quping.dao.mapper;

import com.quping.entry.Rating;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/10 21:38
 */
@Mapper
public interface RatingMapper {
    int insert(Rating rating);
}
