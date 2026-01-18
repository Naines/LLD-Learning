package com.nainesh.lld.AirlineSystem.entities;

import java.util.UUID;

public class Passenger {
    String id, name, email;

    public Passenger(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }
}
