package com.example.shop.repository;

import com.example.shop.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

// repository for storing the Users in the database
public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}
