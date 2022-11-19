package com.example.stonks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.stonks.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);    
}
