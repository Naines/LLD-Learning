package com.nainesh.lld.AirlineSystem;

import com.nainesh.lld.AirlineSystem.entities.*;
import com.nainesh.lld.AirlineSystem.enums.SeatType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AirlineSystem AirlineSystem = new AirlineSystem();

        // Create passengers
        Passenger passenger1 = AirlineSystem.addPassenger("John Doe", "john@example.com");
        Passenger passenger2 = AirlineSystem.addPassenger("John Smith", "smith@example.com");

        // Create aircrafts
        Aircraft aircraft1 = AirlineSystem.addAircraft("A001", "Boeing 747", 300);
        Aircraft aircraft2 = AirlineSystem.addAircraft("A002", "Airbus A380", 500);

        // Create flights
        LocalDateTime departureTime1 = LocalDateTime.now().plusDays(1);
        LocalDateTime arrivalTime1 = departureTime1.plusHours(2);
        Flight flight1 = AirlineSystem.addFlight("New York", "London", departureTime1, arrivalTime1, aircraft1.getTailNumber());

        LocalDateTime departureTime2 = LocalDateTime.now().plusDays(3);
        LocalDateTime arrivalTime2 = departureTime2.plusHours(5);
        Flight flight2 = AirlineSystem.addFlight("Paris", "Tokyo", departureTime2, arrivalTime2, aircraft2.getTailNumber());

        // Search flights
        List<Flight> searchResults = AirlineSystem.searchFlights("New York", "London", LocalDate.now().plusDays(1));
        System.out.println("Search Results:");
        for (Flight flight : searchResults) {
            System.out.println("Flight: " + flight.getFlightNumber() + " - " + flight.getSource() + " to " + flight.getDestination());
        }

        // Book a flight
        Booking booking = AirlineSystem.bookFlight(flight1.getFlightNumber(), passenger1.getId(), new Seat("25A", SeatType.ECONOMY), 100);
        if (booking != null) {
            System.out.println("Booking successful. Booking ID: " + booking.getId());
        } else {
            System.out.println("Booking failed.");
        }

        // Cancel a booking
        AirlineSystem.cancelBooking(booking.getId());
        System.out.println("Booking cancelled.");
    }
}
