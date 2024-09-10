package com.quping.service;


import com.quping.common.Result;
import com.quping.dto.UserDTO;

public interface UserService {
    void getCode(String phoneNumber);

    Result doLogin(UserDTO userDTO);

    Result addUser(UserDTO userDTO);

    Result showProfile();

}
