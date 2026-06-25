package com.nainesh.lld.TrafficLight.state;

import com.nainesh.lld.TrafficLight.TrafficLight;

public class YellowLightState implements TrafficLightState{
    @Override
    public void changeLight(TrafficLight trafficLight) {
        System.out.println("Changing light to Green");
        trafficLight.setState(new GreenLightState());
    }
}
