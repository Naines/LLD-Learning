package com.nainesh.problems.loggingFramework.strategies.formatter;

import com.nainesh.problems.loggingFramework.entities.LogMessage;

public interface LogFormatter {
    String format(LogMessage logMessage);
}
