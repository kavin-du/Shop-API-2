package com.example.shop.service;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.model.AppUser;
import com.example.shop.model.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

// interface for creating users
public interface UserService extends UserDetailsService {
    AppUser saveUser(AppUser user) throws ResourceAlreadyExistsException;
    Role saveRole(Role role);
    AppUser getUser(String email);
}
