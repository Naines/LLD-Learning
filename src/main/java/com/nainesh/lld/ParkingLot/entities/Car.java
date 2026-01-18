package com.nainesh.lld.ParkingLot.entities;

import com.nainesh.lld.ParkingLot.enums.Size;

public class Car extends Vehicle{

    public Car(String licenseNumber) {
        super(licenseNumber, Size.MEDIUM);
    }
}
