package com.nainesh.lld.BookMyShow2;

// BookMyShow-like Ticket Booking
// Assumptions:
// 1. Shows have fixed seats.
// 2. All seats equal.
//
// APIs:
// 1. String bookTickets(String userId, String showId, List<String> seatIds)
// 2. boolean cancelBooking(String bookingId)
//
// Focus Areas:
// 1. Validate seat availability.
// 2. No partial bookings.
// 3. Prevent double-booking.


import com.nainesh.lld.BookMyShow2.entity.Booking;
import com.nainesh.lld.BookMyShow2.entity.Seat;
import com.nainesh.lld.BookMyShow2.entity.SeatManager;
import com.nainesh.lld.BookMyShow2.entity.SeatStatus;
import com.nainesh.lld.BookMyShow2.entity.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ENTITIES: Booking, Show, Seat, User
 *
 *
 */

class SeatNotReservedException extends RuntimeException{
    SeatNotReservedException() {
        super("Seat Not reserved");
    }
    SeatNotReservedException(String msg) {
        super(msg);
    }
}

class PaymentFailedException extends RuntimeException {
    PaymentFailedException(){
        super("Payment failed");
    }
}

public class Main {

    static Map<String, Seat> seats=new ConcurrentHashMap<>();
    static Map<String, Show> shows=new ConcurrentHashMap<>();
    static Map<String, Booking> bookings = new ConcurrentHashMap<>();
    static ConcurrentLinkedQueue<String> bookingIds = new ConcurrentLinkedQueue<>();

    SeatManager seatManager;
    
    public Main() {
        seatManager = new SeatManager(shows, seats);
    }

    boolean createTTLExpiry(List<String> seatIds, String userId, String showId){
        return seatManager.lockSeats(seatIds, userId, showId);
//        return true;
    }

    String bookTicket(List<String> seatIds, String userId, String showId){
        //create a lockObject for userID on the List of seats
        boolean reserved = createTTLExpiry(seatIds, userId, showId);
        if(!reserved) throw new SeatNotReservedException();
        double amt=0.0;
        for(String seatId: seatIds){
            amt+=seats.get(seatId).getCost();
        }
        //assuming payment done
        boolean paymentDone = confirmPayment(amt);
        if(!paymentDone) throw new PaymentFailedException();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        boolean confirmSeatBooking = confirmSeat(seatIds, userId, showId, 1);
        if(!confirmSeatBooking) throw new SeatNotReservedException("Failure after payment");
        String bookingId = bookingIds.peek();
        return bookingId != null ? bookingId : "BOOKING_FAILED";
    }

    boolean confirmPayment(double amt){
        System.out.println("Payment of "+amt+" done.");
        return true;
    }

    boolean confirmSeat(List<String> seatIds, String userId, String showId, int date) {
        try {
            // Validate that all seats are locked by the same userId
            for (String seatId : seatIds) {
                Seat seat = seats.get(seatId);
                if (seat == null) {
                    System.out.println("Seat not found: " + seatId);
                    return false;
                }
                
                // Check if seat is locked
                if (seat.getSeatStatus() != SeatStatus.LOCKED) {
                    System.out.println("Seat " + seatId + " is not locked. Status: " + seat.getSeatStatus());
                    return false;
                }
            }
            
            // All validations passed, create the booking
            String bookingId = UUID.randomUUID().toString();
            Booking booking = new Booking(userId, seatIds, date, showId, "H1");
            
            // Update seat status to BOOKED
            for (String seatId : seatIds) {
                Seat seat = seats.get(seatId);
                if (seat != null && seat.getSeatStatus() == SeatStatus.LOCKED) {
                    seat.setSeatStatus(SeatStatus.BOOKED);
                }
            }
            
            bookings.put(bookingId, booking);
            bookingIds.add(bookingId);
            System.out.println("Booking confirmed: " + bookingId + " for user " + userId);
            
            // Notify seat manager that booking is complete (to prevent unlock on timeout)
//            seatManager.markSeatsBooked(seatIds, userId, showId);
            
            return true;
        } catch (Exception e) {
            System.out.println("Error confirming seat: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Cancel booking
    boolean cancelBooking(String bookingId) {
        try {
            Booking booking = bookings.get(bookingId);
            if (booking == null) {
                System.out.println("Booking not found: " + bookingId);
                return false;
            }
            
            // Release the seats
            for (String seatId : booking.getSeatIds()) {
                Seat seat = seats.get(seatId);
                if (seat != null && seat.getSeatStatus() == SeatStatus.BOOKED) {
                    seat.setSeatStatus(SeatStatus.FREE);
                    System.out.println("Released seat: " + seatId);
                }
            }
            
            bookings.remove(bookingId);
            System.out.println("Booking cancelled: " + bookingId);
            return true;
        } catch (Exception e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        String showId ="S123";


        // Create seats for the show
        System.out.println("=== Creating Seats ===");
        for (int i = 1; i <= 12; i++) {
            String seatId = "SEAT_" + i;
            seats.put(seatId, new Seat(seatId, 100.0));
            System.out.println("Created " + seatId);
        }

        // Create a show
        Show show = new Show();
        shows.put(showId, show);
        System.out.println("Created show: " + showId);

        // Multithreaded booking - create multiple threads to book seats
        System.out.println("\n=== Multithreaded Booking ===");
        List<Thread> bookingThreads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int threadNum = i;
            bookingThreads.add(new Thread(() -> {
                try {
                    String userId = "USER_" + threadNum;
                    List<String> requestedSeats = new ArrayList<>();

                    // Each thread tries to book 2 seats
                    requestedSeats.add("SEAT_" + (threadNum * 2 + 1));
                    requestedSeats.add("SEAT_" + (threadNum * 2 + 2));

                    System.out.println("[" + userId + "] Attempting to book seats: " + requestedSeats);
                    String bookingId = main.bookTicket(requestedSeats, userId, showId);
                    System.out.println("[" + userId + "] Successfully booked with ID: " + bookingId);
                } catch (Exception e) {
                    System.out.println("[BOOKING_THREAD_" + threadNum + "] Error: " + e.getMessage());
                }
            }));
        }

        // Start all booking threads
        for (Thread t : bookingThreads) {
            t.start();
        }

        // Wait for all threads to complete
        for (Thread t : bookingThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nTotal bookings created: " + bookingIds.size());

        // Multithreaded cancellation
        System.out.println("\n=== Multithreaded Cancellation ===");
        List<Thread> cancelThreads = new ArrayList<>();
        for (int i = 0; i < Math.min(3, bookingIds.size()); i++) {
            final int threadNum = i;
            cancelThreads.add(new Thread(() -> {
                String bookingId = bookingIds.poll();
                if (bookingId != null) {
                    System.out.println("[CANCEL_THREAD_" + threadNum + "] Cancelling booking: " + bookingId);
                    boolean result = main.cancelBooking(bookingId);
                    if (result) {
                        System.out.println("[CANCEL_THREAD_" + threadNum + "] Successfully cancelled");
                    } else {
                        System.out.println("[CANCEL_THREAD_" + threadNum + "] Failed to cancel");
                    }
                }
            }));
        }

        // Start all cancellation threads
        for (Thread t : cancelThreads) {
            t.start();
        }

        // Wait for all cancellation threads to complete
        for (Thread t : cancelThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nRemaining bookings: " + bookingIds.size());

        // Print final seat status
        System.out.println("\n=== Final Seat Status ===");
        for (int i = 1; i <= 10; i++) {
            String seatId = "SEAT_" + i;
            Seat seat = seats.get(seatId);
            if (seat != null) {
                System.out.println(seatId + " -> " + seat.getSeatStatus());
            }
        }

        // Additional booking attempts after main operations
        System.out.println("\n=== Additional Booking Attempts ===");
        new Thread(()->{
            try {
                List<String> seatIds = List.of("SEAT_1");
                main.bookTicket(seatIds, "U456", showId);
            } catch (Exception e) {
                System.out.println("[BOOKING_THREAD_] Error: " + e.getMessage());
            }
        }).start();

        new Thread(()->{
            try {
                List<String> seatIds = List.of("SEAT_2");
                main.bookTicket(seatIds, "U456", showId);
            } catch (Exception e) {
                System.out.println("[BOOKING_THREAD_] Error: " + e.getMessage());
            }
        }).start();

        new Thread(()->{
            try {
                List<String> seatIds = List.of("SEAT_1");
                main.bookTicket(seatIds, "U456", showId);
            } catch (Exception e) {
                System.out.println("[BOOKING_THREAD_] Error: " + e.getMessage());
            }
        }).start();

        // Wait a bit for these threads and any scheduled unlock tasks
        try {
            Thread.sleep(6000); // Wait longer to allow scheduled unlock tasks to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the executor service to prevent infinite loop
        System.out.println("\n=== Shutting down executor service ===");
        main.seatManager.shutdown();
        System.out.println("Program finished.");
    }

}

