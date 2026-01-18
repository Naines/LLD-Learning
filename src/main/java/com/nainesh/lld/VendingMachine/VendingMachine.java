package com.nainesh.lld.VendingMachine;

import com.nainesh.lld.VendingMachine.entities.Inventory;
import com.nainesh.lld.VendingMachine.entities.Item;
import com.nainesh.lld.VendingMachine.enums.Coin;
import com.nainesh.lld.VendingMachine.state.IdleState;
import com.nainesh.lld.VendingMachine.state.VendingMachineState;

public class VendingMachine {
    VendingMachineState currState;
    Inventory inventory=new Inventory();
    int balance;
    String selectedItemCode;

    public VendingMachine() {
        this.currState = new IdleState(this);
    }

    public void insertCoin(Coin coin){
     currState.insertCoin(coin);
    }

    public Item addItem(String code, String name, int price, int quantity) {
        Item item = new Item(code, name, price);
        inventory.addItem(code, item, quantity);
        return item;
    }

    public void selectItem(String code) {
        currState.selectItem(code);
    }

    public void dispense() {
        currState.dispense();
    }

    public void dispenseItem() {

        //get item from itemCode. If balance >=itemPrice -> reduceStock and deduct balance
        //
        Item item = inventory.getItem(selectedItemCode);
        if (balance >= item.getPrice()) {
            inventory.reduceStock(selectedItemCode);
            balance -= item.getPrice();
            System.out.println("Dispensed: " + item.getName());
            if (balance > 0) {
                System.out.println("Returning change: " + balance);
            }
        }
        reset();
        setState(new IdleState(this));
    }

    public void refundBalance() {
        System.out.println("Refunding: " + balance);
        balance = 0;
    }

    public void reset() {
        selectedItemCode = null;
        balance = 0;
    }

    public void addBalance(int value) {
        balance += value;
    }

    public void setSelectedItemCode(String code) {
        this.selectedItemCode = code;
    }
    public Item getSelectedItem() {
        return inventory.getItem(selectedItemCode);
    }

    public void setState(VendingMachineState vendingMachineState) {
        this.currState=vendingMachineState;
    }














    /// getters

    public VendingMachineState getCurrState() {
        return currState;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getBalance() {
        return balance;
    }

    public String getSelectedItemCode() {
        return selectedItemCode;
    }
}
