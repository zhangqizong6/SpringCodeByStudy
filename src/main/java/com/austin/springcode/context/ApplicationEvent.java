package com.austin.springcode.context;

import java.util.EventObject;

/**
 * @ClassName: ApplicationEvent
 * @author: zqz
 * @date: 2023/8/3 18:25
 */

public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
