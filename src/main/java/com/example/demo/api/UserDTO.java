package com.example.demo.api;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.UUID;

public class UserDTO {
    private final UUID id;
    private final String name;
    private final String email;
    private final Role role;

    public UserDTO(UUID id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static UserDTO fromUser(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
} 