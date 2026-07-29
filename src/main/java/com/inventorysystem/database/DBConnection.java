package com.inventorysystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:inventory.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL
                )
                """;

        String createProducts = """
                CREATE TABLE IF NOT EXISTS products (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    category TEXT NOT NULL,
                    price REAL NOT NULL,
                    quantity INTEGER NOT NULL
                )
                """;

        String createOrders = """
                CREATE TABLE IF NOT EXISTS orders (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    product_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    status TEXT NOT NULL,
                    order_date TEXT NOT NULL
                )
                """;

        String createSales = """
                CREATE TABLE IF NOT EXISTS sales (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    product_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    sale_date TEXT NOT NULL
                )
                """;

        String createDeliveries = """
                CREATE TABLE IF NOT EXISTS deliveries (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    order_id INTEGER NOT NULL,
                    product_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    delivery_date TEXT NOT NULL
                )
                """;

        String insertAdmin = """
                INSERT OR IGNORE INTO users (username, password)
                VALUES ('admin', 'admin123')
                """;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(createUsers);
            statement.execute(createProducts);
            statement.execute(createOrders);
            statement.execute(createSales);
            statement.execute(createDeliveries);
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertAdmin)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to initialize database", exception);
        }
    }
}
