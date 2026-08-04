package com.inventorysystem.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.inventorysystem.models.Sale;
import com.inventorysystem.services.ProductService;
import com.inventorysystem.services.SaleService;

public class SaleFrame extends JPanel {
    private final SaleService saleService = new SaleService();
    private final ProductService productService = new ProductService();
    private final JTable table = new JTable();
    private final JTextField productIdField = new JTextField();
    private final JTextField quantityField = new JTextField();

    public SaleFrame() {
        initializeUI();
        loadSales();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(12, 12));
        setBackground(UITheme.BACKGROUND);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Record Sale"));
        formPanel.add(new JLabel("Product ID"));
        formPanel.add(productIdField);
        formPanel.add(new JLabel("Quantity"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        JButton recordButton = new JButton("Record Sale");
        JButton refreshButton = new JButton("Refresh");
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.add(recordButton);
        actionsPanel.add(refreshButton);

        recordButton.addActionListener(event -> recordSale());
        refreshButton.addActionListener(event -> loadSales());

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        mainPanel.setBackground(UITheme.BACKGROUND);
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(actionsPanel, BorderLayout.CENTER);
        mainPanel.add(createTableScrollPane(), BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadSales() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Product", "Quantity", "Date"}, 0);
        for (Sale sale : saleService.getAllSales()) {
            String productName = productService.getProductById(sale.getProductId()) != null ? productService.getProductById(sale.getProductId()).getName() : "Unknown";
            model.addRow(new Object[]{sale.getId(), productName, sale.getQuantity(), sale.getSaleDate()});
        }
        table.setModel(model);
    }

    private void recordSale() {
        try {
            int productId = Integer.parseInt(productIdField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            saleService.recordSale(productId, quantity);
            JOptionPane.showMessageDialog(this, "Sale recorded");
            productIdField.setText("");
            quantityField.setText("");
            loadSales();
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
