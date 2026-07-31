# Inventory Management System Overview

This project is a simple desktop inventory application built with Java, Swing, Maven, SQLite, and JDBC. It is designed as a student-friendly example of a small business management system with a clean MVC-style structure: models hold data, services contain business rules and database access, and UI classes handle screen rendering and user interaction.

## Project purpose

The application helps a small business manage:

- products
- orders
- deliveries
- sales
- user login

The system is intended to be easy to understand, maintain, and extend.

---

## Top-level project files

### pom.xml
Purpose:
- Configures the Maven build for the project.
- Declares Java version, project metadata, and dependencies.
- Adds the SQLite JDBC driver and FlatLaf UI library.

What it does in the system:
- Lets the project compile and run as a Java desktop application.
- Ensures the app can connect to SQLite and use a modern Swing look.

### README.md
Purpose:
- Contains basic setup and run instructions for the project.
- Includes the default login credentials and troubleshooting help for SQLite driver issues.

What it does in the system:
- Serves as the main user-facing setup guide.

### codex_prompt.md
Purpose:
- Contains the original project specification and requirements.
- Acts as the reference for the implementation plan.

What it does in the system:
- Defines the feature scope, structure, and expected behavior of the application.

### inventory.db
Purpose:
- The SQLite database file used by the application.

What it does in the system:
- Stores all persisted application data such as users, products, orders, deliveries, and sales.

---

## Main application entry point

### src/main/java/com/inventorysystem/Main.java
Purpose:
- This is the application entry point.

What it does in the system:
- Sets up the FlatLaf appearance theme.
- Initializes the database.
- Opens the login screen so the user can begin using the system.

---

## Database layer

### src/main/java/com/inventorysystem/database/DBConnection.java
Purpose:
- Responsible for opening database connections.
- Creates the SQLite database tables if they do not already exist.
- Inserts the default admin user.

What it does in the system:
- Acts as the bridge between the Java application and the SQLite database.
- Ensures the database is ready before the UI starts.
- Implements the required schema for users, products, orders, sales, and deliveries.

---

## Model layer

The model layer contains plain Java classes that represent data entities.

### src/main/java/com/inventorysystem/models/User.java
Purpose:
- Represents an application user.

What it does in the system:
- Holds user information such as username and password.
- Supports authentication-related workflows.

### src/main/java/com/inventorysystem/models/Product.java
Purpose:
- Represents a product in the inventory.

What it does in the system:
- Stores the product name, category, price, and current stock quantity.
- Used by product management and sales/delivery logic.

### src/main/java/com/inventorysystem/models/Order.java
Purpose:
- Represents a customer or supplier order.

What it does in the system:
- Stores the associated product, quantity, status, and order date.
- Supports order creation, updating, and tracking.

### src/main/java/com/inventorysystem/models/Sale.java
Purpose:
- Represents a completed sale.

What it does in the system:
- Stores sale details such as the product sold, quantity, and sale date.
- Used for recording sales and showing sales history.

### src/main/java/com/inventorysystem/models/Delivery.java
Purpose:
- Represents a delivery event.

What it does in the system:
- Stores delivery information including the related order, product, quantity, and delivery date.
- Used by the delivery workflow.

---

## Service layer

The service layer contains the business logic and database operations. It is the core of the application.

### src/main/java/com/inventorysystem/services/LoginService.java
Purpose:
- Handles authentication logic.

What it does in the system:
- Checks whether a username/password combination exists in the database.
- Allows the login screen to validate users before opening the dashboard.

### src/main/java/com/inventorysystem/services/ProductService.java
Purpose:
- Manages all product-related actions.

What it does in the system:
- Adds, reads, updates, deletes, and searches products.
- Updates stock quantities when sales and deliveries happen.
- Calculates low-stock metrics for the dashboard.

### src/main/java/com/inventorysystem/services/OrderService.java
Purpose:
- Manages order operations.

What it does in the system:
- Creates orders.
- Retrieves orders from the database.
- Updates order status.
- Deletes orders when necessary.
- Counts pending orders for dashboard metrics.

### src/main/java/com/inventorysystem/services/SaleService.java
Purpose:
- Handles sales recording and validation.

What it does in the system:
- Prevents sales that exceed available stock.
- Records a sale in the database.
- Reduces the related product quantity after a sale.
- Provides sales counts for the dashboard.

### src/main/java/com/inventorysystem/services/DeliveryService.java
Purpose:
- Handles delivery recording and stock updates.

What it does in the system:
- Records a delivery against an order.
- Increases the product quantity after delivery.
- Marks the related order as delivered.
- Keeps the inventory synchronized with incoming stock.

---

## UI layer

The UI layer contains the Swing screens that the user interacts with.

### src/main/java/com/inventorysystem/ui/LoginFrame.java
Purpose:
- Displays the login screen.

What it does in the system:
- Lets the user enter a username and password.
- Calls the login service to authenticate the user.
- Opens the dashboard after a successful login.

### src/main/java/com/inventorysystem/ui/DashboardFrame.java
Purpose:
- Displays the main dashboard screen.

What it does in the system:
- Shows summary cards for total products, low stock, total sales, and pending orders.
- Provides navigation to the product, order, delivery, and sales screens.
- Gives the user access to logout and refresh actions.

### src/main/java/com/inventorysystem/ui/ProductFrame.java
Purpose:
- Displays the product management screen.

What it does in the system:
- Lets the user add, edit, delete, and search products.
- Shows products in a table.
- Validates product data before saving it.

### src/main/java/com/inventorysystem/ui/OrderFrame.java
Purpose:
- Displays the order management screen.

What it does in the system:
- Lets the user create new orders.
- Displays order records in a table.
- Updates order status and deletes orders.

### src/main/java/com/inventorysystem/ui/DeliveryFrame.java
Purpose:
- Displays the delivery management screen.

What it does in the system:
- Lets the user record deliveries for existing orders.
- Displays delivery history in a table.
- Updates inventory and order status after delivery.

### src/main/java/com/inventorysystem/ui/SaleFrame.java
Purpose:
- Displays the sales management screen.

What it does in the system:
- Lets the user record sales.
- Prevents sales when stock is insufficient.
- Displays sales history in a table.

### src/main/java/com/inventorysystem/ui/UITheme.java
Purpose:
- Provides shared styling for the Swing UI.

What it does in the system:
- Defines colors, spacing, button visuals, labels, and table appearance.
- Keeps the interface consistent across screens.

---

## Utility layer

### src/main/java/com/inventorysystem/utils/Validator.java
Purpose:
- Centralizes input validation rules.

What it does in the system:
- Ensures product names are not empty.
- Ensures prices are positive.
- Ensures quantities are not negative.
- Helps the UI keep invalid data from reaching the database.

---

## How the system works end to end

1. The application starts from Main.
2. The database is initialized and required tables are created.
3. The login screen appears.
4. After successful authentication, the dashboard opens.
5. From the dashboard, the user can navigate to:
   - products
   - orders
   - deliveries
   - sales
6. Each module updates the SQLite database and keeps the inventory state consistent.
7. The dashboard metrics refresh from the database so the user sees current system values.

---

## Summary of the architecture

- Models represent business objects.
- Services contain logic and database access.
- UI classes present screens and collect input.
- The database stores the persisted data.
- Validation prevents bad data from entering the system.

This structure keeps the application simple, readable, and suitable for a desktop inventory project.
