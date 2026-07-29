package com.inventorysystem.models;

public class Order {
    private int id;
    private int productId;
    private int quantity;
    private String status;
    private String orderDate;

    public Order() {
    }

    public Order(int id, int productId, int quantity, String status, String orderDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.orderDate = orderDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
