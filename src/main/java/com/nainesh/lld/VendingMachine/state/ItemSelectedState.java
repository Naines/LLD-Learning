package com.nainesh.lld.VendingMachine.state;

import com.nainesh.lld.VendingMachine.VendingMachine;
import com.nainesh.lld.VendingMachine.enums.Coin;

public class ItemSelectedState extends VendingMachineState{

    public ItemSelectedState(VendingMachine machine) {
        super(machine);
    }

    @Override
    public void insertCoin(Coin coin) {
        //addBalance to machine, and move state to dispense
        //if insufficient state, move to HasMoneyState to get more money
        machine.addBalance(coin.getValue());
        System.out.println("Coin Inserted: " + coin.getValue());
        int price = machine.getSelectedItem().getPrice();
        if (machine.getBalance() >= price) {
            System.out.println("Sufficient money received.");
            machine.setState(new HasMoneyState(machine));
        }
    }

    @Override
    public void selectItem(String code) {
        System.out.println("Item already selected.");
    }

    @Override
    public void dispense() {

    }

    @Override
    public void refund() {

    }
}
