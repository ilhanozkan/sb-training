package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("api/v1/user")
@RestController
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@Valid @NotNull @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public UserDTO getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id)
            .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUserById(@PathVariable("id") UUID id) {
        userService.deleteUserById(id);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Void> updateUserById(
            @PathVariable("id") UUID id,
            @Valid @NotNull @RequestBody UpdateUserRequest updateRequest) {
        userService.updateUserById(id, updateRequest);
        return ResponseEntity.ok().build();
    }
}
