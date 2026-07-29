package com.inventorysystem;

import com.formdev.flatlaf.FlatLightLaf;
import com.inventorysystem.database.DBConnection;
import com.inventorysystem.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        DBConnection.initializeDatabase();
        java.awt.EventQueue.invokeLater(LoginFrame::new);
    }
}
