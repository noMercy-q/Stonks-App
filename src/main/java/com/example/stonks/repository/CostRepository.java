package com.example.stonks.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stonks.model.Cost;

public interface CostRepository extends JpaRepository<Cost, Long> {
    List<Cost> findByUserId(Long user_id);
}
