package com.austin.springcode.context.event;

/**
 * @ClassName: ContextClosedEvent
 * @author: zqz
 * @date: 2023/8/3 18:30
 */

public class ContextClosedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
