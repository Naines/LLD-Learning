package com.nainesh.lld.ParkingLot.entities;

import java.util.Date;
import java.util.UUID;

public class Ticket {
    private String ticketId;
    private long entryTime;
    private long exitTime;
    private ParkingSpot spot;
    private Vehicle vehicle;

    public Ticket(ParkingSpot spot, Vehicle vehicle) {
        this.ticketId = UUID.randomUUID().toString();
        this.spot = spot;
        this.vehicle = vehicle;
        this.entryTime = new Date().getTime();
    }

    public String getTicketId() {
        return ticketId;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setExitTime(){
        this.exitTime = new Date().getTime() + 10*60*60*1000;
    }
}
