package com.example.shop.service;

import com.example.shop.exceptions.ResourceAlreadyExistsException;
import com.example.shop.model.AppUser;
import com.example.shop.model.Role;
import com.example.shop.repository.RoleRepo;
import com.example.shop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
//    private final PasswordEncoder passwordEncoder;

//    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
//        this.userRepo = userRepo;
//        this.roleRepo = roleRepo;
//        this.passwordEncoder = passwordEncoder;
//    }

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    // spring security
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepo.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) throws ResourceAlreadyExistsException {
        if(userRepo.findByEmail(user.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("User already exists.");
        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }


    @Override
    public AppUser getUser(String email) {
        return userRepo.findByEmail(email);
    }

}
