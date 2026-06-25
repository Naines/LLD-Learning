package com.nainesh.designPatterns.State.States;

import com.nainesh.designPatterns.State.LifeStatus;
import com.nainesh.designPatterns.State.Subject;

/**
 * @author Nainesh
 */
public class MidlifeState implements State{
    @Override
    public void performAction() {
        System.out.println("Off to sleep.");

    }

    @Override
    public void evaluate(Subject subject) {
        if(subject.getAge() > 35){
            System.out.println("Welcome to hell. Enjoy. midlife -> hell");
            subject.setState(new HellState());
        }
    }

    @Override
    public LifeStatus getStatus() {
        return LifeStatus.MIDLIFE;
    }
}
