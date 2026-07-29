package com.inventorysystem.services;

import com.inventorysystem.database.DBConnection;
import com.inventorysystem.models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (product_id, quantity, status, order_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getProductId());
            statement.setInt(2, order.getQuantity());
            statement.setString(3, order.getStatus());
            statement.setString(4, order.getOrderDate());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to create order", exception);
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, product_id, quantity, status, order_date FROM orders ORDER BY id";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("status"),
                        resultSet.getString("order_date")
                ));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to load orders", exception);
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT id, product_id, quantity, status, order_date FROM orders WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order(
                            resultSet.getInt("id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("status"),
                            resultSet.getString("order_date")
                    );
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to load order", exception);
        }
        return null;
    }

    public void updateOrderStatus(int id, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to update order", exception);
        }
    }

    public void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to delete order", exception);
        }
    }

    public int getPendingOrderCount() {
        String sql = "SELECT COUNT(*) AS pending_orders FROM orders WHERE status = 'Pending'";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("pending_orders");
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to count pending orders", exception);
        }
        return 0;
    }
}
