package com.nainesh.lld.LuckyDipLottery.entity;


import com.nainesh.lld.LuckyDipLottery.enums.TicketStatus;

/**
 * @author Nainesh
 */
public class Ticket {
    public final int seatNo;
    public volatile User holder;
    public volatile TicketStatus status = TicketStatus.AVAILABLE;
    public volatile long deadlineMs;
    public Ticket(int seatNo) {
        this.seatNo = seatNo;
    }
}