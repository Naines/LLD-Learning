package com.nainesh.lld.HotelBookingSystem;
import com.nainesh.lld.HotelBookingSystem.entity.Booking;
import com.nainesh.lld.HotelBookingSystem.entity.BookingInventory;
import com.nainesh.lld.HotelBookingSystem.entity.BookingStatus;
import com.nainesh.lld.HotelBookingSystem.entity.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    // Assumptions
    // 1) The constructor will take total number of rooms in this hotel as parameter
    //    100, 200, 50... etc
    // 2) All rooms are of equal size
    // 3) We are not going to worry about the cost of each room or the payment etc.

    //String reserveRoom(int noOfRoomsRequested, LocalDate from, LocalDate to);
    //boolean cancelReservation(String reservationId);

    // Focus Areas
    // 1) Keep the implementation simple and easy
    // 2) No external DB or API etc. Use java containers map, set, list etc.
    // 3) Validations
    //    a) Basic validations
    //    b) Each room requested must be available for continuous days from -> to
    //    c) No partial reservations
    // 4) Thread safety (this we will deal with later if time permits).

    static ConcurrentHashMap<String, Booking> bookings = new ConcurrentHashMap<>();
    static ConcurrentHashMap<String, List<BookingInventory>> inventory = new ConcurrentHashMap<>();
    static ConcurrentLinkedQueue<String> bookingIds = new ConcurrentLinkedQueue<>();
//    static List<String> bookingIds = new ArrayList<>();
    static ConcurrentHashMap<String, ReentrantLock> hotelLocks = new ConcurrentHashMap<>();

    static boolean getRoomsAvailable(String hotelId, int numRequired, int from, int to){

        List<BookingInventory> list = inventory.get(hotelId);
        boolean found = true;
        for(BookingInventory ob: list){
            if(ob.getDate()<from || ob.getDate()>to) continue;
            if (ob.getAvailable() < numRequired){
                found = false;
                break;
            };
        }
//        System.out.println(found+" -> rooms availability");
        return found;
    }
    // Old synchronized version
    /*
    static synchronized boolean getRoomsAvailable(String hotelId, int numRequired, int from, int to){

        //m1: transverse inventory to check if room is available on dates to to from
        List<BookingInventory> list = inventory.get(hotelId);
        boolean found = true;
        for(BookingInventory ob: list){
            if(ob.getDate()<from || ob.getDate()>to) continue;
            if (ob.getAvailable() < numRequired){
                found = false;
                break;
            };
        }
//        System.out.println(found+" -> rooms availability");
        return found;
    }
    */
    static String reserveRoom(int no, int from, int to, String userId, String hotelId){
        ReentrantLock lock = hotelLocks.putIfAbsent(hotelId, new ReentrantLock());
        if (lock == null) {
            lock = hotelLocks.get(hotelId);
        }
        lock.lock();
        try {
            boolean found = getRoomsAvailable(hotelId, no, from, to);
            if (!found) return "ROOMS NOT AVAILABLE";
            List<BookingInventory> bookingInventories = inventory.get(hotelId);
            for (BookingInventory ob : bookingInventories) {
                if (ob.getDate() < from || ob.getDate() > to) continue;
                ob.setAvailable(ob.getAvailable() - no);
            }

            Booking booking = new Booking(userId, hotelId, no, from, to);
            bookings.put(booking.getBookingId(), booking);
            return booking.getBookingId();
        } finally {
            lock.unlock();
        }
    }
    // Old synchronized version
    /*
    static synchronized String reserveRoom(int no, int from, int to, String userId, String hotelId){
        boolean found = getRoomsAvailable(hotelId,no, from , to);
        if(!found) return "ROOMS NOT AVAILABLE";
        List<BookingInventory> bookingInventories=inventory.get(hotelId);
        for(BookingInventory ob: bookingInventories){
            if(ob.getDate()<from || ob.getDate()>to) continue;
            ob.setAvailable(ob.getAvailable()-no);
        }

        Booking booking =new Booking(userId,hotelId, no, from, to);
        bookings.put(booking.getBookingId(), booking);
        return booking.getBookingId();
    }
    */

    static boolean cancelReservation(String reservationId){
        Booking booking= bookings.get(reservationId);
        if(booking==null) {
            throw new NotFoundException("Booking with the mentioned ID not found");
        }

        //lock begins
        ReentrantLock lock = hotelLocks.putIfAbsent(booking.getHotelId(), new ReentrantLock());
        if (lock == null) {
            lock = hotelLocks.get(booking.getHotelId());
        }
        lock.lock();
        try {
            booking.setStatus(BookingStatus.CANCELLED);//multithreaded -synchronized, locks
            List<BookingInventory> bookingInventories = inventory.get(booking.getHotelId());
            System.out.println(booking);
            for (BookingInventory ob : bookingInventories) {
                if (ob.getDate() < booking.getFrom() || ob.getDate() > booking.getTo()) continue;
                ob.setAvailable(ob.getAvailable() + booking.getRooms());
//            System.out.println(ob);
            }
        }finally {
            lock.unlock();
        }
        return true;
    }
    // Old synchronized version
    /*
    static synchronized boolean cancelReservation(String reservationId){
        Booking booking= bookings.get(reservationId);
        if(booking==null) {
            throw new NotFoundException("Booking with the mentioned ID not found");
        }

        //lock begins
       booking.setStatus(BookingStatus.CANCELLED);//multithreaded -synchronized, locks
        List<BookingInventory> bookingInventories=inventory.get(booking.getHotelId());
        System.out.println(booking);
        for(BookingInventory ob: bookingInventories){
            if(ob.getDate()<booking.getFrom() || ob.getDate()>booking.getTo()) continue;
            ob.setAvailable(ob.getAvailable()+booking.getRooms());
//            System.out.println(ob);
        }
        return true;
    }
    */


    public static void main(String[] args) {

        //create stock
        String hotelId = "H123";
        BookingInventory i1=new BookingInventory(10, 10, 1);
        BookingInventory i2=new BookingInventory(10, 10, 2);
        BookingInventory i3=new BookingInventory(10, 10, 3);
        BookingInventory i4=new BookingInventory(10, 10, 4);
        BookingInventory i5=new BookingInventory(10, 10, 5);

        if(!inventory.containsKey(hotelId)) inventory.put(hotelId, new ArrayList<>());
        inventory.get(hotelId).add(i1);
        inventory.get(hotelId).add(i2);
        inventory.get(hotelId).add(i3);
        inventory.get(hotelId).add(i4);
        inventory.get(hotelId).add(i5);

        // Multithreaded booking
        List<Thread> bookingThreads = new ArrayList<>();
        for(int i=0; i<10; i++){
            bookingThreads.add(new Thread(() -> {
                String bid = reserveRoom(10, 2, 2, "U"+Thread.currentThread().getId(), hotelId);
                if(!"ROOMS NOT AVAILABLE".equals(bid)){
                    bookingIds.add(bid);
                    System.out.println("Booked: " + bid);
                } else {
                    System.out.println("Failed to book");
                }
            }));
        }
        for(Thread t : bookingThreads){
            t.start();
        }
        for(Thread t : bookingThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total bookings: " + bookingIds.size());

        // Multithreaded cancellation
        List<Thread> cancelThreads = new ArrayList<>();
        for(int i=0; i<5; i++){
            cancelThreads.add(new Thread(() -> {
                String id = bookingIds.poll();
                if(id != null){
                    try {
                        cancelReservation(id);
                        System.out.println("Cancelled: " + id);
                    } catch (Exception e){
                        System.out.println("Error cancelling: " + e.getMessage());
                    }
                }
            }));
        }
        for(Thread t : cancelThreads){
            t.start();
        }
        for(Thread t : cancelThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Remaining bookings: " + bookingIds.size());

    }

}