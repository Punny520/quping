package com.quping.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quping.dao.mapper.UserRatingMapper;
import com.quping.entry.UserRatingMapping;
import com.quping.service.UserRatingMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/24 14:59
 */
@Service
public class UserRatingMappingServiceImpl implements UserRatingMappingService {
    private final UserRatingMapper userRatingMapper;
    @Autowired
    UserRatingMappingServiceImpl(UserRatingMapper userRatingMapper){
        this.userRatingMapper = userRatingMapper;
    }

    /**
     * @param userRatingMapping
     * @return
     */
    @Override
    public UserRatingMapping getByEntity(UserRatingMapping userRatingMapping) {
        return userRatingMapper.selectOne(new QueryWrapper<>(userRatingMapping));
    }
}
