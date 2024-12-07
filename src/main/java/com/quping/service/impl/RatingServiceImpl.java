package com.quping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quping.common.Constants;
import com.quping.common.PageInfo;
import com.quping.common.PageResult;
import com.quping.common.Result;
import com.quping.dao.mapper.UserRatingMapper;
import com.quping.dto.RatingDTO;
import com.quping.dto.UserRatingMappingDTO;
import com.quping.entity.Rating;
import com.quping.dao.mapper.RatingMapper;
import com.quping.entity.User;
import com.quping.entity.UserRatingMapping;
import com.quping.service.FileService;
import com.quping.service.RatingService;
import com.quping.utils.RedisUtil;
import com.quping.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/10 21:34
 */
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingMapper ratingMapper;
    private final UserRatingMapper userRatingMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final FileService fileService;
    @Autowired
    RatingServiceImpl(RatingMapper ratingMapper,
                      UserRatingMapper userRatingMapper,
                      StringRedisTemplate stringRedisTemplate,
                      FileService fileService){
        this.ratingMapper = ratingMapper;
        this.userRatingMapper = userRatingMapper;
        this.stringRedisTemplate =stringRedisTemplate;
        this.fileService = fileService;
    }
    /**
     * 插入新评分
     * @param ratingDTO
     * @return
     */
    @Override
    public Result<Void> insert(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        BeanUtil.copyProperties(ratingDTO,rating);
        int res = ratingMapper.insert(rating);
        return res == 0 ? Result.fail() : Result.ok();
    }

    /**
     * 根据id获取评分，走缓存逻辑
     * @param id
     * @return
     */
    @Override
    public Rating getById(long id) {
        String key = Constants.RATING_CACHE_PREFIX + id;
        return RedisUtil.getFromCacheById(key, (long) id,Rating.class,ratingMapper);
    }

    /**
     * 用户评分
     * @param urmd
     * @return
     */
    @Override
    public synchronized Result<Void> doRating(UserRatingMappingDTO urmd) {
        User user = UserHolder.getUserSession();
        if(user == null) return Result.failWithMsg("请登录");
        urmd.setUserId(user.getId());
        Rating rating = getById(urmd.getRatingId());
        if(rating == null) return Result.failWithMsg("数据不存在");
        UserRatingMapping entity = userRatingMapper.selectOne(new QueryWrapper<UserRatingMapping>()
                .eq("user_id",urmd.getUserId())
                .eq("rating_id",urmd.getRatingId()));
        int res = 0;
        if(entity == null){
            //新增用户评分
            entity = new UserRatingMapping();
            BeanUtil.copyProperties(urmd,entity);
            userRatingMapper.insert(entity);
            /*
             * talScore/count = oldScore => talScore = oldScore*count
             * newScore = (talScore+newUScore)/(count+1) => (oldScore*count+newUScore)/(count+1)
             */
            float oldScore = rating.getScore();
            float count = rating.getCount();
            float newUScore = urmd.getScore();
            float newScore = (oldScore*count+newUScore)/(count+1);
            rating.setScore(newScore);
            rating.setCount(rating.getCount()+1);
        }else{
            //用户修改评分
            /*
             * talScore/count = oldScore => talScore = oldScore*count
             * newScore = (talScore - oldUScore + newUScore)/count =>
             * newScore = (oldScore*count - oldUScore + newUScore)/count
             */
            float oldScore = rating.getScore();
            float count = rating.getCount();
            float oldUScore = entity.getScore();
            float newUScore = urmd.getScore();
            float newScore = (oldScore*count-oldUScore+newUScore)/count;
            rating.setScore(newScore);
            entity.setScore(urmd.getScore());
            userRatingMapper.updateById(entity);
        }
        res = ratingMapper.updateById(rating);//先更新数据库后删除缓存
        stringRedisTemplate.delete(Constants.RATING_CACHE_PREFIX+rating.getId());
        return res>0?Result.ok():Result.fail();
    }

    /**
     * 查看用户对应的评分
     * @param userId
     * @param ratingId
     * @return
     */
    @Override
    public UserRatingMapping getUserRating(Long userId, Long ratingId) {
        UserRatingMapping userRatingMapping = new UserRatingMapping();
        userRatingMapping.setUserId(userId);
        userRatingMapping.setRatingId(ratingId);
        return userRatingMapper.getByentity(userRatingMapping);
    }

    /**
     * 用户创建新评分
     *
     * @param ratingDTO
     * @return
     */
    @Override
    public Result<Void> create(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        BeanUtil.copyProperties(ratingDTO,rating);
        User user = UserHolder.getUserSession();
        rating.setCreateBy(user.getId());
        rating.setImageUrl(fileService.upload(ratingDTO.getImage()));
        rating.setScore(0f);
        rating.setCount(0);
        return ratingMapper.insert(rating) > 0 ? Result.ok():Result.fail();
    }
    /**
     * 根据id获取评分详情，以及当前用户的评分
     * 走缓存路线
     * @param id
     * @return
     */
    @Override
    public Result<RatingDTO> showById(Long id) {
        Rating rating = getById(id);
        if(rating == null) return Result.failWithMsg("数据不存在");
        RatingDTO ratingDTO = new RatingDTO();
        BeanUtil.copyProperties(rating,ratingDTO);
        User user = UserHolder.getUserSession();
        if(user != null){
            UserRatingMapping userRatingMapping = userRatingMapper
                    .selectOne(new QueryWrapper<UserRatingMapping>()
                            .eq("user_id", user.getId())
                            .eq("rating_id", rating.getId()));
            if(userRatingMapping != null){
                ratingDTO.setMyScore(userRatingMapping.getScore());
            }
        }
        return Result.ok(ratingDTO);
    }

    @Override
    public Result<PageResult<RatingDTO>> search(PageInfo pageInfo) {
        Page<Rating> page = new Page<>(pageInfo.getPageNumber(),pageInfo.getPageSize());
        QueryWrapper<Rating> wrapper = new QueryWrapper<>();
        wrapper.like("title",pageInfo.getCondition());
        Long total = ratingMapper.selectCount(wrapper);
        PageResult<RatingDTO> pageResult = new PageResult<>();
        if(total > 0){
            pageResult.setTotal(total);
            List<RatingDTO> ratingDTOList = ratingMapper.selectPage(page, wrapper)
                    .getRecords()
                    .stream()
                    .map(e -> {
                        RatingDTO ratingDTO = new RatingDTO();
                        BeanUtil.copyProperties(e, ratingDTO);
                        return ratingDTO;
                    })
                    .collect(Collectors.toList());
            pageResult.setDataList(ratingDTOList);
        }
        return Result.ok(pageResult);
    }
}
