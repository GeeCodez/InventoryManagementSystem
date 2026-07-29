package com.inventorysystem.ui;

import com.inventorysystem.services.OrderService;
import com.inventorysystem.services.ProductService;
import com.inventorysystem.services.SaleService;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardFrame extends JFrame {
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final SaleService saleService = new SaleService();
    private final Map<String, JLabel> metricLabels = new LinkedHashMap<>();

    public DashboardFrame() {
        initializeUI();
        refreshMetrics();
    }

    private void initializeUI() {
        setTitle("StockFlow Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(980, 620));
        setLocationRelativeTo(null);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(UITheme.BACKGROUND);
        rootPanel.add(createSidebar(), BorderLayout.WEST);
        rootPanel.add(createMainContent(), BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(UITheme.SIDEBAR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 18, 24, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel brand = new JLabel("StockFlow");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 24));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel caption = new JLabel("Inventory workspace");
        caption.setForeground(new Color(203, 213, 225));
        caption.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(brand);
        sidebar.add(caption);
        sidebar.add(Box.createVerticalStrut(32));
        sidebar.add(navButton("Products", () -> new ProductFrame().setVisible(true)));
        sidebar.add(navButton("Orders", () -> new OrderFrame().setVisible(true)));
        sidebar.add(navButton("Deliveries", () -> new DeliveryFrame().setVisible(true)));
        sidebar.add(navButton("Sales", () -> new SaleFrame().setVisible(true)));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(navButton("Refresh dashboard", this::refreshMetrics));
        sidebar.add(navButton("Logout", () -> {
            dispose();
            new LoginFrame().setVisible(true);
        }));
        return sidebar;
    }

    private JButton navButton(String title, Runnable action) {
        JButton button = new JButton(title);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(new Color(30, 41, 59));
        button.setForeground(Color.WHITE);
        button.addActionListener(event -> action.run());
        return button;
    }

    private JPanel createMainContent() {
        JPanel contentPanel = UITheme.pagePanel();

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.add(UITheme.title("Business Dashboard"));
        headerText.add(UITheme.subtitle("Track stock, orders, deliveries and sales from one simple screen."));
        headerPanel.add(headerText, BorderLayout.WEST);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 16, 16));
        cardsPanel.setOpaque(false);
        cardsPanel.add(createMetricCard("Total Products", "0", new Color(37, 99, 235)));
        cardsPanel.add(createMetricCard("Low Stock", "0", new Color(245, 158, 11)));
        cardsPanel.add(createMetricCard("Total Sales", "0", new Color(16, 185, 129)));
        cardsPanel.add(createMetricCard("Pending Orders", "0", new Color(139, 92, 246)));

        JPanel welcomeCard = UITheme.cardPanel();
        welcomeCard.setLayout(new BorderLayout(8, 8));
        welcomeCard.add(UITheme.title("Welcome back, admin"), BorderLayout.NORTH);
        welcomeCard.add(UITheme.subtitle("Use the left menu to manage products, create supplier orders, record deliveries, and sell available stock."), BorderLayout.CENTER);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(cardsPanel, BorderLayout.CENTER);
        contentPanel.add(welcomeCard, BorderLayout.SOUTH);
        return contentPanel;
    }

    private JPanel createMetricCard(String title, String value, Color accent) {
        JPanel card = UITheme.cardPanel();
        card.setLayout(new BorderLayout(8, 8));
        JLabel titleLabel = UITheme.subtitle(title);
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        valueLabel.setForeground(UITheme.TEXT);
        JPanel accentBar = new JPanel();
        accentBar.setPreferredSize(new Dimension(5, 0));
        accentBar.setBackground(accent);
        card.add(accentBar, BorderLayout.WEST);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        metricLabels.put(title, valueLabel);
        return card;
    }

    private void refreshMetrics() {
        metricLabels.get("Total Products").setText(String.valueOf(productService.getAllProducts().size()));
        metricLabels.get("Low Stock").setText(String.valueOf(productService.getLowStockCount()));
        metricLabels.get("Total Sales").setText(String.valueOf(saleService.getSalesCount()));
        metricLabels.get("Pending Orders").setText(String.valueOf(orderService.getPendingOrderCount()));
    }
}
