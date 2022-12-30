package com.example.stonks.service;

import java.util.List;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.User;

public interface UserService {
    void saveUser(UserDto userDto);
    UserDto findUserByEmail(String email);
    User findUserByUsername(String username);
    List<UserDto> findAllUsers();
    UserDto getCurrentUser();
}
