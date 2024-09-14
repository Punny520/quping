package com.quping.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.quping.common.Constants;
import com.quping.common.Result;
import com.quping.dto.RatingDTO;
import com.quping.entry.Rating;
import com.quping.dao.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/10 21:34
 */
@Service
public class RatingImpl implements RatingService{
    @Autowired
    RatingMapper ratingMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
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

    /**
     * 根据id获取评分
     * @param id
     * @return
     */
    @Override
    public Result getById(int id) {
        //TODO 如果涉及到缓存删除，需要修改缓存重建逻辑，防止高并发情况缓存错误的数据
        String key = Constants.RATING_CACHE_PREFIX + id;
        String ratingJSON = stringRedisTemplate.opsForValue().get(key);
        //重建缓存防止并防止缓存穿透
        if(ratingJSON == null){
            Rating rating = ratingMapper.getById(id);
            ratingJSON = (rating == null ? "" : JSONUtil.toJsonPrettyStr(rating));
            stringRedisTemplate.opsForValue().set(key,ratingJSON,1, TimeUnit.DAYS);
            return ratingJSON.equals("") ? Result.fail() : Result.ok(rating);
        }
        return ratingJSON.equals("")?Result.fail():Result.ok(JSONUtil.toBean(ratingJSON,Rating.class));
    }
}
