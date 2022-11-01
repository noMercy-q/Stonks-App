package com.example.stonks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stonks.model.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
