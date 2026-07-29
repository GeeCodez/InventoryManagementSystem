package com.inventorysystem.ui;

import com.inventorysystem.services.LoginService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final LoginService loginService;

    public LoginFrame() {
        this.loginService = new LoginService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Inventory System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(titleLabel, constraints);

        constraints.gridy = 1;
        add(new JLabel("Username"), constraints);
        constraints.gridy = 2;
        add(usernameField, constraints);
        constraints.gridy = 3;
        add(new JLabel("Password"), constraints);
        constraints.gridy = 4;
        add(passwordField, constraints);
        constraints.gridy = 5;
        add(loginButton, constraints);

        loginButton.addActionListener(event -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (loginService.authenticate(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful");
                dispose();
                new DashboardFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
