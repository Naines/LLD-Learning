package com.nainesh.designPatterns.State.States;

import com.nainesh.designPatterns.State.LifeStatus;
import com.nainesh.designPatterns.State.Subject;

/**
 * @author Nainesh
 */
public class DeadState implements State {
    @Override
    public void performAction() {
        System.out.println("Off to heaven.");
    }

    @Override
    public void evaluate(Subject subject) {
       throw new IllegalStateException("Should not reach here.");
    }

    @Override
    public LifeStatus getStatus() {
        return LifeStatus.DEAD;
    }
}
