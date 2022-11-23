package com.example.stonks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stonks.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}
