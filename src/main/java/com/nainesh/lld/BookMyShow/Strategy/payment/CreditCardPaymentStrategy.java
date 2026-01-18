package com.nainesh.lld.BookMyShow.Strategy.payment;

import com.nainesh.lld.BookMyShow.entities.Payment;
import com.nainesh.lld.BookMyShow.enums.PaymentStatus;

import java.util.UUID;

public class CreditCardPaymentStrategy implements PaymentStrategy{
    String cvv, cardNumber;

    public CreditCardPaymentStrategy(String cvv, String cardNumber) {
        this.cvv = cvv;
        this.cardNumber = cardNumber;
    }

    @Override
    public Payment pay(double amount) {
        System.out.printf("Processing amt of $%.2f\n", amount);
        boolean paymentSuccess = Math.random() > 0.05; //95%success rate
        return new Payment(amount, paymentSuccess? PaymentStatus.SUCCESS: PaymentStatus.FAILURE,"TXN_"+ UUID.randomUUID().toString());
    }
}
