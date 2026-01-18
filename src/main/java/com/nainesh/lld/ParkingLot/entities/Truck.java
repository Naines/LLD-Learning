package com.nainesh.lld.ParkingLot.entities;

import com.nainesh.lld.ParkingLot.enums.Size;

public class Truck extends Vehicle{
    public Truck(String licenseNumber) {
        super(licenseNumber, Size.LARGE);
    }
}
