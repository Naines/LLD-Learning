package com.nainesh.lld.AirlineSystem.entities;

import com.nainesh.lld.AirlineSystem.enums.SeatStatus;
import com.nainesh.lld.AirlineSystem.enums.SeatType;

public class Seat {
    String seatNumber;
    SeatType type;
    SeatStatus status;

    public Seat(String seatNumber, SeatType type) {
        this.seatNumber = seatNumber;
        this.type = type;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void reserve() {
        status = SeatStatus.RESERVED;
    }

    public void release() {
        status = SeatStatus.AVAILABLE;
    }

    public synchronized boolean isBooked() {
        return status == SeatStatus.OCCUPIED;
    }
}
