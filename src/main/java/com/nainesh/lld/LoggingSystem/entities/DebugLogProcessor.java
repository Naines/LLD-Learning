package com.nainesh.lld.LoggingSystem.entities;

public class DebugLogProcessor extends LogProcessor{

    public DebugLogProcessor(LogProcessor next) {
        super(next);
    }

    public void log(int logLevel, String message){
        if(logLevel==DEBUG){
            System.out.println("DEBUG: "+message);
        }else{
            super.log(logLevel, message);
        }
    }
}
