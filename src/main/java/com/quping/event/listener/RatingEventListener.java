package com.quping.event.listener;

import cn.hutool.json.JSONUtil;
import com.quping.common.Constants;
import com.quping.dao.mapper.RatingMapper;
import com.quping.entity.Rating;
import com.quping.event.RatingEvent;
import com.quping.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class RatingEventListener {
    private final StringRedisTemplate stringRedisTemplate;
    private final RatingMapper ratingMapper;
    @Autowired
    public RatingEventListener(StringRedisTemplate stringRedisTemplate,
                               RatingMapper ratingMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.ratingMapper = ratingMapper;

    }
    @EventListener
    public void handleEvent(RatingEvent event) {
        log.info("[{}]:监听到事件消息...", LocalDateTime.now());
        String statusKey = Constants.RATING_STATUS + event.getRatingId();
        String cacheKey = Constants.RATING_CACHE_PREFIX + event.getRatingId();
        stringRedisTemplate.opsForValue().set(statusKey,"0");
        String ratingJson = stringRedisTemplate.opsForValue().get(cacheKey);
        Rating entity = JSONUtil.toBean(ratingJson, Rating.class);
        ratingMapper.updateById(entity);
        log.info("更新成功");
    }
}
