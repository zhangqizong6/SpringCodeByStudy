package com.austin.springcode.beans.factory.support;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.ConfigurableListableBeanFactory;
import com.austin.springcode.beans.factory.config.BeanDefinition;
import com.austin.springcode.beans.factory.support.AbstractAutowireCapableBeanFactory;
import com.austin.springcode.beans.factory.support.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DefaultListableBeanFactory
 * @author: zqz
 * @date: 2023/7/21 00:38
 */

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("");
        }
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }
}
