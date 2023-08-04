package com.austin.springcode.context.event;

import com.austin.springcode.context.ApplicationEvent;

import java.util.EventListener;

/**
 * @ClassName: ApplicationListener
 * @author: zqz
 * @date: 2023/8/3 18:32
 */

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);
}
