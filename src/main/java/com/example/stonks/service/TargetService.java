package com.example.stonks.service;

import java.util.List;
import com.example.stonks.dto.TargetDto;
import com.example.stonks.dto.UserDto;

public interface TargetService {
    void saveTarget(TargetDto targetDto, UserDto userDto);
    void updateTarget(TargetDto targetDto);
    List<TargetDto> findTargetsForUser(UserDto userDto);
    TargetDto findTargetForUserByName(UserDto userDto, String name);
}
