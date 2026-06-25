package com.nainesh.designPatterns.Observer;

/**
 * @author Nainesh
 */
public class Subscriber implements Observer{
    private final String name;
    public Subscriber(String name){
        this.name = name;
    }
    @Override
    public void update(String msg){
        System.out.println(this.name + " received notification "+msg);
    }
}
