package com.example.shop.service;

import com.example.shop.exceptions.UserAlreadyExistsException;
import com.example.shop.model.AppUser;

public interface UserService {
    AppUser saveUser(AppUser user) throws UserAlreadyExistsException;
    AppUser getUser(String email);
}
