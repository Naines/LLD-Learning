package com.nainesh.lld.HotelBookingSystem.entity;

import java.util.UUID;

public class Booking{
    String bookingId;
    String userId;
    int rooms;
    int to;
    int from;
    String hotelId;
    BookingStatus status;

    public Booking(String userId,String hotelId, int rooms, int from, int to) {
        this.bookingId = UUID.randomUUID().toString();
        this.userId = userId;
        this.rooms = rooms;
        this.to = to;
        this.from = from;
        this.status = BookingStatus.SUCCESS;
        this.hotelId = hotelId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public int getRooms() {
        return rooms;
    }

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", userId='" + userId + '\'' +
                ", rooms=" + rooms +
                ", to=" + to +
                ", from=" + from +
                ", status=" + status +
                '}';
    }
}
