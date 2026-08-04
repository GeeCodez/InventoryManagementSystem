package com.inventorysystem.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.inventorysystem.services.LoginService;

public class LoginFrame extends JFrame {
    private final LoginService loginService;

    public LoginFrame() {
        this.loginService = new LoginService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("StockFlow Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(UITheme.BACKGROUND);
        root.add(createHeroPanel());
        root.add(createLoginPanel());
        setContentPane(root);
        setVisible(true);
    }

    private JPanel createHeroPanel() {
        JPanel hero = new JPanel(new GridBagLayout());
        hero.setBackground(UITheme.SIDEBAR);
        hero.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel brand = new JLabel("StockFlow");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 38));
        JLabel message = new JLabel("Simple inventory management for small businesses.");
        message.setForeground(new Color(203, 213, 225));
        message.setFont(new Font("SansSerif", Font.PLAIN, 16));
        text.add(brand);
        text.add(Box.createVerticalStrut(12));
        text.add(message);
        hero.add(text);
        return hero;
    }

    private JPanel createLoginPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BACKGROUND);
        JPanel card = UITheme.cardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(330, 330));

        JTextField usernameField = new JTextField("admin", 18);
        JPasswordField passwordField = new JPasswordField("admin123", 18);
        JButton loginButton = UITheme.primaryButton("Login");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        card.add(UITheme.title("Welcome back"), constraints);
        constraints.gridy++;
        card.add(UITheme.subtitle("Sign in with the default student project account."), constraints);
        constraints.gridy++;
        card.add(new JLabel("Username"), constraints);
        constraints.gridy++;
        card.add(usernameField, constraints);
        constraints.gridy++;
        card.add(new JLabel("Password"), constraints);
        constraints.gridy++;
        card.add(passwordField, constraints);
        constraints.gridy++;
        card.add(loginButton, constraints);

        loginButton.addActionListener(event -> login(usernameField, passwordField));
        passwordField.addActionListener(event -> login(usernameField, passwordField));
        wrapper.add(card);
        return wrapper;
    }

    private void login(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (loginService.authenticate(username, password)) {
            dispose();
            new DashboardFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
