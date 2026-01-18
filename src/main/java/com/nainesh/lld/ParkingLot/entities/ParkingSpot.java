package com.nainesh.lld.ParkingLot.entities;

import com.nainesh.lld.ParkingLot.enums.Size;

import java.util.Map;

public class ParkingSpot {
    private String spotId;
    private Vehicle vehicle;
    private boolean isAvailable;
    private Size size;

    public ParkingSpot(String spotId, Size size) {
        this.spotId =spotId;
        this.size = size;
        this.vehicle = null;
        this.isAvailable = true;
    }

    public String getSpotId() {
        return spotId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Size getSize() {
        return size;
    }

    public void parkVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        this.isAvailable = false;
    }

    public void unParkVehicle(){
        this.vehicle = null;
        this.isAvailable = true;
    }

}
