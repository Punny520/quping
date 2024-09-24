package com.quping.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.quping.common.Constants;
import com.quping.common.Result;
import com.quping.dao.mapper.UserRatingMapper;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entry.Rating;
import com.quping.dao.mapper.RatingMapper;
import com.quping.entry.UserRatingMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
    UserRatingMapper userRatingMapper;
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
    public Result<Rating> getById(int id) {
        String key = Constants.RATING_CACHE_PREFIX + id;
        Rating entry = new Rating();
        entry.setId(id);
        return getByCache(key,Rating.class,entry,ratingMapper::getByEntry);
    }

    /**
     * 用户评分
     * @param urmd
     * @return
     */
    @Override
    public Result doRating(UserRatingMappingDTO urmd) {
        Result result = getById(urmd.getRatingId());
        if(result.getData()==null) return Result.fail();
        Rating rating = (Rating) result.getData();
        UserRatingMapping urm = new UserRatingMapping();
        BeanUtil.copyProperties(urmd,urm);
        UserRatingMapping entry = userRatingMapper.getByEntry(urm);
        int res = 0;
        if(entry == null){
            //新增用户评分
            userRatingMapper.insert(urm);
            /**
             * talScore/count = oldScore => talScore = oldScore*count
             * newScore = (talScore+newUScore)/(count+1) => (oldScore*count+newUScore)/(count+1)
             */
            float oldScore = rating.getScore();
            float count = rating.getCount();
            float newUScore = urm.getScore();
            float newScore = (oldScore*count+newUScore)/(count+1);
            rating.setScore(newScore);
            rating.setCount(rating.getCount()+1);
        }else{
            //用户修改评分
            /**
             * talScore/count = oldScore => talScore = oldScore*count
             * newScore = (talScore - oldUScore + newUScore)/count =>
             * newScore = (oldScore*count - oldUScore + newUScore)/count
             */
            float oldScore = rating.getScore();
            float count = rating.getCount();
            float oldUScore = entry.getScore();
            float newUScore = urm.getScore();
            float newScore = (oldScore*count-oldUScore+newUScore)/count;
            rating.setScore(newScore);
            entry.setScore(urm.getScore());
            userRatingMapper.update(entry);
        }
        res = ratingMapper.update(rating);
        return res>0?Result.ok():Result.fail();
    }

    /**
     * 通过缓存获取数据
     * @param key
     * @param clzz
     * @return
     * @param <T>
     */
    private <T> Result<T> getByCache(String key, Class<T> clzz, T entry, Function<T,T> getByEntry){
        String JSON = stringRedisTemplate.opsForValue().get(key);
        if(JSON == null){
            entry = cacheReBuild(key,clzz,entry,getByEntry);
            if(entry == null){
                stringRedisTemplate.opsForValue().set(key,"");
                return Result.fail();
            }else return Result.ok(entry);
        }else if(JSON.equals("")){
            return Result.fail();
        }
        entry = JSONUtil.toBean(JSON,clzz);
        return Result.ok(entry);
    }

    /**
     * 缓存重建
     * @param key
     * @param clzz
     * @return
     * @param <T>
     */
    private synchronized <T> T cacheReBuild(String key, Class<T> clzz,T entry,Function<T,T> getByEntry) {
        String JSON = stringRedisTemplate.opsForValue().get(key);
        if(JSON!=null){
            if(JSON.equals("")) return null;//防止缓存穿透
            return JSONUtil.toBean(JSON,clzz);
        }else {
            entry = getByEntry.apply(entry);
            if(entry == null) stringRedisTemplate.opsForValue().set(key,"");
            else stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonPrettyStr(entry),1,TimeUnit.DAYS);
            return entry;
        }
    }
}
