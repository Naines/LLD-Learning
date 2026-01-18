package com.nainesh.lld.BookMyShow.entities;

import com.nainesh.lld.BookMyShow.enums.PaymentStatus;

import java.util.UUID;

public class Payment {
    String id;
    double amount;
    PaymentStatus status;
    String transactionId;

    public Payment(double amount, PaymentStatus status, String transactionId) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
    }

    public PaymentStatus getStatus(){
        return status;
    }
}
