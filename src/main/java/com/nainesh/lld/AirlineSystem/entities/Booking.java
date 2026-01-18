package com.nainesh.lld.AirlineSystem.entities;

import com.nainesh.lld.AirlineSystem.enums.BookingStatus;
import com.nainesh.lld.AirlineSystem.enums.FlightStatus;

import java.util.UUID;

public class Booking {
    String id;
    Flight flight;
    Passenger passenger;
    Seat seat;
    double price;
    BookingStatus status;

    public Booking(Flight flight, Passenger passenger, double price, Seat seat) {
        this.id = UUID.randomUUID().toString();
        this.flight = flight;
        this.passenger = passenger;
        this.price = price;
        this.seat = seat;
        this.status = BookingStatus.CANCELLED;
    }
    public void cancel() {
        status = BookingStatus.CANCELLED;
        seat.release();
    }

    public String getId() {
        return id;
    }

}
