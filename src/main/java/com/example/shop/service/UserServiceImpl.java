package com.example.shop.service;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.model.AppUser;
import com.example.shop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public AppUser saveUser(AppUser user) throws ResourceAlreadyExistsException {
        if(userRepo.findByEmail(user.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("User already exists.");
        }
        return userRepo.save(user);
    }

    @Override
    public AppUser getUser(String email) {
        return userRepo.findByEmail(email);
    }
}
