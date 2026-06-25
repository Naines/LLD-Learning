package com.nainesh.designPatterns.State.States;

import com.nainesh.designPatterns.State.LifeStatus;
import com.nainesh.designPatterns.State.Subject;

public interface State{
    void performAction();
    void evaluate(Subject subject);
    LifeStatus getStatus();
}
