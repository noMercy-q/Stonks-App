package com.example.stonks.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stonks.model.Cost;
import com.example.stonks.model.User;

public interface CostRepository extends JpaRepository<Cost, Long> {
    //Cost findByName(String name);
    List<Cost> findByUserId(Long user_id);
}
