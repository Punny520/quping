package com.quping.dao.mapper;

import com.quping.entry.UserRatingMapping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRatingMapper {
    UserRatingMapping getByEntry(UserRatingMapping urm);

    void insert(UserRatingMapping urm);

    void update(UserRatingMapping urm);
}
