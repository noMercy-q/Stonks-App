package com.example.stonks.service;

import java.util.List;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
