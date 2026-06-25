package com.nainesh.lld.TrafficLight;

import com.nainesh.lld.TrafficLight.state.TrafficLightState;

public class TrafficLight {
    TrafficLightState state;
    TrafficLight(TrafficLightState state) {
        this.state = state;
    }

    public void setState(TrafficLightState state) {
        this.state = state;
    }

    public void changeLight(){
        state.changeLight(this);
    }
}
