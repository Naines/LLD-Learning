package com.nainesh.designPatterns.State.States;

import com.nainesh.designPatterns.State.LifeStatus;
import com.nainesh.designPatterns.State.Subject;

/**
 * @author Nainesh
 */
public class BornState implements State{

    @Override
    public void performAction() {
        System.out.println(" Off to school.");
    }

    @Override
    public void evaluate(Subject subject) {
        if(subject.getAge() > 22){
            System.out.println("Welcome to real world. born-> schooled");
            subject.setState(new SchooledStatus());
        }
    }

    @Override
    public LifeStatus getStatus() {
        return LifeStatus.BORN;
    }
}