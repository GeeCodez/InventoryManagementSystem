package com.inventorysystem.services;

import com.inventorysystem.database.DBConnection;
import com.inventorysystem.models.Delivery;
import com.inventorysystem.models.Order;
import com.inventorysystem.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveryService {
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();

    public void recordDelivery(int orderId, int quantity) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Selected order does not exist");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (quantity > order.getQuantity()) {
            throw new IllegalArgumentException("Delivery quantity cannot exceed the order quantity");
        }

        Product product = productService.getProductById(order.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Related product does not exist");
        }

        String sql = "INSERT INTO deliveries (order_id, product_id, quantity, delivery_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, order.getProductId());
            statement.setInt(3, quantity);
            statement.setString(4, LocalDate.now().toString());
            statement.executeUpdate();
            productService.updateQuantity(product.getId(), product.getQuantity() + quantity);
            orderService.updateOrderStatus(orderId, "Delivered");
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to record delivery", exception);
        }
    }

    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT id, order_id, product_id, quantity, delivery_date FROM deliveries ORDER BY id";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                deliveries.add(new Delivery(
                        resultSet.getInt("id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("delivery_date")
                ));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to load deliveries", exception);
        }
        return deliveries;
    }
}
