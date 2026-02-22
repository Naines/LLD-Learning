package com.nainesh.lld.LoggingSystem2;

class Logger {
    private static final Logger INSTANCE = new Logger();
    Node chain;
    private Logger() {
        System.out.println("Creating logger singleton");
        chain = buildChain();
    }
    public static Logger getInstance() {
        return INSTANCE;
    }

    void log(LogLevel log, String msg){
        LogMessage message = new LogMessage(log, msg);
        chain.handle(message);
    }

    void debug(String msg){
        log(LogLevel.DEBUG, msg);
    }

    void error(String msg){
        log(LogLevel.ERROR, msg);
    }

    Node buildChain(){
        Node debugNode = new DebugNode();
        Node errorNode = new ErrorNode();

        debugNode.next = errorNode;
        return debugNode;
    }
}
enum LogLevel{
    DEBUG, INFO, WARN, ERROR;
}
class LogMessage{
    LogLevel level;
    String message;

    public LogMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }
}

abstract class Node{
    Node next;
    public void handle(LogMessage msg){
        if(canHandle(msg.level)){
            //extend to use multiple loggers like file, sysout (logappenders)
            //formatter can be used to format based on json, string
            System.out.println(msg.level+":"+msg.message);
        }else if(next!=null){
            next.handle(msg);
        }
    }
    abstract boolean canHandle(LogLevel level);
}

class DebugNode extends Node{

    @Override
    public boolean canHandle(LogLevel level) {
        return level==LogLevel.DEBUG;
    }
}
class ErrorNode extends Node{

    @Override
    public boolean canHandle(LogLevel level) {
        return level==LogLevel.ERROR;
    }
}
public class Main {


    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.debug("DEBUG MSG");
        logger.error("ERROR MSG");
    }
}
