package com.nainesh.problems.loggingFramework;

import com.nainesh.problems.loggingFramework.enums.LogLevel;
import com.nainesh.problems.loggingFramework.strategies.appender.ConsoleAppender;

public class LoggingFrameworkDemo {
    public static void main(String[] args) {
        //1.init
        LogManager logManager = LogManager.getInstance();
        Logger rootLogger = logManager.getRootLogger();
        rootLogger.setLevel(LogLevel.INFO); //global minimum is INFO

        rootLogger.addAppender(new ConsoleAppender());
        System.out.println("---INITIAL LOGGING DEMO----");
        Logger mainLogger = logManager.getLogger("com.example.Main");

        rootLogger.info("TEST");
        mainLogger.info("App starting up");
        mainLogger.debug("This debug msg wont appear");//below root level
        mainLogger.warn("this is warning msg");


        //2.heirarcy and additivity
        System.out.println("\n---Logger Hierarchy Demo----");

        Logger dbLogger = logManager.getLogger("com.example.db");
        dbLogger.info("Database connection pool initializing");

        Logger serviceLogger = logManager.getLogger("com.example.service.UserService");
        serviceLogger.setLevel(LogLevel.DEBUG);//more verbose logging used
        serviceLogger.info("User service starting");
        serviceLogger.debug("This would now appear for service logger.");


        //3.dynamic config changes
        System.out.println("\n---- Dynamic Configuration demo -----");
        System.out.println("Change debugging level to DEBUG...");
        rootLogger.setLevel(LogLevel.DEBUG);
        mainLogger.debug("this debug msg would be visible from main logger now");

        try{
            Thread.sleep(500);
            logManager.shutdown();
        }catch(InterruptedException e){
            System.out.println("Caught exception");
        }

    }
}
