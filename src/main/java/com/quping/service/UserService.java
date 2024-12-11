package com.quping.service;


import com.quping.common.Result;
import com.quping.dto.UserDTO;
import com.quping.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Result<String> doLogin(UserDTO userDTO);

    Result<?> addUser(UserDTO userDTO);

    Result<User> showProfile();

    Result<Void> loginOut();

    Result<?> getCodeByPhone(String phone);

    Result<?> getCodeByEmail(String email);

    Result<Boolean> checkIfFirstLogin();

    Result<?> firstSetting(UserDTO userDTO);

    Result<String> uploadAvatar(MultipartFile file);

    Result<?> update(UserDTO userDTO);
}
