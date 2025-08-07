// Example UserService.java
package com.gym_management_backend.services;

import com.gym_management_backend.dto.RegisterRequest;
import com.gym_management_backend.entities.User;
import com.gym_management_backend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // This method needs to be called during user registration
    public void registerNewUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // ENCODE THE PASSWORD!
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(request.getRole())
                .active(true)
                .build();

        userRepository.save(newUser);
    }
}