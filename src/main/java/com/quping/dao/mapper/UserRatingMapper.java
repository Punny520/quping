package com.quping.dao.mapper;

import com.quping.entry.UserRatingMapping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRatingMapper {
    int doRating(UserRatingMapping urm);
    UserRatingMapping getByEntry(UserRatingMapping urm);
}
