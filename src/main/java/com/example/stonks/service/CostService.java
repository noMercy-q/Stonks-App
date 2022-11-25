package com.example.stonks.service;

import java.util.List;
import com.example.stonks.dto.CostDto;
import com.example.stonks.model.User;

public interface CostService {
    void saveCost(CostDto costDto);
    List<CostDto> findAllCostsForUser(User user);
    Long getTotalCredit(User user);
    Long getTotalDebit(User user);
}
