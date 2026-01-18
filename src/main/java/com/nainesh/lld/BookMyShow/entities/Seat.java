package com.nainesh.lld.BookMyShow.entities;

import com.nainesh.lld.BookMyShow.enums.SeatStatus;
import com.nainesh.lld.BookMyShow.enums.SeatType;

public class Seat {
    String id;
    int row, col;
    SeatType type;
    SeatStatus status;

    public Seat(String id, int row, int col, SeatType type) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.type = type;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public SeatType getType() {
        return type;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
