package com.pingfen.user.repository.mapper;

import com.pingfen.user.entry.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getUser(User user);
    int insertUser(User user);

    User getUserByPhoneNumber(String phoneNumber);
}
