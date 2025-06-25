package com.akzvitor.orderservice.model;

public class OrderItem {
    private String productCode;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}