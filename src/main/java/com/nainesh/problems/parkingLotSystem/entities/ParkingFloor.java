package com.nainesh.problems.parkingLotSystem.entities;

import com.nainesh.problems.parkingLotSystem.vehicle.Vehicle;
import com.nainesh.problems.parkingLotSystem.vehicle.VehicleSize;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ParkingFloor {

    private final int floorNumber;
    private final Map<String, ParkingSpot> spots;

    public ParkingFloor(int floorNumber) {
        //create new floor
        this.floorNumber = floorNumber;
        this.spots = new ConcurrentHashMap<>();
    }

    //findavailableSpots
    //addSpot
    //displayAvailableSpots
    public void addSpot(ParkingSpot parkingSpot){
        spots.put(parkingSpot.getSpotId(), parkingSpot);
    }

    public synchronized Optional<ParkingSpot> findAvailableSpot(Vehicle vehicle){
//        List<ParkingSpot> toSort = new ArrayList<>();
//        for (ParkingSpot spot : spots.values()) {
//            if (!spot.isOccupied() && spot.canFitVehicle(vehicle)) {
//                toSort.add(spot);
//            }
//        }
//        toSort.sort(Comparator.comparing(ParkingSpot::getSpotId));
//        for (ParkingSpot spot : toSort) {
//            return Optional.of(spot);
//        }
//        return Optional.empty();

        return spots.values()
                .stream()
                .filter(spot -> !spot.isOccupied() && spot.canFitVehicle(vehicle))
                .sorted(Comparator.comparing(ParkingSpot::getSpotId))
                .findFirst();
    }

    public void displayAvailability(){
        System.out.printf("----Floor %d Availability----\n" , floorNumber);
//        Map<VehicleSize, Long> availableCounts = new HashMap<>();
//        for (ParkingSpot spot : spots.values()) {
//            if (!spot.isOccupied()) {
//                availableCounts.merge(spot.getSize(), 1L, Long::sum);
//            }
//        }
        Map<VehicleSize, Long> availableCounts = spots.values()
                .stream().filter(spot -> !spot.isOccupied())
                .collect(Collectors.groupingBy(ParkingSpot:: getSize, Collectors.counting()));
        for (VehicleSize size : VehicleSize.values()) {
            System.out.printf("  %s spots: %d\n", size, availableCounts.getOrDefault(size, 0L));
        }
    }
}
