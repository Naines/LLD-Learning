package com.nainesh.lld.ECommerceSystem;
import com.nainesh.lld.ECommerceSystem.entity.OrderItem;
import com.nainesh.lld.ECommerceSystem.entity.Product;
import com.nainesh.lld.ECommerceSystem.service.InventoryService;
import com.nainesh.lld.ECommerceSystem.service.OrderService;
import com.nainesh.lld.ECommerceSystem.service.ProductService;
import java.util.*;

public class Main {
    static InventoryService inventoryManager;

    public static void main(String[] args) {
        String productId = "P1";
        String productId2 = "P2";
        String itemId1="I1";
        String itemId2="I2";
        String itemId3="I3";
        String userId1="U1";
        String userId2="U2";

        ProductService productService  =new ProductService();
        productService.addProduct(new Product(productId, "Soap","Glycerin soap",40.0));
        productService.addProduct(new Product(productId2, "Charger","Type-C- Cable included, 65W",1000.50));
        System.out.println( productService.searchProducts("a") );


        inventoryManager = new InventoryService();
        inventoryManager.addItems(productId, itemId1, 5);
        inventoryManager.addItems(productId, itemId2, 5);
        inventoryManager.addItems(productId, itemId3, 5);

        inventoryManager.addItems(productId2, itemId1, 5);
        inventoryManager.addItems(productId2, itemId2, 5);
        inventoryManager.addItems(productId2, itemId3, 5);
        int checkCount = inventoryManager.checkCount(productId);
        System.out.println("Initial stock for P1: " + checkCount);

        System.out.println("\n=== Multithreaded Order Operations ===");
        OrderService orderService=new OrderService(inventoryManager);
        
        // Store order IDs for cancellation later
        List<String> orderIds = new ArrayList<>();
        
        // Thread 1: Place Order
        Thread orderThread1 = new Thread(() -> {
            try {
                System.out.println("[Thread-1] User " + userId1 + " placing order...");
                OrderItem o1=new OrderItem(productId, 3);
                OrderItem o2=new OrderItem(productId2, 2);
                String orderId = orderService.placeOrder(userId1, Arrays.asList(o1, o2));
                synchronized(orderIds) {
                    orderIds.add(orderId);
                }
                System.out.println("[Thread-1] Order placed: " + orderId);
            } catch (Exception e) {
                System.out.println("[Thread-1] Error: " + e.getMessage());
            }
        });

        // Thread 2: Place Order
        Thread orderThread2 = new Thread(() -> {
            try {
                System.out.println("[Thread-2] User " + userId2 + " placing order...");
                OrderItem o1=new OrderItem(productId, 2);
                OrderItem o2=new OrderItem(productId2, 3);
                String orderId = orderService.placeOrder(userId2, Arrays.asList(o1, o2));
                synchronized(orderIds) {
                    orderIds.add(orderId);
                }
                System.out.println("[Thread-2] Order placed: " + orderId);
            } catch (Exception e) {
                System.out.println("[Thread-2] Error: " + e.getMessage());
            }
        });

        // Start order placement threads
        orderThread1.start();
        orderThread2.start();

        // Wait for order placement to complete
        try {
            orderThread1.join();
            orderThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== Stock after placing orders ===");
        checkCount = inventoryManager.checkCount(productId);
        System.out.println("Stock for P1: " + checkCount);
        int checkCount1 = inventoryManager.checkCount(productId2);
        System.out.println("Stock for P2: " + checkCount1);

        System.out.println("\n=== Multithreaded Order Cancellation ===");
        
        // Thread 3: Cancel Order
        Thread cancelThread1 = new Thread(() -> {
            try {
                synchronized(orderIds) {
                    if (!orderIds.isEmpty()) {
                        String orderId = orderIds.get(0);
                        System.out.println("[Thread-3] Cancelling order: " + orderId);
                        boolean cancelled = orderService.cancelOrder(orderId);
                        System.out.println("[Thread-3] Order cancelled: " + cancelled);
                    }
                }
            } catch (Exception e) {
                System.out.println("[Thread-3] Error: " + e.getMessage());
            }
        });

        // Thread 4: Cancel Order
        Thread cancelThread2 = new Thread(() -> {
            try {
                synchronized(orderIds) {
                    if (orderIds.size() > 1) {
                        String orderId = orderIds.get(1);
                        System.out.println("[Thread-4] Cancelling order: " + orderId);
                        boolean cancelled = orderService.cancelOrder(orderId);
                        System.out.println("[Thread-4] Order cancelled: " + cancelled);
                    }
                }
            } catch (Exception e) {
                System.out.println("[Thread-4] Error: " + e.getMessage());
            }
        });

        // Start cancellation threads
        cancelThread1.start();
        cancelThread2.start();

        // Wait for cancellation to complete
        try {
            cancelThread1.join();
            cancelThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== Stock after cancelling orders ===");
        checkCount = inventoryManager.checkCount(productId);
        System.out.println("Stock for P1: " + checkCount);
        checkCount1 = inventoryManager.checkCount(productId2);
        System.out.println("Stock for P2: " + checkCount1);
        
        System.out.println("\n=== Program Completed ===");
    }
}
