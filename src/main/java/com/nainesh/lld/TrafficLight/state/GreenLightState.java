package com.nainesh.lld.TrafficLight.state;

import com.nainesh.lld.TrafficLight.TrafficLight;

public class GreenLightState implements TrafficLightState{
    @Override
    public void changeLight(TrafficLight trafficLight) {
        System.out.println("Changing light to Red");
        trafficLight.setTls(new YellowLightState());
    }
}
