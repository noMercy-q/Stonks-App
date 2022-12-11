package com.example.stonks.service;

import java.util.List;
import java.util.Map;
import com.example.stonks.dto.CostDto;
import com.example.stonks.dto.UserDto;

public interface CostService {
    void saveCost(CostDto costDto, UserDto user);
    List<CostDto> findAllCostsForUser(UserDto user);
    Map<String, Integer> createCategroriesMap(UserDto user, Boolean positive_sum);
    Long getTotalCredit(UserDto user);
    Long getTotalDebit(UserDto user);
}
