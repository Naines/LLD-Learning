package com.nainesh.lld.ATM.state;

import com.nainesh.lld.ATM.ATMSystem;
import com.nainesh.lld.ATM.enums.OperationType;

public interface    ATMState {
    void insertCard(ATMSystem atmSystem, String cardNumber);
    void enterPin(ATMSystem atmSystem, String pin);
    void selectOperation(ATMSystem atmSystem, OperationType op, int... args);
    void ejectCard(ATMSystem atmSystem);
}
