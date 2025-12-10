package com.nainesh.problems.loggingFramework.strategies.appender;

import com.nainesh.problems.loggingFramework.entities.LogMessage;
import com.nainesh.problems.loggingFramework.strategies.formatter.LogFormatter;
import com.nainesh.problems.loggingFramework.strategies.formatter.SimpleTextFormatter;

public class ConsoleAppender implements LogAppender{

    private LogFormatter formatter;

    public ConsoleAppender(){
        this.formatter = new SimpleTextFormatter();
    }
    @Override
    public void append(LogMessage logMessage) {
        System.out.println(formatter.format(logMessage));
    }

    @Override
    public void close() {
        //not required here
    }

    @Override
    public LogFormatter getFormatter() {
        return null;
    }

    @Override
    public void setFormatter(LogFormatter logFormatter) {
        this.formatter = logFormatter;
    }
}
