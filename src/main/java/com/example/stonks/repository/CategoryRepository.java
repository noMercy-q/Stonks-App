package com.example.stonks.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.stonks.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long user_id);
    List<Category> findByName(String name);
    Category findByNameAndUserId(String name, Long user_id);
}
