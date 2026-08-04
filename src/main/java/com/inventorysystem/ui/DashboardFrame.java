package com.inventorysystem.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.inventorysystem.services.OrderService;
import com.inventorysystem.services.ProductService;
import com.inventorysystem.services.SaleService;

public class DashboardFrame extends JFrame {
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final SaleService saleService = new SaleService();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentContainer = new JPanel(cardLayout);
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
        sidebar.add(navButton("Dashboard", () -> showView("dashboard")));
        sidebar.add(navButton("Products", () -> showView("products")));
        sidebar.add(navButton("Orders", () -> showView("orders")));
        sidebar.add(navButton("Deliveries", () -> showView("deliveries")));
        sidebar.add(navButton("Sales", () -> showView("sales")));
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
        contentPanel.setLayout(new BorderLayout());
        contentContainer.setOpaque(false);
        contentContainer.add(createDashboardView(), "dashboard");
        contentContainer.add(new ProductFrame(), "products");
        contentContainer.add(new OrderFrame(), "orders");
        contentContainer.add(new DeliveryFrame(), "deliveries");
        contentContainer.add(new SaleFrame(), "sales");
        contentPanel.add(contentContainer, BorderLayout.CENTER);
        return contentPanel;
    }

    private JPanel createDashboardView() {
        JPanel dashboardView = new JPanel(new BorderLayout(16, 16));
        dashboardView.setOpaque(false);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JPanel headerText = new JPanel();
        headerText.setOpaque(false);
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.add(UITheme.title("Business Dashboard"));
        headerText.add(UITheme.subtitle("Track stock, orders, deliveries and sales from one simple screen."));
        headerPanel.add(headerText, BorderLayout.WEST);

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 12, 12));
        cardsPanel.setOpaque(false);
        cardsPanel.add(createMetricCard("Total Products", "0", new Color(37, 99, 235)));
        cardsPanel.add(createMetricCard("Low Stock", "0", new Color(245, 158, 11)));
        cardsPanel.add(createMetricCard("Total Sales", "0", new Color(16, 185, 129)));
        cardsPanel.add(createMetricCard("Pending Orders", "0", new Color(139, 92, 246)));

        JPanel welcomeCard = UITheme.cardPanel();
        welcomeCard.setLayout(new BorderLayout(8, 8));
        welcomeCard.add(UITheme.title("Welcome back, admin"), BorderLayout.NORTH);
        welcomeCard.add(UITheme.subtitle("Use the left menu to manage products, create supplier orders, record deliveries, and sell available stock."), BorderLayout.CENTER);

        dashboardView.add(headerPanel, BorderLayout.NORTH);
        dashboardView.add(cardsPanel, BorderLayout.CENTER);
        dashboardView.add(welcomeCard, BorderLayout.SOUTH);
        return dashboardView;
    }

    private JPanel createMetricCard(String title, String value, Color accent) {
        JPanel card = UITheme.cardPanel();
        card.setLayout(new BorderLayout(8, 8));
        card.setPreferredSize(new Dimension(0, 112));

        JLabel titleLabel = UITheme.subtitle(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        valueLabel.setForeground(UITheme.TEXT);

        JPanel accentBar = new JPanel();
        accentBar.setPreferredSize(new Dimension(4, 0));
        accentBar.setBackground(accent);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(Box.createVerticalGlue());
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(4));
        content.add(valueLabel);
        content.add(Box.createVerticalGlue());

        card.add(accentBar, BorderLayout.WEST);
        card.add(content, BorderLayout.CENTER);
        metricLabels.put(title, valueLabel);
        return card;
    }

    private void showView(String viewName) {
        cardLayout.show(contentContainer, viewName);
    }

    private void refreshMetrics() {
        metricLabels.get("Total Products").setText(String.valueOf(productService.getAllProducts().size()));
        metricLabels.get("Low Stock").setText(String.valueOf(productService.getLowStockCount()));
        metricLabels.get("Total Sales").setText(String.valueOf(saleService.getSalesCount()));
        metricLabels.get("Pending Orders").setText(String.valueOf(orderService.getPendingOrderCount()));
    }
}
