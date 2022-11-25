package com.example.stonks.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.stonks.dto.CostDto;
import com.example.stonks.model.Cost;
import com.example.stonks.model.User;
import com.example.stonks.repository.CostRepository;

@Service
public class CostServiceImpl implements CostService {

    private CostRepository costRepository;

    public CostServiceImpl(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    @Override
    public void saveCost(CostDto costDto) {
        Cost cost = new Cost();
        cost.setAmount(costDto.getAmount());
        cost.setDate(costDto.getDate());
        cost.setUser(cost.getUser());
        costRepository.save(cost);
    }

    @Override
    public List<CostDto> findAllCostsForUser(User user) {
        List<Cost> costs = costRepository.findByUserId(user.getId());

        return costs.stream()
            .map((cost) -> mapToCostDto(cost))
            .collect(Collectors.toList());
    }

    @Override
    public Long getTotalDebit(User user) {
        List<CostDto> costs = findAllCostsForUser(user);
        return costs.stream().filter(cost -> cost.getAmount() >= 0)
                             .mapToLong(cost -> cost.getAmount())
                             .sum();
    }


    @Override
    public Long getTotalCredit(User user) {
        List<CostDto> costs = findAllCostsForUser(user);
        return costs.stream().filter(cost -> cost.getAmount() < 0)
                             .mapToLong(cost -> cost.getAmount())
                             .sum();
    }

    private CostDto mapToCostDto(Cost cost) {
        CostDto costDto = new CostDto();
        costDto.setAmount(cost.getAmount());
        costDto.setDate(cost.getDate());
        return costDto;
    }
}
