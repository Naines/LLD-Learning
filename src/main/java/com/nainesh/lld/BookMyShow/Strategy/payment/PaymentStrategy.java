package com.nainesh.lld.BookMyShow.Strategy.payment;

import com.nainesh.lld.BookMyShow.entities.Payment;

public interface PaymentStrategy {
    Payment pay(double amount);
}
