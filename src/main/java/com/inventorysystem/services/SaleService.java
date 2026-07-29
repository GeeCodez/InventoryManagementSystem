package com.inventorysystem.services;

import com.inventorysystem.database.DBConnection;
import com.inventorysystem.models.Product;
import com.inventorysystem.models.Sale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleService {
    private final ProductService productService = new ProductService();

    public void recordSale(int productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Selected product does not exist");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        String sql = "INSERT INTO sales (product_id, quantity, sale_date) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            statement.setInt(2, quantity);
            statement.setString(3, LocalDate.now().toString());
            statement.executeUpdate();
            productService.updateQuantity(productId, product.getQuantity() - quantity);
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to record sale", exception);
        }
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT id, product_id, quantity, sale_date FROM sales ORDER BY id";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                sales.add(new Sale(
                        resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("sale_date")
                ));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to load sales", exception);
        }
        return sales;
    }

    public int getSalesCount() {
        String sql = "SELECT COUNT(*) AS sales_count FROM sales";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("sales_count");
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to count sales", exception);
        }
        return 0;
    }
}
