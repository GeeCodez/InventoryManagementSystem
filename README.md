# Inventory Management System

A simple Java Swing desktop application for inventory management using Maven, SQLite, and JDBC.

## Requirements

- Java 21
- Maven

## Build

```bash
mvn clean package
```

## Run

```bash
java -jar target/inventory-system.jar
```

## Default Login

- Username: admin
- Password: admin123

## Fixing the SQLite JDBC error

If you see the error:

```text
No suitable driver found for jdbc:sqlite:inventory.db
```

follow these steps:

1. Make sure the dependencies are downloaded:
   ```bash
   mvn dependency:go-offline
   ```
2. Rebuild the project:
   ```bash
   mvn clean package
   ```
3. Run the shaded jar:
   ```bash
   java -jar target/inventory-system.jar
   ```
4. If you are running the app manually from the command line, include the JDBC jar in the classpath:
   ```bash
   java -cp "target/classes;C:\Users\godsw\.m2\repository\org\xerial\sqlite-jdbc\3.46.0.0\sqlite-jdbc-3.46.0.0.jar;C:\Users\godsw\.m2\repository\com\formdev\flatlaf\3.5.1\flatlaf-3.5.1.jar" com.inventorysystem.Main
   ```

If Maven is not installed on your machine, install it first and then rerun the build commands above.
