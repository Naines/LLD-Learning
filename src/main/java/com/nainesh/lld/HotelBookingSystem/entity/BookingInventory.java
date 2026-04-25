package com.nainesh.lld.HotelBookingSystem.entity;

public class BookingInventory{
    int total, available;
    String hotelId;
    int date;

    public BookingInventory(int total, int available, int date) {
        this.total = total;
        this.available = available;
        this.date = date;
    }

    @Override
    public String toString() {
        return "BookingInventory{" +
                "total=" + total +
                ", available=" + available +
                ", hotelId='" + hotelId + '\'' +
                ", date=" + date +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}