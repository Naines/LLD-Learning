package com.nainesh.lld.ParkingLot.entities;

import com.nainesh.lld.ParkingLot.enums.Size;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Floor {
    private Map<String , ParkingSpot> floorMap;
    private final int floorId;

    public Floor(int floorId) {
        this.floorMap = new ConcurrentHashMap<>();
        this.floorId = floorId;
    }

    public Map<String, ParkingSpot> getFloorMap() {
        return floorMap;
    }

    public int getFloorId() {
        return floorId;
    }

    public void addSpot(ParkingSpot spot){
        this.floorMap.put(spot.getSpotId(), spot);
    }

    public void findAvailableSpots(){
        //go through spots and find all is Available
        Map<Size, Long> counts = new HashMap<>();
        for(Map.Entry<String, ParkingSpot> e: floorMap.entrySet()){
            ParkingSpot currSpot = e.getValue();
            if(currSpot.isAvailable()){
                counts.put(currSpot.getSize(), counts.getOrDefault(currSpot.getSize(),0L)+1);
                System.out.println(currSpot.getSpotId()+" is Available in floor:" +getFloorId());
            }
        }

        for (Size size : Size.values()) {
            System.out.printf("  %s spots: %d\n", size, counts.getOrDefault(size, 0L));
        }

    }

    public Optional<ParkingSpot> findAvailableSpot(Vehicle vehicle){
        return floorMap.values().stream().filter(s -> s.isAvailable() && s.getSize().equals(vehicle.getSize())).findFirst();
    }
}
