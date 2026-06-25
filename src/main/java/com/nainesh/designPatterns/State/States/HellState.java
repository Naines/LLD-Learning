package com.nainesh.designPatterns.State.States;

import com.nainesh.designPatterns.State.LifeStatus;
import com.nainesh.designPatterns.State.Subject;

/**
 * @author Nainesh
 */
public class HellState implements State{
    @Override
    public void performAction() {
        System.out.println("Off to bed. ");

    }

    @Override
    public void evaluate(Subject subject) {
        if(subject.getAge() > 60){
            System.out.println("You may rest now. hell -> dead");
            subject.setState(new DeadState());
        }
    }

    @Override
    public LifeStatus getStatus() {
        return LifeStatus.HELL;
    }
}
