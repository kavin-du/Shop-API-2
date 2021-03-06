package com.example.shop.service;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.model.AppUser;

// interface for creating users
public interface UserService {
    AppUser saveUser(AppUser user) throws ResourceAlreadyExistsException;
    AppUser getUser(String email);
}
