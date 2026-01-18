package com.nainesh.lld.AirlineSystem.entities;

import com.nainesh.lld.AirlineSystem.enums.FlightStatus;

import java.time.LocalDateTime;
import java.util.*;

public class Flight {
    String flightNumber;
    String source, destination;
    LocalDateTime departureTime, arrivalTime;
    FlightStatus status;
    Aircraft aircraft;
    Map<String, Seat> seats;
    List<Seat> availableSeats;

    public Flight(String source, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, Aircraft aircraft) {
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.aircraft = aircraft;
        this.flightNumber = UUID.randomUUID().toString();
        this.status = FlightStatus.ON_TIME;
        this.seats = new HashMap<>();
        this.availableSeats = new ArrayList<>();
    }
    public synchronized boolean isSeatAvailable(String seatNo) {
        Seat seat = seats.get(seatNo);
        return seat != null && !seat.isBooked();
    }

    public synchronized void reserveSeat(String seatNo) {
        Seat seat = seats.get(seatNo);
        if (seat == null) throw new IllegalArgumentException("Invalid seat number");
        seat.reserve();
    }

    public synchronized void releaseSeat(String seatNo) {
        Seat seat = seats.get(seatNo);
        if (seat != null) seat.release();
    }

    /// getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }
}
