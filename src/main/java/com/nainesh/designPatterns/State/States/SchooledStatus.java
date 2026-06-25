package com.nainesh.designPatterns.State.States;

import com.nainesh.designPatterns.State.LifeStatus;
import com.nainesh.designPatterns.State.Subject;

/**
 * @author Nainesh
 */
public class SchooledStatus implements State{
    @Override
    public void performAction() {
        System.out.println("Off to work.");

    }

    @Override
    public void evaluate(Subject subject) {
        if(subject.getAge() > 30){
            System.out.println("Welcome to midlife. Enjoy the crisis. schooled -> midlife.");
            subject.setState(new MidlifeState());
        }
    }

    @Override
    public LifeStatus getStatus() {
        return LifeStatus.SCHOOLED;
    }
}
