package com.austin.springcode.context.event;

import com.austin.springcode.context.ApplicationEvent;

/**
 * @ClassName: ApplicationEventMulticaster
 * @author: zqz
 * @date: 2023/8/3 18:30
 */

public interface ApplicationEventMulticaster {
    /**
     * Add a listener to be notified of all events.
     * @param listener the listener to add
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener the listener to remove
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the given application event to appropriate listeners.
     * @param event the event to multicast
     */
    void multicastEvent(ApplicationEvent event);

}
