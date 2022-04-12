package com.example.shop.controller;

import com.example.shop.model.AppUser;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<AppUser> register(@Valid @RequestBody AppUser user) {
        AppUser appUser = userService.saveUser(user);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }

}
