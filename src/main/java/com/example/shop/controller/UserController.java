package com.example.shop.controller;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.model.AppUser;
import com.example.shop.model.CustomResponse;
import com.example.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", consumes = "application/json")
@Tag(name = "Auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    @Operation(summary = "User registration")
    public ResponseEntity<?> register(@Valid @RequestBody AppUser user) {
        AppUser appUser;
        try {
            appUser = userService.saveUser(user);
        } catch (ResourceAlreadyExistsException e) {
            return CustomResponse.generate("User already exists.", HttpStatus.CONFLICT);
        }

        return CustomResponse.generate("Successfully registered.", appUser, HttpStatus.CREATED);
    }

}
