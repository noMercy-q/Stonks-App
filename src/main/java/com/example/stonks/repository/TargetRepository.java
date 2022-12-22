package com.example.stonks.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stonks.model.Target;

public interface TargetRepository extends JpaRepository<Target, Long> {
    List<Target> findByUserId(Long user_id);
    Target findByUserIdAndName(Long user_id, String name);
    Target findByIdAndName(Long id, String name);
}
