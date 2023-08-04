package com.austin.springcode.context.event;

import com.austin.springcode.context.ApplicationContext;
import com.austin.springcode.context.ApplicationEvent;

/**
 * @ClassName: ApplicationContextEvent
 * @author: zqz
 * @date: 2023/8/3 18:27
 */

public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * Get the <code>ApplicationContext</code> that the event was raised for.
     */
    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }

}
