package com.quping.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RatingMapper extends BaseMapper<Rating> {

    Rating getByEntry(Rating rating);
}
