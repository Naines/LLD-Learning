package com.nainesh.lld.NotificationSystem;

import com.nainesh.lld.NotificationSystem.strategy.NotificationStrategy;

/**
 * @author Nainesh
 */
public interface Observer {
    void update(String message);
}
