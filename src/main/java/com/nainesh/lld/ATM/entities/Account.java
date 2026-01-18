package com.nainesh.lld.ATM.entities;

import java.util.HashMap;
import java.util.Map;

public class Account {
    String accountNumber;
    double balance;
    private Map<String, Card> cards;

    public Account(String accountNumber, double balance) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        cards = new HashMap<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Card> getCards() {
        return cards;
    }

    public synchronized void deposit(double amount){
        balance+=amount;
    }

    public synchronized boolean withdraw(double amount){
        if(balance>=amount){
            balance-=amount;
            return true;
        }
        return false;
    }
}
