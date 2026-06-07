package com.skyfly.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyfly.backend.model.User;

// Meng-extends JpaRepository memberikan kita fitur otomatis: Save, FindAll, Delete, dll.
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Fitur Ajaib: Spring Boot akan otomatis mengubah nama fungsi ini menjadi query SQL: 
    // "SELECT * FROM users WHERE identifier = ?" (Untuk keperluan Login)
    Optional<User> findByIdentifier(String identifier);
}