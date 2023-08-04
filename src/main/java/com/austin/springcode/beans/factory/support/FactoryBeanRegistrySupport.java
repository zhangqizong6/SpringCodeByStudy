package com.austin.springcode.beans.factory.support;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: FactoryBeanRegistrySupport
 * @author: zqz
 * @date: 2023/8/3 17:26
 */

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name --> object
     */

    protected static final Object NULL_OBJECT = new Object();

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.getCachedObjectForFactoryBean(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
