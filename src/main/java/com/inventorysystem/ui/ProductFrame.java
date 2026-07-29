package com.inventorysystem.ui;

import com.inventorysystem.models.Product;
import com.inventorysystem.services.ProductService;
import com.inventorysystem.utils.Validator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductFrame extends JFrame {
    private final ProductService productService = new ProductService();
    private final JTable table = new JTable();
    private final JTextField nameField = new JTextField();
    private final JTextField categoryField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JTextField searchField = new JTextField();
    private final JLabel selectedIdLabel = new JLabel("Selected ID: -");
    private int selectedProductId = -1;

    public ProductFrame() {
        initializeUI();
        loadProducts();
    }

    private void initializeUI() {
        setTitle("Product Management");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        formPanel.add(new JLabel("Name"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Category"));
        formPanel.add(categoryField);
        formPanel.add(new JLabel("Price"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Quantity"));
        formPanel.add(quantityField);
        formPanel.add(selectedIdLabel);
        formPanel.add(new JLabel());

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        actionsPanel.add(addButton);
        actionsPanel.add(updateButton);
        actionsPanel.add(deleteButton);
        actionsPanel.add(clearButton);

        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.add(new JLabel("Search"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton, BorderLayout.EAST);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                selectedProductId = (int) table.getValueAt(row, 0);
                selectedIdLabel.setText("Selected ID: " + selectedProductId);
                nameField.setText(String.valueOf(table.getValueAt(row, 1)));
                categoryField.setText(String.valueOf(table.getValueAt(row, 2)));
                priceField.setText(String.valueOf(table.getValueAt(row, 3)));
                quantityField.setText(String.valueOf(table.getValueAt(row, 4)));
            }
        });

        addButton.addActionListener(event -> saveProduct(false));
        updateButton.addActionListener(event -> saveProduct(true));
        deleteButton.addActionListener(event -> deleteSelectedProduct());
        clearButton.addActionListener(event -> clearForm());
        searchButton.addActionListener(event -> searchProducts());
        searchField.addActionListener(event -> searchProducts());

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(actionsPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private void loadProducts() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Category", "Price", "Quantity"}, 0);
        for (Product product : productService.getAllProducts()) {
            model.addRow(new Object[]{product.getId(), product.getName(), product.getCategory(), product.getPrice(), product.getQuantity()});
        }
        table.setModel(model);
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Category", "Price", "Quantity"}, 0);
        for (Product product : productService.searchProducts(keyword)) {
            model.addRow(new Object[]{product.getId(), product.getName(), product.getCategory(), product.getPrice(), product.getQuantity()});
        }
        table.setModel(model);
    }

    private void saveProduct(boolean update) {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (!Validator.isValidProductName(name)) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validator.isValidPrice(Double.parseDouble(priceText))) {
            JOptionPane.showMessageDialog(this, "Price must be positive", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validator.isValidQuantity(Integer.parseInt(quantityText))) {
            JOptionPane.showMessageDialog(this, "Quantity cannot be negative", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(Double.parseDouble(priceText));
        product.setQuantity(Integer.parseInt(quantityText));

        try {
            if (update && selectedProductId > 0) {
                product.setId(selectedProductId);
                productService.updateProduct(product);
                JOptionPane.showMessageDialog(this, "Product updated");
            } else {
                productService.addProduct(product);
                JOptionPane.showMessageDialog(this, "Product added");
            }
            clearForm();
            loadProducts();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedProduct() {
        if (selectedProductId <= 0) {
            JOptionPane.showMessageDialog(this, "Select a product first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Delete the selected product?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            productService.deleteProduct(selectedProductId);
            clearForm();
            loadProducts();
        }
    }

    private void clearForm() {
        selectedProductId = -1;
        selectedIdLabel.setText("Selected ID: -");
        nameField.setText("");
        categoryField.setText("");
        priceField.setText("");
        quantityField.setText("");
        searchField.setText("");
        table.clearSelection();
    }
}
