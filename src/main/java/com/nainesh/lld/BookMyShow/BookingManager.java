package com.nainesh.lld.BookMyShow;

import com.nainesh.lld.BookMyShow.Strategy.payment.PaymentStrategy;
import com.nainesh.lld.BookMyShow.entities.*;
import com.nainesh.lld.BookMyShow.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;

public class BookingManager {
    SeatLockManager seatLockManager;
    BookingManager(SeatLockManager seatLockManager){
        this.seatLockManager = seatLockManager;
    }

    public Optional<Booking> createBooking(User user, Show show, List<Seat> seats, PaymentStrategy paymentStrategy){
        //Lock the seats
        //Then calculate total price -> process payment -> if payment is successful, create booking
        //confirm booking(mark seats as BOOKED), clean lock map
        seatLockManager.lockSeats(show, seats, user.getId());
        double totalAmount = show.getPricingStartegy().calculatePrice(seats);
        Payment payment = paymentStrategy.pay(totalAmount);
        if(payment.getStatus() == PaymentStatus.SUCCESS){
            Booking booking = new Booking.BookingBuilder()
                    .setUser(user).setShow(show).setSeats(seats).setTotalAmount(totalAmount).setPayment(payment).build();
            booking.confirmBooking();
            seatLockManager.unlockSeats(show, seats, user.getId());

            return Optional.of(booking);
        }else{
            System.out.println("Payment failed. Please try again!!");
            return Optional.empty();
        }

    }
}
