package com.example.demo.dao;

import com.example.demo.api.UserDTO;
import com.example.demo.model.User;
import com.example.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class UserDataAccessService implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public UserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertUser(UUID id, User user) {
        final String sql = "INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            id,
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getRole().name()
        );
    }

    @Override
    public List<UserDTO> getAllUsers() {
        final String sql = "SELECT id, name, email, role FROM users";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            Role role = Role.valueOf(resultSet.getString("role"));

            // Use empty string for password since it's not needed for general listing
            return new UserDTO(id, name, email, role);
        });
    }

    @Override
    public Optional<UserDTO> selectUserById(UUID id) {
        final String sql = "SELECT id, name, email, role FROM users WHERE id = ?";

        try {
            UserDTO userDTO = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
                UUID userId = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Role role = Role.valueOf(resultSet.getString("role"));

                return new UserDTO(userId, name, email, role);
            });

            return Optional.ofNullable(userDTO);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        // Keep password in this query as it's typically used for authentication
        final String sql = "SELECT id, name, email, password, role FROM users WHERE email = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, (resultSet, i) -> {
                UUID userId = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                String userEmail = resultSet.getString("email");
                String password = resultSet.getString("password");
                Role role = Role.valueOf(resultSet.getString("role"));

                return new User(userId, name, userEmail, password, role);
            });

            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteUserById(UUID id) {
        final String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateUserById(UUID id, User user) {
        final String sql = "UPDATE users SET name = ?, email = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(
            sql,
            user.getName(),
            user.getEmail(),
            user.getRole().name(),
            id
        );
    }
}
