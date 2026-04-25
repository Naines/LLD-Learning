package com.nainesh.lld.ECommerceSystem.entity;

public class Product {
    public String productId;
    public String name;
    public String description;
    public double price;

    public Product(String productId, String name, String description, double price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}

