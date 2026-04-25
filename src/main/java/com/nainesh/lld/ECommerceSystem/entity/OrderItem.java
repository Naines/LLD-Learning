package com.nainesh.lld.ECommerceSystem.entity;

public class OrderItem {
    public String productId;
    public int count;

    public OrderItem(String productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId='" + productId + '\'' +
                ", count=" + count +
                '}';
    }
}

