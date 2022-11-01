package com.example.stonks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stonks.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> { }
