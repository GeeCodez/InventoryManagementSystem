package com.inventorysystem.services;

import com.inventorysystem.database.DBConnection;
import com.inventorysystem.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, category, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to add product", exception);
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, category, price, quantity FROM products ORDER BY id";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity")
                ));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to load products", exception);
        }
        return products;
    }

    public Product getProductById(int productId) {
        String sql = "SELECT id, name, category, price, quantity FROM products WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("quantity")
                    );
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to load product", exception);
        }
        return null;
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, category = ?, price = ?, quantity = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to update product", exception);
        }
    }

    public void updateQuantity(int productId, int quantity) {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to update stock", exception);
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to delete product", exception);
        }
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, category, price, quantity FROM products WHERE name LIKE ? OR category LIKE ? ORDER BY id";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            statement.setString(1, pattern);
            statement.setString(2, pattern);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to search products", exception);
        }
        return products;
    }

    public int getLowStockCount() {
        String sql = "SELECT COUNT(*) AS low_stock FROM products WHERE quantity < 5";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("low_stock");
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to count low stock items", exception);
        }
        return 0;
    }
}
