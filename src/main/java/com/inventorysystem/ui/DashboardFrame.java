package com.inventorysystem.ui;

import com.inventorysystem.services.OrderService;
import com.inventorysystem.services.ProductService;
import com.inventorysystem.services.SaleService;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final SaleService saleService = new SaleService();

    public DashboardFrame() {
        initializeUI();
        refreshMetrics();
    }

    private void initializeUI() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Business Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 12, 12));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        cardsPanel.add(createCard("Total Products", "0"));
        cardsPanel.add(createCard("Low Stock", "0"));
        cardsPanel.add(createCard("Total Sales", "0"));
        cardsPanel.add(createCard("Pending Orders", "0"));

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        buttonsPanel.add(createActionButton("Manage Products", () -> new ProductFrame().setVisible(true)));
        buttonsPanel.add(createActionButton("Manage Orders", () -> new OrderFrame().setVisible(true)));
        buttonsPanel.add(createActionButton("Manage Deliveries", () -> new DeliveryFrame().setVisible(true)));
        buttonsPanel.add(createActionButton("Manage Sales", () -> new SaleFrame().setVisible(true)));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(event -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JPanel contentPanel = new JPanel(new BorderLayout(16, 16));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(cardsPanel, BorderLayout.CENTER);
        contentPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutButton);
        contentPanel.add(bottomPanel, BorderLayout.EAST);

        setContentPane(contentPanel);
    }

    private void refreshMetrics() {
        ((JLabel) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponent(1)).setText(String.valueOf(productService.getAllProducts().size()));
        ((JLabel) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(1)).getComponent(1)).setText(String.valueOf(productService.getLowStockCount()));
        ((JLabel) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(2)).getComponent(1)).setText(String.valueOf(saleService.getSalesCount()));
        ((JLabel) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(3)).getComponent(1)).setText(String.valueOf(orderService.getPendingOrderCount()));
    }

    private JPanel createCard(String title, String value) {
        JPanel card = new JPanel();
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JButton createActionButton(String title, Runnable action) {
        JButton button = new JButton(title);
        button.addActionListener(event -> action.run());
        return button;
    }
}
