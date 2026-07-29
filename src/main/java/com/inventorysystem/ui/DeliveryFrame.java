package com.inventorysystem.ui;

import com.inventorysystem.models.Delivery;
import com.inventorysystem.models.Order;
import com.inventorysystem.models.Product;
import com.inventorysystem.services.DeliveryService;
import com.inventorysystem.services.OrderService;
import com.inventorysystem.services.ProductService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DeliveryFrame extends JFrame {
    private final DeliveryService deliveryService = new DeliveryService();
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();
    private final JTable table = new JTable();
    private final JTextField orderIdField = new JTextField();
    private final JTextField quantityField = new JTextField();

    public DeliveryFrame() {
        initializeUI();
        loadDeliveries();
    }

    private void initializeUI() {
        setTitle("Delivery Management");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Record Delivery"));
        formPanel.add(new JLabel("Order ID"));
        formPanel.add(orderIdField);
        formPanel.add(new JLabel("Quantity"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        JButton recordButton = new JButton("Record Delivery");
        JButton refreshButton = new JButton("Refresh");
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.add(recordButton);
        actionsPanel.add(refreshButton);

        recordButton.addActionListener(event -> recordDelivery());
        refreshButton.addActionListener(event -> loadDeliveries());

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(actionsPanel, BorderLayout.CENTER);
        mainPanel.add(createTableScrollPane(), BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private void loadDeliveries() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Order", "Product", "Quantity", "Date"}, 0);
        for (Delivery delivery : deliveryService.getAllDeliveries()) {
            Order order = orderService.getOrderById(delivery.getOrderId());
            Product product = productService.getProductById(delivery.getProductId());
            model.addRow(new Object[]{delivery.getId(), order != null ? order.getId() : "-", product != null ? product.getName() : "-", delivery.getQuantity(), delivery.getDeliveryDate()});
        }
        table.setModel(model);
    }

    private void recordDelivery() {
        try {
            int orderId = Integer.parseInt(orderIdField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            deliveryService.recordDelivery(orderId, quantity);
            JOptionPane.showMessageDialog(this, "Delivery recorded");
            orderIdField.setText("");
            quantityField.setText("");
            loadDeliveries();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private JScrollPane createTableScrollPane() {
        UITheme.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        return scrollPane;
    }

}
