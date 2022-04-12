package com.example.shop.repository;

import com.example.shop.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}
