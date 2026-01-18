package com.nainesh.lld.AirlineSystem.entities;

import com.nainesh.lld.AirlineSystem.enums.PaymentStatus;

public class Payment {
    String paymentId;
    String paymentMethod;
    double amount;
    PaymentStatus paymentStatus;

    public Payment(String paymentId, String paymentMethod, double amount) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = PaymentStatus.PENDING;
    }
    public void processPayment() {
        // Process payment logic
        paymentStatus = PaymentStatus.COMPLETED;
    }
}
