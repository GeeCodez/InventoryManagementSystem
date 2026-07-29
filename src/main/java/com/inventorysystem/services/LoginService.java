package com.inventorysystem.services;

import com.inventorysystem.database.DBConnection;
import com.inventorysystem.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    public boolean authenticate(String username, String password) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Authentication failed", exception);
        }
    }

    public User findUser(String username) {
        String sql = "SELECT id, username, password FROM users WHERE username = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to load user", exception);
        }
        return null;
    }
}
