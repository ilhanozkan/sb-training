package com.example.demo.service;

import com.example.demo.api.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(User user) {
        // Encode password and set default role
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User userToSave = new User(
            user.getId(),
            user.getName(),
            user.getEmail(),
            encodedPassword,
            Role.USER  // Always set to USER role for new registrations
        );
        userService.addUser(userToSave);
        
        String jwtToken = jwtService.generateToken(userToSave);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(String email, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        
        User user = userService.getUserByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
} 