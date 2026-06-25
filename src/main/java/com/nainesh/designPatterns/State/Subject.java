package com.nainesh.designPatterns.State;

import com.nainesh.designPatterns.State.States.BornState;
import com.nainesh.designPatterns.State.States.State;

/**
 * @author Nainesh
 */
public class Subject {

    private String name;
    private State state;
    private int age;
    Subject(String name){
        this.name = name;
        this.state = new BornState();
        this.age = 0 ;
        System.out.println("Hello to the world, Enjoy."+name);
    }

    public void grow(int toAge) throws IllegalStateException{
        setAge(toAge);
        while (true) {
            LifeStatus curr = state.getStatus();
            state.evaluate(this);
            state.performAction();//on basis of state, hence stateobj is used by context to store actions and rules
            //Using separate BornState, SchooledState, etc. objects
            //there is overengineering unless each stage has different actions and rules.

            if (curr == state.getStatus()) {
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}