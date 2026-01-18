package com.nainesh.lld.ParkingLot.entities;

import com.nainesh.lld.ParkingLot.enums.Size;
import com.nainesh.lld.ParkingLot.enums.VehicleType;

abstract public class Vehicle {
    private String licenseNumber;
    private Size size;

    public Vehicle(String licenseNumber, Size size) {
        this.licenseNumber = licenseNumber;
        this.size = size;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
