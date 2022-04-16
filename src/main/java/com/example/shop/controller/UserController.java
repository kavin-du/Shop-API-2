package com.example.shop.controller;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.model.AppUser;
import com.example.shop.model.CustomResponse;
import com.example.shop.service.UserService;
import com.example.shop.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth", consumes = "application/json")
@Tag(name = "Auth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

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

    @PostMapping("login")
    @Operation(summary = "User login")
    public ResponseEntity<?> login(@Valid @RequestBody AppUser user) {
        String email = user.getEmail();
        String password = user.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException e) {
            return CustomResponse.generate("Incorrect username or password", e.getMessage(), HttpStatus.CONFLICT);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails);

        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("accessToken", jwt);

        return CustomResponse.generate("Login success", tokenData, HttpStatus.OK);
    }

}
