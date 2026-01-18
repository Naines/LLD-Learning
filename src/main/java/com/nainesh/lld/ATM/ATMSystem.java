package com.nainesh.lld.ATM;

import com.nainesh.lld.ATM.COR.DispenseChain;
import com.nainesh.lld.ATM.COR.NoteDispenser100;
import com.nainesh.lld.ATM.COR.NoteDispenser20;
import com.nainesh.lld.ATM.COR.NoteDispenser50;
import com.nainesh.lld.ATM.entities.BankService;
import com.nainesh.lld.ATM.entities.Card;
import com.nainesh.lld.ATM.entities.CashDispenser;
import com.nainesh.lld.ATM.enums.OperationType;
import com.nainesh.lld.ATM.state.ATMState;
import com.nainesh.lld.ATM.state.IdleState;

import java.util.concurrent.atomic.AtomicLong;

//STATES: IDLE -> HasCardState -> AuthenticatedState
 public class ATMSystem {
    private static ATMSystem INSTANCE;
    private final BankService bankService;
    private final CashDispenser cashDispenser;
    private static final AtomicLong transactionCounter = new AtomicLong(0);
    private ATMState currentState;
    private Card currentCard;

    private ATMSystem(){
        this.currentState = new IdleState();
        this.bankService = new BankService();
        // Setup the dispenser chain
        DispenseChain c1 = new NoteDispenser100(10); // 10 x $100 notes
        DispenseChain c2 = new NoteDispenser50(20); // 20 x $50 notes
        DispenseChain c3 = new NoteDispenser20(30); // 30 x $20 notes
        c1.setNextChain(c2);
        c2.setNextChain(c3);
        this.cashDispenser = new CashDispenser(c1);
    }

    public static ATMSystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ATMSystem();
        }
        return INSTANCE;
    }

    public void setCurrentState(ATMState currentState) {
        this.currentState = currentState;
    }

    public void changeState(ATMState newState) {
        this.currentState = newState;
    }


    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public void insertCard(String cardNumber) {
        currentState.insertCard(this, cardNumber);
    }

    public void enterPin(String pin) {
        currentState.enterPin(this, pin);
    }

    public void selectOperation(OperationType op, int... args) {
        currentState.selectOperation(this, op, args);
    }

    public Card getCard(String cardNumber) {
        return bankService.getCard(cardNumber);
    }


    public boolean authenticate(String pin) {
        return bankService.authenticate(currentCard, pin);
    }
    public void checkBalance() {
        double balance = bankService.getBalance(currentCard);
        System.out.printf("Your current account balance is: $%.2f%n", balance);
    }

    //withdraw from cashDispenser and account
    public void withdrawCash(int amount) {
        if (!cashDispenser.canDispenseCash(amount)) {
            throw new IllegalStateException("Insufficient cash available in the ATM.");
        }

        bankService.withdrawMoney(currentCard, amount);

        try {
            cashDispenser.dispenseCash(amount);
        } catch (Exception e) {
            bankService.depositMoney(currentCard, amount); // Deposit back if dispensing fails
        }
    }

    public void depositCash(int amount) {
        bankService.depositMoney(currentCard, amount);
    }

    public BankService getBankService() {
        return bankService;
    }

    public Card getCurrentCard() {
        return currentCard;
    }
}
