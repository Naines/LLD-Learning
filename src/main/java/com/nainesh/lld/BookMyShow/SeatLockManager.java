package com.nainesh.lld.BookMyShow;

import com.nainesh.lld.BookMyShow.entities.Seat;
import com.nainesh.lld.BookMyShow.entities.Show;
import com.nainesh.lld.BookMyShow.enums.SeatStatus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SeatLockManager {
    Map<Show, Map<Seat, String>> lockedSeats = new ConcurrentHashMap<>();
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    long LOCKOUT_TIME_MS = 500;

    void lockSeats(Show show, List<Seat> seats, String userId){
        synchronized (show){
            // Synchronize on the show to ensure atomicity for that specific show
            // Check if any of the requested seats are already locked or booked
            for(Seat seat: seats){
                if(seat.getStatus()!= SeatStatus.AVAILABLE){
                    System.out.println("Seat " + seat.getId() + " is not available.");
                    return;
                }
            }

            for (Seat seat : seats) {
                seat.setStatus(SeatStatus.LOCKED);
            }

            lockedSeats.computeIfAbsent(show, k -> new ConcurrentHashMap<>());
            for (Seat seat : seats) {
                lockedSeats.get(show).put(seat, userId);
            }
            // Schedule a task to unlock the seats after a timeout
            executorService.schedule(()-> unlockSeats(show, seats, userId), LOCKOUT_TIME_MS, TimeUnit.MILLISECONDS);
            System.out.println("Locked seats: " + seats.stream().map(Seat::getId).toList() + " for user " + userId);
        }
    }

    //Get all seats for the show, and get map
    //get all seats from user and unlock states for the seats if Seat is present
    //and seat is booked bu userId
    void unlockSeats(Show show, List<Seat> seats, String userId){
        synchronized (show){
            Map<Seat, String> showLocks = lockedSeats.get(show);
            if(showLocks!=null){
                for(Seat seat: seats){
                    // Only unlock if it's still locked by the same user (prevents race conditions)
                    if(showLocks.containsKey(seat) && showLocks.get(seat).equals(userId)){
                        showLocks.remove(seat);
                        if(seat.getStatus() == SeatStatus.LOCKED) {
                            seat.setStatus(SeatStatus.AVAILABLE);
                            System.out.println("Unlocked seat: " + seat.getId() + " due to timeout.");
                        } else {
                            showLocks.remove(seat);
                            System.out.println("Unlocked seat: " + seat.getId() + " due to booking completion.");
                        }
                    }
                }
            }
        }
    }

    public void shutdown() {
        System.out.println("Shutting down SeatLockProvider scheduler.");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
