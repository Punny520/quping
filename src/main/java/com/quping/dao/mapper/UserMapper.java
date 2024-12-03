package com.quping.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quping.entry.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
