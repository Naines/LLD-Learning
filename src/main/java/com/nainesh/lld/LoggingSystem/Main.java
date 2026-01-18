package com.nainesh.lld.LoggingSystem;

import com.nainesh.lld.LoggingSystem.entities.DebugLogProcessor;
import com.nainesh.lld.LoggingSystem.entities.ErrorLogProcessor;
import com.nainesh.lld.LoggingSystem.entities.InfoLogProcessor;
import com.nainesh.lld.LoggingSystem.entities.LogProcessor;

public class Main {

    public static void main(String[] args) {
        LogProcessor obj = new InfoLogProcessor(new DebugLogProcessor(new ErrorLogProcessor(null)));
        obj.log(LogProcessor.ERROR, "exception happens");
        obj.log(LogProcessor.DEBUG, "need to debug this ");
        obj.log(LogProcessor.INFO, "just for info ");
    }

}
