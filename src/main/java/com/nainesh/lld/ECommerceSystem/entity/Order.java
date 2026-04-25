package com.nainesh.lld.ECommerceSystem.entity;

import java.util.List;

public class Order {
    public String orderId;
    public String userId;
    public List<OrderItem> orderItems;
    public boolean isActive;

    public Order(String orderId, String userId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderItems = orderItems;
        this.isActive = true;
    }
}

