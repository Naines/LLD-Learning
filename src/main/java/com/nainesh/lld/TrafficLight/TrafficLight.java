package com.nainesh.lld.TrafficLight;

import com.nainesh.lld.TrafficLight.state.TrafficLightState;

public class TrafficLight {
    TrafficLightState tls;
    TrafficLight(TrafficLightState tls) {
        this.tls = tls;
    }

    public void setTls(TrafficLightState tls) {
        this.tls = tls;
    }

    public void changeLight(){
        tls.changeLight(this);
    }
}
