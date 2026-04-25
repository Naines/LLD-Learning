package com.nainesh.lld.ECommerceSystem.service;


import com.nainesh.lld.ECommerceSystem.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductService{
    final Map<String, Product> productMap=new ConcurrentHashMap<>();

    public List<Product> searchProducts(String keyword) {
        List<Product> results = new ArrayList<>();
        for (Product product : productMap.values()) {
            if (product.name.toLowerCase().contains(keyword.toLowerCase()) ||
                    product.description.toLowerCase().contains(keyword.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }

    public void addProduct(Product product) {
        productMap.put(product.productId, product);
    }
}