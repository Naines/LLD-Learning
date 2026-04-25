package com.nainesh.lld.BookMyShow2.entity;

import java.util.List;

public class Booking {

    String userId;
    List<String> seatIds;
    int date;
    String showId;
    String hallId;

    public Booking(String userId, List<String> seatIds, int date, String showId, String hallId) {
        this.userId = userId;
        this.seatIds = seatIds;
        this.date = date;
        this.showId = showId;
        this.hallId = hallId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "userId='" + userId + '\'' +
                ", seatIds=" + seatIds +
                ", date=" + date +
                ", showId='" + showId + '\'' +
                ", hallId='" + hallId + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }
}
