package com.inventorysystem.models;

public class Sale {
    private int id;
    private int productId;
    private int quantity;
    private String saleDate;

    public Sale() {
    }

    public Sale(int id, int productId, int quantity, String saleDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
