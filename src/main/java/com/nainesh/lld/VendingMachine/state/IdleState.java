package com.nainesh.lld.VendingMachine.state;

import com.nainesh.lld.VendingMachine.VendingMachine;
import com.nainesh.lld.VendingMachine.enums.Coin;

public class IdleState extends VendingMachineState {

    public IdleState(VendingMachine machine) {
        super(machine);
    }

    @Override
    public void insertCoin(Coin coin) {
        System.out.println("Please select item before inserting money");
    }

    @Override
    public void selectItem(String code) {
        //check if item is available, if available set state to hasMoney and selectItem
        if (!machine.getInventory().isAvailable(code)) {
            System.out.println("Item not available.");
            return;
        }
        machine.setSelectedItemCode(code);
        machine.setState(new ItemSelectedState(machine));
        System.out.println("Item selected: " + code);
    }

    @Override
    public void dispense() {
        System.out.println("No item selected.");
    }

    @Override
    public void refund() {
        System.out.println("No money to refund.");
    }
}
