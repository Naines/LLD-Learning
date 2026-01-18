package com.nainesh.lld.VendingMachine.state;

import com.nainesh.lld.VendingMachine.VendingMachine;
import com.nainesh.lld.VendingMachine.enums.Coin;

public class DispensingState extends VendingMachineState {

    public DispensingState(VendingMachine machine) {
        super(machine);
    }

    @Override
    public void insertCoin(Coin coin) {
        System.out.println("Currently dispensing, please wait");
    }

    @Override
    public void selectItem(String code) {
        System.out.println("Currently dispensing. Please wait.");
    }

    @Override
    public void dispense() {
        // already triggered by HasMoneyState
    }

    @Override
    public void refund() {
        System.out.println("Dispensing in progress. Refund not allowed.");
    }
}
