package com.nainesh.lld.TrafficLight.state;

import com.nainesh.lld.TrafficLight.TrafficLight;

public class RedLightState implements TrafficLightState{
    @Override
    public void changeLight(TrafficLight trafficLight) {
        System.out.println("Changing light to Yellow");
        trafficLight.setState(new YellowLightState());
    }
}
