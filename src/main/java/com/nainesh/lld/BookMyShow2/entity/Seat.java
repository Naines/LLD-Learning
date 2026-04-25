package com.nainesh.lld.BookMyShow2.entity;

public class Seat {
    String id;
    double cost;
    SeatStatus seatStatus;

    public Seat(String id, double cost) {
        this.id = id;
        this.cost = cost;
        this.seatStatus=SeatStatus.FREE;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id='" + id + '\'' +
                ", cost=" + cost +
                '}';
    }
}
