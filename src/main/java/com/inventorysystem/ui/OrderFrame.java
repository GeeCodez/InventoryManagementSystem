package com.inventorysystem.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.inventorysystem.models.Order;
import com.inventorysystem.models.Product;
import com.inventorysystem.services.OrderService;
import com.inventorysystem.services.ProductService;

public class OrderFrame extends JPanel {
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();
    private final JTable table = new JTable();
    private final JTextField productIdField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "Delivered", "Cancelled"});
    private int selectedOrderId = -1;

    public OrderFrame() {
        initializeUI();
        loadOrders();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(12, 12));
        setBackground(UITheme.BACKGROUND);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        formPanel.add(new JLabel("Product ID"));
        formPanel.add(productIdField);
        formPanel.add(new JLabel("Quantity"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Status"));
        formPanel.add(statusComboBox);
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createButton = new JButton("Create Order");
        JButton updateButton = new JButton("Set Delivered");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        actionsPanel.add(createButton);
        actionsPanel.add(updateButton);
        actionsPanel.add(deleteButton);
        actionsPanel.add(refreshButton);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                selectedOrderId = (int) table.getValueAt(row, 0);
                productIdField.setText(String.valueOf(table.getValueAt(row, 1)));
                quantityField.setText(String.valueOf(table.getValueAt(row, 2)));
                statusComboBox.setSelectedItem(table.getValueAt(row, 3));
            }
        });

        createButton.addActionListener(event -> createOrder());
        updateButton.addActionListener(event -> updateStatus());
        deleteButton.addActionListener(event -> deleteOrder());
        refreshButton.addActionListener(event -> loadOrders());

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(actionsPanel, BorderLayout.CENTER);
        mainPanel.add(createTableScrollPane(), BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadOrders() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Product", "Quantity", "Status", "Date"}, 0);
        for (Order order : orderService.getAllOrders()) {
            Product product = productService.getProductById(order.getProductId());
            String productName = product != null ? product.getName() : "Unknown";
            model.addRow(new Object[]{order.getId(), productName, order.getQuantity(), order.getStatus(), order.getOrderDate()});
        }
        table.setModel(model);
    }

    private void createOrder() {
        try {
            int productId = Integer.parseInt(productIdField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            Product product = productService.getProductById(productId);
            if (product == null) {
                JOptionPane.showMessageDialog(this, "Please select an existing product ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Order order = new Order();
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setStatus("Pending");
            order.setOrderDate(LocalDate.now().toString());
            orderService.addOrder(order);
            JOptionPane.showMessageDialog(this, "Order created");
            clearForm();
            loadOrders();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus() {
        if (selectedOrderId <= 0) {
            JOptionPane.showMessageDialog(this, "Select an order first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        orderService.updateOrderStatus(selectedOrderId, statusComboBox.getSelectedItem().toString());
        JOptionPane.showMessageDialog(this, "Order status updated");
        loadOrders();
    }

    private void deleteOrder() {
        if (selectedOrderId <= 0) {
            JOptionPane.showMessageDialog(this, "Select an order first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Delete the selected order?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            orderService.deleteOrder(selectedOrderId);
            clearForm();
            loadOrders();
        }
    }

    private void clearForm() {
        selectedOrderId = -1;
        productIdField.setText("");
        quantityField.setText("");
        statusComboBox.setSelectedItem("Pending");
        table.clearSelection();
    }
    private JScrollPane createTableScrollPane() {
        UITheme.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        return scrollPane;
    }

}
