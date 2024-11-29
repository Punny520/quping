package com.quping.dao.mapper;

import com.quping.common.PageInfo;
import com.quping.entry.Rating;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/10 21:38
 */
@Mapper
public interface RatingMapper {
    int insert(Rating rating);

    Rating getById(int id);

    int update(Rating rating);

    Rating getByEntry(Rating rating);

    List<Rating> page(PageInfo pageInfo);

    Integer count();
}
