package com.austin.springcode.context.support;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.config.BeanPostProcessor;
import com.austin.springcode.context.ApplicationContext;
import com.austin.springcode.context.ApplicationContextAware;

/**
 * @ClassName: ApplicationContextAwareProcessor
 * @author: zqz
 * @date: 2023/8/2 09:39
 */

public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
