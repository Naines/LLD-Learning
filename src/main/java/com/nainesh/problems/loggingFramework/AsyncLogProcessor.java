package com.nainesh.problems.loggingFramework;

import com.nainesh.problems.loggingFramework.entities.LogMessage;
import com.nainesh.problems.loggingFramework.strategies.appender.LogAppender;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncLogProcessor {
    private final ExecutorService executor;

    public AsyncLogProcessor(){
        executor = Executors.newSingleThreadExecutor( runnable -> {
            Thread thread = new Thread(runnable, "AsyncLogProcessor");
            thread.setDaemon(true); //doesn't prevent JVM exit
            return thread;
        });
    }

    public void process(LogMessage message, List<LogAppender> appenders){
        if(executor.isShutdown()){
            System.err.println("Logger is shutdown. Message not processed");
            return;
        }

        executor.submit(()->{
           for(LogAppender appender: appenders){
               appender.append(message);
           }
        });
    }

    public void shutdown(){
        //disable new tasks from being submitted
        executor.shutdown();
        try{
            if(!executor.awaitTermination(2, TimeUnit.SECONDS)){
                System.err.println("Logger executor didn't terminate in 2 s alloted to it.");
                executor.shutdownNow();
            }
        }catch(InterruptedException e){
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
