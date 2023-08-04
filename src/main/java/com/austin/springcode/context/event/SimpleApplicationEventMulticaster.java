package com.austin.springcode.context.event;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.BeanFactory;
import com.austin.springcode.context.ApplicationEvent;

/**
 * @ClassName: SimpleApplicationEventMulticaster
 * @author: zqz
 * @date: 2023/8/4 00:07
 */

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{


    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

}
