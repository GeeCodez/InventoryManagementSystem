Build Java Desktop Inventory Management System

You are a senior Java developer. Build a complete Inventory Management System desktop application for a small business.

The application must be simple, genuine, maintainable, and suitable for an academic software engineering project.

Technology Requirements

Use:

Language: Java 21
Build Tool: Maven
Desktop UI: Java Swing
UI Styling: FlatLaf for modern Swing appearance
Database: SQLite
Database Access: JDBC
Architecture: Simple MVC + Service Layer

Do not use:

Spring Boot
Web frameworks
REST APIs
Complex enterprise patterns

This is a standalone desktop application.

Project Structure

Create the following structure:

src/main/java/com/inventorysystem/

├── Main.java

├── database/
│   └── DBConnection.java

├── models/
│   ├── User.java
│   ├── Product.java
│   ├── Order.java
│   ├── Sale.java
│   └── Delivery.java

├── services/
│   ├── LoginService.java
│   ├── ProductService.java
│   ├── OrderService.java
│   ├── SaleService.java
│   └── DeliveryService.java

├── ui/
│   ├── LoginFrame.java
│   ├── DashboardFrame.java
│   ├── ProductFrame.java
│   ├── OrderFrame.java
│   ├── SaleFrame.java
│   └── DeliveryFrame.java

└── utils/
    └── Validator.java
Database Design

Use SQLite.

Create a database file:

inventory.db

Create these tables automatically when the application starts.

Users Table
id INTEGER PRIMARY KEY AUTOINCREMENT

username TEXT UNIQUE

password TEXT

Insert a default admin account:

username: admin
password: admin123
Products Table
id INTEGER PRIMARY KEY AUTOINCREMENT

name TEXT

category TEXT

price REAL

quantity INTEGER
Orders Table
id INTEGER PRIMARY KEY AUTOINCREMENT

product_id INTEGER

quantity INTEGER

status TEXT

order_date TEXT

Status values:

Pending
Delivered
Cancelled
Sales Table
id INTEGER PRIMARY KEY AUTOINCREMENT

product_id INTEGER

quantity INTEGER

sale_date TEXT
Deliveries Table
id INTEGER PRIMARY KEY AUTOINCREMENT

order_id INTEGER

product_id INTEGER

quantity INTEGER

delivery_date TEXT
Application Flow

The application should work like this:

Start Application

        ↓

Initialize Database

        ↓

Login Screen

        ↓

Dashboard

        ↓

Manage Products

Manage Orders

Manage Deliveries

Manage Sales

        ↓

Exit
UI Requirements

Create a clean business dashboard.

Use:

Sidebar navigation
Header section
Cards for statistics
Tables using JTable
Forms using JTextField/JComboBox
Dialog messages for success/errors

The design should look like a simple admin dashboard.

Screens
1. Login Screen

Components:

Username field
Password field
Login button

Function:

Authenticate user through LoginService.
Open DashboardFrame after successful login.
2. Dashboard Screen

Display:

Total Products
Low Stock Products
Total Sales
Pending Orders

Navigation buttons:

Products
Orders
Deliveries
Sales
Logout
3. Product Management

Features:

CRUD operations:

Add product
View products
Update product
Delete product
Search product

Table columns:

ID
Name
Category
Price
Quantity

Validation:

Name cannot be empty.
Price must be positive.
Quantity cannot be negative.
4. Order Management

Features:

Create order
View orders
Update order status
Delete order

Table:

ID
Product
Quantity
Status
Date
5. Delivery Management

When a delivery is recorded:

Save delivery record.
Increase product quantity.
Change order status to Delivered.
6. Sales Management

When a sale is recorded:

Check available stock.
Prevent selling more than available quantity.
Reduce product quantity.
Save sale record.
Coding Rules

Follow these rules:

Models should only contain:
Fields
Constructors
Getters
Setters
Services should contain:
Database operations
Business logic
UI classes should only handle:
Display
User input
Calling services
Do not put SQL queries inside UI classes.
Use PreparedStatement for all SQL queries.
Handle database exceptions properly.
Write clean readable Java code.
Maven Dependencies

Add:

SQLite JDBC driver
FlatLaf

Configure Maven correctly.

Development Approach

Do not generate everything blindly.

Build in this order:

Phase 1:

Maven setup
Dependencies
Database connection
Database tables

Phase 2:

Models

Phase 3:

Login system

Phase 4:

Dashboard

Phase 5:

Product module

Phase 6:

Order module

Phase 7:

Delivery module

Phase 8:

Sales module

Phase 9:

UI polishing and testing

After every phase:

Compile the project.
Fix errors.
Keep the project runnable.
Final Deliverables

The final project should include:

Complete Java source code
Maven configuration
SQLite database initialization
Clean UI
README.md explaining setup and usage
Instructions to run:
mvn clean package
java -jar target/inventory-system.jar

Start by creating the Maven project structure and implementing Phase 1 only. Do not skip steps.