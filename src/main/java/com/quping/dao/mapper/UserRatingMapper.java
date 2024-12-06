package com.quping.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quping.entity.UserRatingMapping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRatingMapper extends BaseMapper<UserRatingMapping> {
    UserRatingMapping getByentity(UserRatingMapping urm);
}
