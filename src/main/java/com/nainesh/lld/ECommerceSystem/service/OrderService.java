package com.nainesh.lld.ECommerceSystem.service;

import com.nainesh.lld.ECommerceSystem.entity.Order;
import com.nainesh.lld.ECommerceSystem.entity.OrderItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> orderHistory = new ConcurrentHashMap<>(); //(userId-> orderId)
    private final InventoryService inventoryService;
    
    public OrderService(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    // NEW: Atomic - either all items are reserved or none (transaction semantics)
    List<OrderItem> validateAndReserveStock(List<OrderItem> orderItems){
        // First, validate ALL items have enough stock (no partial fulfillment)
        for(OrderItem oi: orderItems){
            if(inventoryService.checkCount(oi.productId) < oi.count) {
                System.out.println("Insufficient stock for product: " + oi.productId);
                return new ArrayList<>();
            }
        }
        
        // All items validated, now reserve them all
        List<OrderItem> returnList = new ArrayList<>();
        try {
            for(OrderItem oi: orderItems){
                inventoryService.removeStockCount(oi.productId, oi.count);
                returnList.add(oi);
            }
        } catch (RuntimeException e) {
            // If any reservation fails, rollback all previous reservations
            System.out.println("Error during stock reservation, rolling back: " + e.getMessage());
            for(OrderItem oi: returnList){
                inventoryService.addStockCount(oi.productId, "I3", oi.count);
            }
            throw e;
        }
        return returnList;
    }

    public String placeOrder(String userId, List<OrderItem> orderItems){
        String orderId = UUID.randomUUID().toString();
        // Validate and reserve stock atomically
        List<OrderItem> items = validateAndReserveStock(orderItems);
        printOrderSummary(items);
        if(items.isEmpty()) return "ORDER NOT PLACED DUE TO INSUFFICIENT STOCK";
        Order order=new Order(orderId, userId, items);
        orders.put(orderId, order);
        orderHistory.computeIfAbsent(userId, k-> new HashSet<String>()).add(orderId);
        System.out.println("order placed: "+orderId);
        return orderId;
    }
    
    void printOrderSummary(List<OrderItem> items){
        System.out.println("Order placed, displaying summary:");
        for(int i=0;i<items.size();i++){
           System.out.println((i+1)+" "+items.get(i));
       }
    }

    public boolean cancelOrder(String orderId){
        Order order=  orders.get(orderId);
        if(order==null) {
            throw new RuntimeException("Order Not found with the given orderId"+orderId);
        }
        order.isActive = false;
        freeStockCount(order.orderItems);
        return true;
    }

    void freeStockCount(List<OrderItem> items){
        for(OrderItem oi: items){
            inventoryService.addStockCount(oi.productId, "I3", oi.count);
        }
    }
}

//class OrderService{
//    private final Map<String, Order> orders = new ConcurrentHashMap<>();
//    private final Map<String, Set<String>> orderHistory = new ConcurrentHashMap<>(); //(userId-> orderId)
//    private final InventoryService inventoryService;
//    OrderService(InventoryService inventoryService){
//        this.inventoryService = inventoryService;
//    }
//
//    // OLD: Not atomic - can partially reserve items if one fails
//    /*
//    List<OrderItem> validateAndReserveStock(List<OrderItem> orderItems){
//        List<OrderItem> returnList=new ArrayList<>();
//        for(OrderItem oi: orderItems){
//            if(inventoryService.checkCount(oi.productId)<oi.count) continue;
//            inventoryService.removeStockCount(oi.productId, oi.count);
//            returnList.add(oi);
//        }
//        return returnList;
//    }
//    */
//
//    // NEW: Atomic - either all items are reserved or none (transaction semantics)
//    List<OrderItem> validateAndReserveStock(List<OrderItem> orderItems){
//        // First, validate ALL items have enough stock (no partial fulfillment)
//        for(OrderItem oi: orderItems){
//            if(inventoryService.checkCount(oi.productId) < oi.count) {
//                System.out.println("Insufficient stock for product: " + oi.productId);
//                return new ArrayList<>();
//            }
//        }
//
//        // All items validated, now reserve them all
//        List<OrderItem> returnList = new ArrayList<>();
//        try {
//            for(OrderItem oi: orderItems){
//                inventoryService.removeStockCount(oi.productId, oi.count);
//                returnList.add(oi);
//            }
//        } catch (RuntimeException e) {
//            // If any reservation fails, rollback all previous reservations
//            System.out.println("Error during stock reservation, rolling back: " + e.getMessage());
//            for(OrderItem oi: returnList){
//                inventoryService.addStockCount(oi.productId, "I3", oi.count);
//            }
//            throw e;
//        }
//        return returnList;
//    }
//
//    String placeOrder(String userId, List<OrderItem> orderItems){
//        String orderId = UUID.randomUUID().toString();
//        // Validate and reserve stock atomically
//        List<OrderItem> items = validateAndReserveStock(orderItems);
//        printOrderSummary(items);
//        if(items.isEmpty()) return "ORDER NOT PLACED DUE TO INSUFFICIENT STOCK";
//        Order order=new Order(orderId, userId, items);
//        orders.put(orderId, order);
//        orderHistory.computeIfAbsent(userId, k-> new HashSet<String>()).add(orderId);
//        System.out.println("order placed: "+orderId);
//        return orderId;
//    }
//    void printOrderSummary(List<OrderItem> items){
//        System.out.println("Order placed, displaying summary:");
//        for(int i=0;i<items.size();i++){
//           System.out.println((i+1)+" "+items.get(i));
//       }
//    }
//
//    boolean cancelOrder(String orderId){
//        Order order=  orders.get(orderId);
//        if(order==null) {
//            throw new RuntimeException("Order Not found with the given orderId"+orderId);
//        }
//        order.isActive = false;
//        freeStockCount(order.orderItems);
//        return true;
//    }
//
//    void freeStockCount(List<OrderItem> items){
//        for(OrderItem oi: items){
//            inventoryService.addStockCount(oi.productId, "I3", oi.count);
//        }
//    }
//}

