package com.nainesh.lld.ParkingLot;

import com.nainesh.lld.ParkingLot.entities.ParkingSpot;
import com.nainesh.lld.ParkingLot.entities.Vehicle;
import com.nainesh.lld.ParkingLot.strategy.FeeStrategy;
import com.nainesh.lld.ParkingLot.entities.Floor;
import com.nainesh.lld.ParkingLot.strategy.HourlyFeeStrategy;
import com.nainesh.lld.ParkingLot.strategy.NearestFirstParkingStartegy;
import com.nainesh.lld.ParkingLot.strategy.ParkingStrategy;
import com.nainesh.lld.ParkingLot.entities.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotSystem {
    private List<Floor> floors = new ArrayList<>();
    private FeeStrategy feeStrategy;
    private ParkingStrategy parkingStrategy;
    private Map<String, Ticket> activeTickets;
    private static ParkingLotSystem ins = new ParkingLotSystem();

    private ParkingLotSystem() {
        this.feeStrategy = new HourlyFeeStrategy();
        this.parkingStrategy = new NearestFirstParkingStartegy();
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public static ParkingLotSystem getIns(){
        return ins;
    }
    public void addFloor(Floor floor){
        floors.add(floor);
    }

    public void setFeeStrategy(FeeStrategy feeStrategy){
        this.feeStrategy = feeStrategy;
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy){
        this.parkingStrategy = parkingStrategy;
    }

    public Optional<Ticket>     parkVehicle(Vehicle vehicle){
        //go through floor, and find available spot based on strategy
        //park Vehicle on spot
        //create a ticket
        Optional<ParkingSpot> availableSpot = parkingStrategy.findSpot(floors, vehicle);
        if(availableSpot.isPresent()){
            ParkingSpot spot = availableSpot.get();
            spot.parkVehicle(vehicle);
            Ticket ticket = new Ticket(spot, vehicle);
            activeTickets.put(vehicle.getLicenseNumber(), ticket);
            System.out.printf("Vehicle %s parked at %s. Ticket: is %s\n", vehicle.getLicenseNumber(), spot.getSpotId(), ticket.getTicketId());
            return Optional.of(ticket);
        }
        System.out.println("No available spot for " +vehicle.getLicenseNumber());

        return Optional.empty();
    }

    public Optional<Double> unParkVehicle(Vehicle vehicle){
        //get Ticket from activeTicket
        //unpark vehicle using the spot id from ticket
        Ticket currTicket = activeTickets.get(vehicle.getLicenseNumber());
        activeTickets.remove(vehicle.getLicenseNumber());
        if(currTicket == null){
            System.out.printf("TIcket %s not found");
            return Optional.empty();
        }
        currTicket.setExitTime();
        currTicket.getSpot().unParkVehicle();
        Double parkingFee = feeStrategy.calculateFee(currTicket);
        return Optional.of(parkingFee);
    }
}
