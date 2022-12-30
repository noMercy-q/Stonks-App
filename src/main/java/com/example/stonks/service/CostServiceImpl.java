package com.example.stonks.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.stonks.dto.CostDto;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.Category;
import com.example.stonks.model.Cost;
import com.example.stonks.model.User;
import com.example.stonks.repository.CategoryRepository;
import com.example.stonks.repository.CostRepository;
import com.example.stonks.repository.UserRepository;

@Service
public class CostServiceImpl implements CostService {

    private CostRepository costRepository;
    private UserRepository userRepositiry;
    private CategoryRepository categoryRepository;

    public CostServiceImpl(CostRepository costRepository,
                           UserRepository userRepository,
                           CategoryRepository categoryRepository) {
        this.costRepository = costRepository;
        this.userRepositiry = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveCost(CostDto costDto, UserDto userDto) {
        Cost cost = new Cost();
        cost.setAmount(costDto.getAmount());
        if (costDto.getDate() == null) {
            Date date = new Date();
            cost.setDate(date);
        } else
            cost.setDate(costDto.getDate());

        User user = userRepositiry.findByEmail(userDto.getEmail());
        cost.setUser(user);
        Category category = categoryRepository.findByNameAndUserId(costDto.getCategory(), user.getId());
        cost.setCategory(category);

        costRepository.save(cost);
    }

    @Override
    public List<CostDto> findAllCostsForUser(UserDto user) {
        List<Cost> costs = costRepository.findByUserId(user.getId());
        return costs.stream()
                    .map((cost) -> mapToCostDto(cost))
                    .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> createCategroriesMap(UserDto user, Boolean positive_sum) {
        Map<String, Integer> categories_map = new HashMap<>();
        List<CostDto> costs = findAllCostsForUser(user);
        for (CostDto cost : costs) {
            if (positive_sum == cost.getAmount() > 0)
                categories_map.merge(cost.getCategory(), Math.toIntExact(cost.getAmount()), (t, u) -> Integer.sum(t, u));     
        }
        return categories_map;
    }

    @Override
    public Long getTotalDebit(UserDto user) {
        List<CostDto> costs = findAllCostsForUser(user);
        return costs.stream().filter(cost -> cost.getAmount() >= 0)
                             .mapToLong(cost -> cost.getAmount())
                             .sum();
    }

    @Override
    public Long getTotalCredit(UserDto user) {
        List<CostDto> costs = findAllCostsForUser(user);
        return costs.stream().filter(cost -> cost.getAmount() < 0)
                             .mapToLong(cost -> cost.getAmount())
                             .sum();
    }

    private CostDto mapToCostDto(Cost cost) {
        CostDto costDto = new CostDto();
        costDto.setAmount(cost.getAmount());
        costDto.setDate(cost.getDate());
        costDto.setCategory(cost.getCategory().getName());
        return costDto;
    }
}
