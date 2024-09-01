package com.pingfen.user.service;

import com.pingfen.user.common.Result;
import com.pingfen.user.dto.UserDTO;

public interface UserService {
    void getCode(String phoneNumber);

    Result doLogin(UserDTO userDTO);

    Result addUser(UserDTO userDTO);
}
