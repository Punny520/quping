package com.quping.service;


import com.quping.common.Result;
import com.quping.dto.UserDTO;
import com.quping.entry.User;

public interface UserService {
    Result<?> getCode(String phoneNumber);

    Result<String> doLogin(UserDTO userDTO);

    Result<?> addUser(UserDTO userDTO);

    Result<User> showProfile();

    Result<Void> loginOut();
}
