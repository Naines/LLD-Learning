package com.nainesh.lld.ECommerceSystem.entity;

public class Item {
    public String itemId;
    public String productId;
    public int available;
    public String warehouseId;

    public Item(String itemId, String productId, int available) {
        this.itemId = itemId;
        this.productId = productId;
        this.available = available;
        this.warehouseId = "W123";
    }
}

