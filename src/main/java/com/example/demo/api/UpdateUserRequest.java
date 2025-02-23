package com.example.demo.api;

import com.example.demo.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {
    @NotBlank(message = "name cannot be blank")
    private final String name;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email should be valid")
    private final String email;

    private final Role role;

    public UpdateUserRequest(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role != null ? role : Role.USER;
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