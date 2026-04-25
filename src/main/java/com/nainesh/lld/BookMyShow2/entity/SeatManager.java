package com.nainesh.lld.BookMyShow2.entity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SeatManager {
    Map<String, Show> shows;
    Map<String, Seat> seats;
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public SeatManager(Map<String, Show> shows, Map<String, Seat> seats){
        this.shows = shows;
        this.seats = seats;
    }
    Map<String, Map<String, String>> lockedSeats = new ConcurrentHashMap<>();
    //showId - <seatId, userId>

    //showIds
    ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    //createTTLExpiry(seatIds, userId, showId)
    //SeatStatus - LOCKED,
    //map<seatId, userId>
    public boolean lockSeats(List<String> seatIds, String userId, String showId){
//        if (executorService.isShutdown()) {
//            System.out.println("Executor service is shut down, cannot lock seats");
//            return;
//        }
        
        ReentrantLock lock = locks.putIfAbsent(showId, new ReentrantLock());
        if (lock == null) {
            lock = locks.get(showId);
        }
        lock.lock();
        try{
            for(String seatId: seatIds){
                if(seats.get(seatId).getSeatStatus()!=SeatStatus.FREE){
                    System.out.println("Seat "+seatId+" is not available");
                    return false;
                }
            }

            for(String seatId: seatIds){
                seats.get(seatId).setSeatStatus(SeatStatus.LOCKED);
            }
            Map<String, String> map = lockedSeats.putIfAbsent(showId, new ConcurrentHashMap<>());
            if(map==null){
               map= lockedSeats.get(showId);
            }
            for(String seatId: seatIds){
                map.put(seatId, userId);
            }
            //create  a new thread to sweep the locks
            // Increased TTL timeout to 5 seconds to allow time for payment and booking confirmation
            executorService.schedule(()-> unLockSeats(seatIds, userId, showId), 5000, TimeUnit.MILLISECONDS);
            //
            System.out.println("locked seats: "+seatIds+" for user "+userId);
        }finally {
            lock.unlock();
        }
        return true;
    }


    //Get all seats for the show, and get map
    //get all seats from user and unlock states for the seats if Seat is present
    //and seat is booked bu userId
    // Only unlock if it's still locked by the same user (prevents race conditions)
    public void unLockSeats(List<String> seatIds, String userId, String showId){
        ReentrantLock lock = locks.putIfAbsent(showId, new ReentrantLock());
        if (lock == null) {
            lock = locks.get(showId);
        }
        lock.lock();
        try{
            //seatid,userid
            Map<String, String> showLocks = lockedSeats.get(showId);
            if(showLocks==null) return;

            //unlock if lock is held by same user
            for(String seatId: seatIds){
                if(showLocks.containsKey(seatId) && showLocks.get(seatId).equals(userId)){
                    showLocks.remove(seatId);
                    if(seats.get(seatId).getSeatStatus() == SeatStatus.LOCKED){
                        seats.get(seatId).setSeatStatus(SeatStatus.FREE);
                        System.out.println("unlocked seat: "+seats.get(seatId)+" due to timeout.");
                    }else{
//                        showLocks.remove(seatId);
                        System.out.println("Unlocked seat"+seats.get(seatId).getId()+" due to booking completion");
                    }
                }
            }
        }finally {
            lock.unlock();
        }
    }
    
    // Mark seats as booked to prevent timeout unlock
//    public void markSeatsBooked(List<String> seatIds, String userId, String showId) {
//        ReentrantLock lock = locks.get(showId);
//        if (lock != null) {
//            lock.lock();
//            try {
//                Map<String, String> showLocks = lockedSeats.get(showId);
//                if (showLocks != null) {
//                    // Keep the mapping but seats are now BOOKED, so unLockSeats will skip them
//                    System.out.println("Seats marked as booked for show: " + showId);
//                }
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
    
    // Shutdown the executor service
    public void shutdown() {
//        System.out.println("Initiating shutdown of SeatManager executor service...");
        
        executorService.shutdown();
        
        try {
            // Wait for existing tasks to complete
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
//                System.out.println("Forcing shutdown of remaining tasks...");
                executorService.shutdownNow();
                
                // Wait a bit more for tasks to respond to being cancelled
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
//                    System.out.println("Executor service did not terminate cleanly.");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Shutdown interrupted, forcing shutdown...");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("SeatManager executor service shut down.");
    }

}
