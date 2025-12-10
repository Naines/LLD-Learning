package com.nainesh.problems.loggingFramework.strategies.appender;

import com.nainesh.problems.loggingFramework.entities.LogMessage;
import com.nainesh.problems.loggingFramework.strategies.formatter.LogFormatter;
import com.nainesh.problems.loggingFramework.strategies.formatter.SimpleTextFormatter;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements LogAppender{

    private LogFormatter formatter;
    private FileWriter writer;

    public FileAppender(String filePath) {
        this.formatter = new SimpleTextFormatter();
        try{
            this.writer = new FileWriter(filePath, true);
        }catch(Exception e){
            System.out.println("Failed to create filewriter "+e.getMessage());
        }
    }

    @Override
    public void append(LogMessage logMessage) {
        try{
            writer.write(formatter.format(logMessage)+" \n");
            writer.flush();
        } catch(IOException e){
            System.out.println("failed to close log file: "+e.getMessage());
        }
    }

    @Override
    public void close() {
        try{
            writer.close();
        }catch (IOException e){
            System.out.println("failed to close logs file, exception: "+e.getMessage());
        }
    }

    @Override
    public LogFormatter getFormatter() {
        return formatter;
    }

    @Override
    public void setFormatter(LogFormatter logFormatter) {
        this.formatter = logFormatter;
    }
}
