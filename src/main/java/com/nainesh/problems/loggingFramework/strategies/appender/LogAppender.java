package com.nainesh.problems.loggingFramework.strategies.appender;

import com.nainesh.problems.loggingFramework.entities.LogMessage;
import com.nainesh.problems.loggingFramework.strategies.formatter.LogFormatter;

public interface LogAppender {
    void append(LogMessage logMessage);
    void close();
    LogFormatter getFormatter();
    void setFormatter(LogFormatter logFormatter);
}
