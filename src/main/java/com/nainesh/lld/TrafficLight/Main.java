package com.nainesh.lld.TrafficLight;

import com.nainesh.lld.TrafficLight.state.RedLightState;

public class Main {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight(new RedLightState());
        trafficLight.changeLight();
        trafficLight.changeLight();
        trafficLight.changeLight();
    }
}
