package com.inventorysystem.utils;

public class Validator {
    public static boolean isValidProductName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidPrice(double price) {
        return price > 0;
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity >= 0;
    }
}
