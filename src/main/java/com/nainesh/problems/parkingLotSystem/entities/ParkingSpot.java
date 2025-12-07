package com.nainesh.problems.parkingLotSystem.entities;

import com.nainesh.problems.parkingLotSystem.vehicle.Vehicle;
import com.nainesh.problems.parkingLotSystem.vehicle.VehicleSize;

public class ParkingSpot {

    private final String spotId;
    private boolean isOccupied;
    private Vehicle vehicle;
    private final VehicleSize size;

    public ParkingSpot(String spotId, VehicleSize size) {
        //spot creation
        this.spotId = spotId;
        this.vehicle = null;
        this.size = size;
        this.isOccupied = false;
    }

    public String getSpotId() {
        return spotId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleSize getSize() {
        return size;
    }

    public synchronized boolean isAvailable(){
        return !isOccupied;
    }

    public synchronized void parkVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public synchronized void unParkVehicle(){
        this.vehicle = null;
        this.isOccupied = false;
    }

    public boolean canFitVehicle(Vehicle vehicle){
        if(isOccupied) return false;
        switch(vehicle.getSize()){
            case SMALL:
                return size == VehicleSize.SMALL;
            case MEDIUM:
                return size== VehicleSize.MEDIUM;
            case LARGE:
                return size == VehicleSize.LARGE;
            default:
                return false;
        }
        /**
         * Advanced Switch
         * return switch (vehicle.getSize()) {
         *             case SMALL -> size == VehicleSize.SMALL;
         *             case MEDIUM -> size == VehicleSize.MEDIUM;
         *             case LARGE -> size == VehicleSize.LARGE;
         *             default -> false;
         *         };
         */
    }
}
