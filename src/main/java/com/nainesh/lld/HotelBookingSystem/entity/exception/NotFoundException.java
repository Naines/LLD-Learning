package com.nainesh.lld.HotelBookingSystem.entity.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String msg){
        super(msg);
    }
}