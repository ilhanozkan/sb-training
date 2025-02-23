package com.example.demo.dao;

import com.example.demo.api.UserDTO;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    int insertUser(UUID id, User user);

    default int insertUser(User user) {
        UUID id = UUID.randomUUID();

        return insertUser(id, user);
    }


    List<UserDTO> getAllUsers();

    Optional<UserDTO> selectUserById(UUID id);

    Optional<User> selectUserByEmail(String email);

    int deleteUserById(UUID id);

    int updateUserById(UUID id, User user);
}
