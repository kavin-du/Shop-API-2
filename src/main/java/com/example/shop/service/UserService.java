package com.example.shop.service;

import com.example.shop.model.AppUser;

public interface UserService {
    AppUser saveUser(AppUser user);
    AppUser getUser(String email);
}
