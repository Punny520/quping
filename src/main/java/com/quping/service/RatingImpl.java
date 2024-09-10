package com.quping.service;

import cn.hutool.core.bean.BeanUtil;
import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.entry.Rating;
import com.quping.dao.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/10 21:34
 */
@Service
public class RatingImpl implements RatingService{
    @Autowired
    RatingMapper ratingMapper;

    /**
     * 插入新评分
     * @param ratingDTO
     * @return
     */
    @Override
    public Result insert(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        BeanUtil.copyProperties(ratingDTO,rating);
        ratingMapper.insert(rating);
        return Result.ok();
    }
}
