package com.quping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import com.quping.event.publisher.RatingEventPublisher;
import com.quping.service.FileService;
import com.quping.service.RatingService;
import com.quping.utils.RedisUtil;
import com.quping.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/10 21:34
 */
@Service
@Slf4j
public class RatingServiceImpl implements RatingService {
    private final RatingMapper ratingMapper;
    private final UserRatingMapper userRatingMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final FileService fileService;
    private final RatingEventPublisher ratingEventPublisher;
    @Autowired
    RatingServiceImpl(RatingMapper ratingMapper,
                      UserRatingMapper userRatingMapper,
                      StringRedisTemplate stringRedisTemplate,
                      FileService fileService,
                      RatingEventPublisher ratingEventPublisher){
        this.ratingMapper = ratingMapper;
        this.userRatingMapper = userRatingMapper;
        this.stringRedisTemplate =stringRedisTemplate;
        this.fileService = fileService;
        this.ratingEventPublisher = ratingEventPublisher;
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
        return RedisUtil.getFromCacheById(key, id,Rating.class,ratingMapper);
    }

    /**
     * 用户评分
     * 单机情况：
     * 通过乐观锁更新UserRatingMapping
     * 如果更新成功，修改redis中的评分信息
     * 更新失败，返回"你点的太快了"
     * 在redis中检查评分的标志位，如果为1则直接返回
     * 如果为0或者没有则设置为1,发布一个延迟一秒后发布的事件
     * 监听器收到事件后，将标志位设置为0，并且从redis中获取评分数据保存到数据库中
     * 分布式、微服务情况：
     *
     * @param urmd
     * @return
     */
    @Override
    public Result<Void> doRating(UserRatingMappingDTO urmd) {
        User user = UserHolder.getUserSession();
        if(user == null){
            return Result.failWithMsg("请登录后重试");
        }
        urmd.setUserId(user.getId());
        UserRatingMapping urm = userRatingMapper.selectOne(new QueryWrapper<UserRatingMapping>()
                .eq("user_Id", urmd.getUserId())
                .eq("rating_Id", urmd.getRatingId()));
        if(urm == null){
            urm = new UserRatingMapping();
            urm.setRatingId(urmd.getRatingId());
            urm.setUserId(urmd.getUserId());
            urm.setScore(urmd.getScore());
            try{
                userRatingMapper.insert(urm);
                getAndSet(urm,urmd, false);
            }catch (Exception e){
                log.error(e.getMessage());
                return Result.failWithMsg("你点的太快了");
            }
        }else {
            //乐观锁更新
            Integer oldScore = urm.getScore();
            urm.setScore(urmd.getScore());
            UserRatingMapping queryEntity = new UserRatingMapping();
            queryEntity.setScore(urm.getScore());
            int result = userRatingMapper
                    .update(queryEntity,new UpdateWrapper<UserRatingMapping>()
                            .eq("user_Id", urmd.getUserId())
                            .eq("rating_Id", urmd.getRatingId()));
            if(result == 0){
                return Result.failWithMsg("你点的太快了");
            }
            urm.setScore(oldScore);
            getAndSet(urm, urmd,true);
        }
        String key = Constants.RATING_STATUS + urmd.getRatingId();
        String status = stringRedisTemplate
                .opsForValue()
                .get(key);
        if(status != null && status.equals("1")){
            return Result.ok();
        }
        stringRedisTemplate
                .opsForValue()
                .set(key,"1");
        ratingEventPublisher.publishEventWithDelay(urmd.getRatingId());
        return Result.ok();
    }

    /**
     * 从缓存中获取并修改
     * @param method false insert,true update
     */
    private synchronized void getAndSet(UserRatingMapping urm,UserRatingMappingDTO urmd,boolean method){
        Rating entity = getById(urm.getRatingId());
        if(!method){
            entity.setTotalScore(entity.getTotalScore() + urm.getScore());
            entity.setCount(entity.getCount() + 1);
        }else{
            entity.setTotalScore(entity.getTotalScore() - urm.getScore() + urmd.getScore());
        }
        String key = Constants.RATING_CACHE_PREFIX + urmd.getRatingId();
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonPrettyStr(entity));
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
        rating.setTotalScore(0);
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
