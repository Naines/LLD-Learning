package com.nainesh.problems.loggingFramework;

import com.nainesh.problems.loggingFramework.entities.LogMessage;
import com.nainesh.problems.loggingFramework.enums.LogLevel;
import com.nainesh.problems.loggingFramework.strategies.appender.LogAppender;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Logger {
    private final String name;
    private LogLevel level;
    private final Logger parent;
    private final List<LogAppender> appenders;
    private boolean additivity = true;

    public Logger(String name, Logger parent) {
        this.name = name;
        this.parent = parent;
        this.appenders = new CopyOnWriteArrayList<>();
    }

    public LogLevel getEffectiveLevel(){
        for(Logger logger = this; logger!=null; logger=logger.parent){
            LogLevel currLevel = logger.level;
            if(currLevel != null){
                return currLevel;
            }
        }
        return LogLevel.DEBUG; //default level
    }

    public void log(LogLevel messageLevel , String message){
        if(messageLevel.isGreaterOrEqual(getEffectiveLevel())){
            LogMessage logMessage = new LogMessage(messageLevel, this.name, message);
            callAppenders(logMessage);
        }
    }

    private void callAppenders(LogMessage logMessage){
        if(!appenders.isEmpty()){
            LogManager.getInstance().getProcessor().process(logMessage, this.appenders);
        }

        if(additivity && parent!=null){
            parent.callAppenders(logMessage);
        }
    }

    public void debug(String message){
        log(LogLevel.DEBUG, message);
    }

    public void info(String message){
        log(LogLevel.INFO, message);
    }

    public void warn(String message){
        log(LogLevel.WARN, message);
    }

    public void error(String message){
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message){
        log(LogLevel.FATAL, message);
    }





    //// GETTERS AND SETTERS /////

    public void addAppender(LogAppender appender){
        appenders.add(appender);
    }

    public List<LogAppender> getAppenders() {
        return appenders;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public void setAdditivity(boolean additivity) {
        this.additivity = additivity;
    }
    public String getName() {
        return name;
    }

    public LogLevel getLevel() {
        return level;
    }



    public Logger getParent() {
        return parent;
    }



    public boolean isAdditivity() {
        return additivity;
    }


}
