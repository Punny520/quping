package com.quping.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quping.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
