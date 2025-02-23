package com.example.demo.service;

import com.example.demo.api.UpdateUserRequest;
import com.example.demo.api.UserDTO;
import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(@Qualifier("postgres") UserDao userDao) {
        this.userDao = userDao;
    }

    public int addUser(User user) {
        return userDao.insertUser(user);
    }

    public List<UserDTO> getAllUsers() {
        return userDao.getAllUsers();
    }

    public Optional<UserDTO> getUserById(UUID id) {
        return userDao.selectUserById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userDao.selectUserByEmail(email);
    }

    public int deleteUserById(UUID id) {
        return userDao.deleteUserById(id);
    }

    public int updateUserById(UUID id, UpdateUserRequest updateRequest) {
        // Get the existing user to preserve the password
        User existingUser = getUserByEmail(updateRequest.getEmail())
            .orElseGet(() -> new User(id, updateRequest.getName(), updateRequest.getEmail(), "", updateRequest.getRole()));
        
        User updatedUser = new User(
            id,
            updateRequest.getName(),
            updateRequest.getEmail(),
            existingUser.getPassword(), // Preserve the existing password
            updateRequest.getRole()
        );
        
        return userDao.updateUserById(id, updatedUser);
    }
}
