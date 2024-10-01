package com.quping.service;

import com.quping.common.Constants;
import com.quping.common.Result;
import com.quping.dao.mapper.UserRatingMapper;
import com.quping.entry.UserRatingMapping;
import com.quping.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Punny
 * @date: 2024/9/24 14:59
 */
@Service
public class UserRatingMapperServiceImpl implements UserRatingMapperService{
    private UserRatingMapper userRatingMapper;
    @Autowired
    UserRatingMapperServiceImpl(UserRatingMapper userRatingMapper){
        this.userRatingMapper = userRatingMapper;
    }

    /**
     * 通过缓存获取
     * @param userRatingMapping
     * @return
     */
    @Override
    public UserRatingMapping getByEntry(UserRatingMapping userRatingMapping) {
        String key = Constants.USER_RATING_MAPPING_CACHE_PREFIX + userRatingMapping.getId();
        Result<UserRatingMapping> result = RedisUtil.getByCache(key,UserRatingMapping.class,userRatingMapping,userRatingMapper::getByEntry);
        return  result.getData();
    }
}
