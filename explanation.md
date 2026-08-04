# Explanation of the Inventory Management System

This project is a desktop-based inventory management system built in Java. It is designed to help a user manage products, orders, deliveries, and sales from a simple graphical interface. The system uses a database to store and retrieve data, so the information remains available even after the program is closed.

---

## 1. What this system does

The application allows a user to:
- log in to the system
- manage products (add, update, delete, search)
- create purchase orders
- record deliveries
- record sales
- view a dashboard with summary statistics

The project is organized into different folders so the code is easier to understand and maintain.

---

## 2. Main languages and frameworks used

### Java
Java is the main programming language used to build this project.

Why Java is used:
- it is object-oriented
- it is platform independent
- it is widely used for desktop applications
- it has strong libraries for GUI and database work

In this project, Java is used to create the application logic, manage user actions, connect to the database, and display the user interface.

#### Important Java concepts used in this project
- Classes: every important part of the system is written as a class.
- Objects: classes are used to create objects such as Product, Order, Sale, and Delivery.
- Methods: methods contain actions such as adding a product or authenticating a login.
- Variables: used to store values such as names, prices, quantities, and IDs.
- Constructors: used to initialize objects.
- Encapsulation: data is stored in private fields and accessed using getter and setter methods.
- Exception handling: the code uses try-catch blocks to handle errors safely.

Example:
```java
public class Product {
    private int id;
    private String name;
    private double price;

    public Product() {
    }
}
```

This shows a Java class with private fields and a constructor.

---

### Swing
Swing is a Java framework used to build the desktop graphical user interface (GUI).

It provides components such as:
- JFrame: the main window
- JPanel: containers for organizing UI elements
- JButton: buttons
- JTextField: text input fields
- JTable: tables for displaying data
- JOptionPane: dialog boxes for messages and confirmations

In this project, Swing is used to create the login screen, dashboard, product form, order form, sales form, and delivery form.

#### How Swing works in this project
The UI classes in the package `com.inventorysystem.ui` use Swing classes to create visible screens.

Example:
```java
JFrame frame = new JFrame();
JPanel panel = new JPanel();
JButton button = new JButton("Login");
```

This creates a window, a panel, and a button.

#### Layout managers
Swing uses layout managers to place components neatly on the screen. In this project, layout managers such as:
- BorderLayout
- GridLayout
- FlowLayout
- GridBagLayout

are used to arrange buttons, labels, forms, and tables.

Example:
```java
JPanel root = new JPanel(new GridLayout(1, 2));
```

This creates a panel with two columns.

---

### FlatLaf
FlatLaf is a UI look-and-feel library for Java Swing.

It is used to make the interface look more modern and attractive.

In this project, it is used in the `Main` class:
```java
FlatLightLaf.setup();
```

This line applies the FlatLaf light theme to the application.

Why it is useful:
- improves the visual appearance of the GUI
- gives a modern desktop application style
- works with Swing without changing the code structure much

---

### Maven
Maven is a build automation and dependency management tool.

It helps the project by:
- compiling the Java code
- downloading required libraries
- packaging the application into a runnable JAR file
- managing project dependencies from the `pom.xml` file

The `pom.xml` file contains the project configuration, including:
- Java version
- project name and version
- dependencies such as SQLite JDBC and FlatLaf

Example dependency:
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.46.0.0</version>
</dependency>
```

This tells Maven to download the SQLite database driver for Java.

---

### SQLite
SQLite is a small, lightweight database system.

It is used in this project because:
- it is simple to set up
- it does not require a separate server
- it stores data in a local file named `inventory.db`

The database stores tables such as:
- users
- products
- orders
- sales
- deliveries

This is important because the system needs to remember products, stock quantities, orders, and sales history.

---

### JDBC
JDBC stands for Java Database Connectivity.

It is the Java API used to connect Java programs to databases.

In this project, JDBC is used to:
- open a connection to the SQLite database
- execute SQL statements
- insert, update, delete, and retrieve records

The connection class is `DBConnection`, which creates the link between the Java program and the SQLite database.

Example:
```java
Connection connection = DriverManager.getConnection(DB_URL);
```

This code opens a database connection.

---

### SQL
SQL stands for Structured Query Language.

It is used to communicate with the database.

The system uses SQL to:
- create tables
- insert default login data
- add products
- search products
- update quantities
- retrieve sales and orders

Examples of SQL statements used:
```sql
CREATE TABLE IF NOT EXISTS products (...)
SELECT id, name, category, price, quantity FROM products
INSERT INTO sales (product_id, quantity, sale_date) VALUES (?, ?, ?)
UPDATE products SET quantity = ? WHERE id = ?
```

The Java code uses `PreparedStatement` to safely send SQL queries to the database.

---

## 3. How the code is organized

The project is divided into packages:

### `com.inventorysystem.main`
This contains the entry point of the application.

### `com.inventorysystem.ui`
This contains all graphical screen classes:
- LoginFrame
- DashboardFrame
- ProductFrame
- OrderFrame
- DeliveryFrame
- SaleFrame
- UITheme

These classes are responsible for the visible interface.

### `com.inventorysystem.services`
This contains the service classes that handle business logic and database operations:
- LoginService
- ProductService
- OrderService
- DeliveryService
- SaleService

These classes act as the bridge between the user interface and the database.

### `com.inventorysystem.models`
This contains the model classes that represent data objects:
- Product
- Order
- Sale
- Delivery
- User

These classes hold the data and provide methods to get or set values.

### `com.inventorysystem.database`
This contains database connection and initialization logic.

### `com.inventorysystem.utils`
This contains helper classes such as validation.

---

## 4. How the application works step by step

### Step 1: Program starts
When the application starts, the `Main` class runs.

It performs these tasks:
1. loads the FlatLaf theme
2. initializes the database tables
3. opens the login window

```java
public static void main(String[] args) {
    FlatLightLaf.setup();
    DBConnection.initializeDatabase();
    java.awt.EventQueue.invokeLater(LoginFrame::new);
}
```

This means the GUI is created on the Event Dispatch Thread, which is the correct thread for Swing applications.

### Step 2: Login screen appears
The `LoginFrame` displays the login form.

When the user clicks the login button, the `LoginService` checks the username and password against the database.

### Step 3: Dashboard opens
If authentication succeeds, the dashboard is shown.

The dashboard provides navigation to the product, order, delivery, and sales sections.

### Step 4: Product management
The product screen allows the user to:
- enter product details
- add products
- update products
- delete products
- search products

The `ProductService` class handles the database operations for these tasks.

### Step 5: Orders, deliveries, and sales
- Orders are created and stored in the database.
- Deliveries are recorded and linked to orders.
- Sales reduce the stock quantity and are stored for report purposes.

This shows the flow of data from the UI to the service layer and finally into the database.

---

## 5. Explanation of the most important classes

### `Main`
The `Main` class is the starting point of the application.
It initializes the theme, creates the database tables, and opens the login window.

### `DBConnection`
This class manages the SQLite database connection.
It uses:
- `DriverManager.getConnection()` to connect to the database
- `Statement` or `PreparedStatement` to run SQL commands

### `LoginService`
This class checks whether a username and password match a record in the database.
It uses a SQL query to search for the user.

### `ProductService`
This class handles all product-related database work.
It can:
- add new products
- retrieve products
- update products
- delete products
- search products
- count low-stock items

### `OrderService`
This class manages orders in the database.
It stores order details and tracks their status.

### `DeliveryService`
This class records deliveries and updates stock after a delivery is received.

### `SaleService`
This class records sales and reduces the available stock quantity.

### `UITheme`
This class contains reusable design settings for the interface.
It defines colors, button styles, table styling, and common UI appearance rules.

---

## 6. Important Java syntax used in this project

### Class and object creation
```java
Product product = new Product();
```
This creates an object from the `Product` class.

### Access modifiers
```java
private int id;
public void setId(int id) {
    this.id = id;
}
```
`private` restricts access to the field, while `public` allows other classes to use the method.

### Methods
```java
public void addProduct(Product product) {
    // method body
}
```
A method defines a block of code that performs an action.

### Constructor
```java
public Product(int id, String name, String category, double price, int quantity) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
}
```
A constructor initializes a new object when it is created.

### Conditional statements
```java
if (product == null) {
    throw new IllegalArgumentException("Selected product does not exist");
}
```
This checks a condition and responds accordingly.

### Exception handling
```java
try {
    // database code
} catch (SQLException exception) {
    throw new RuntimeException("Failed to add product", exception);
}
```
This handles possible errors during database work.

### `try-with-resources`
```java
try (Connection connection = DBConnection.getConnection();
     PreparedStatement statement = connection.prepareStatement(sql)) {
    // code
}
```
This is a safe way to open resources such as database connections and automatically close them after use.

### `PreparedStatement`
```java
PreparedStatement statement = connection.prepareStatement(sql);
statement.setString(1, username);
```
This is used to safely send values into SQL queries.

### Event listeners
```java
loginButton.addActionListener(event -> login(usernameField, passwordField));
```
This tells the program what to do when the button is clicked.

---

## 7. Why the services layer is important

The services package is important because it keeps the code organized.

It separates:
- user interface code from business logic
- database logic from screen logic
- data validation and processing from display coding

This makes the application easier to understand, maintain, and expand.

---

## 8. Why this project is a good example of Java desktop application development

This project shows many important programming concepts:
- Java programming
- object-oriented design
- GUI development with Swing
- database work with JDBC and SQLite
- use of classes, methods, and objects
- building a complete application from small connected modules

It is a strong example of how a desktop application can be built with Java.

---

## 9. Possible viva questions and simple answers

### Question 1: What language is used in this project?
Answer: The project is built mainly using Java.

### Question 2: What is Swing?
Answer: Swing is a Java library used to build graphical desktop interfaces.

### Question 3: What is Maven used for?
Answer: Maven is used to manage project dependencies and build the application.

### Question 4: What is JDBC?
Answer: JDBC is the Java API used to connect Java applications to databases.

### Question 5: Why is SQLite used?
Answer: SQLite is used because it is lightweight and stores data in a local file without needing a server.

### Question 6: What is the role of the `services` package?
Answer: It contains the business logic and database operations.

### Question 7: What is the purpose of `PreparedStatement`?
Answer: It helps execute SQL statements safely and efficiently.

### Question 8: What happens when a sale is recorded?
Answer: The sale is saved in the database and the product quantity is reduced.

### Question 9: What is the difference between a model and a service?
Answer: A model represents data, while a service handles the logic and database operations for that data.

### Question 10: What is the purpose of `UITheme`?
Answer: It stores shared styling information for the user interface.

---

## 10. Final summary

This Inventory Management System is built using:
- Java as the main programming language
- Swing for the desktop user interface
- FlatLaf for modern styling
- Maven for project build and dependencies
- SQLite for data storage
- JDBC for database connection
- SQL for database operations

It demonstrates how a complete software system can be built by combining database handling, user interface design, and object-oriented programming in Java.
