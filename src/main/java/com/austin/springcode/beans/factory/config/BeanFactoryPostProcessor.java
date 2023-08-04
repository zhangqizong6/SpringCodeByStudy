package com.austin.springcode.beans.factory.config;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.ConfigurableListableBeanFactory;


/**
 * @ClassName: BeanFactoryPostProcessor
 * @author: zqz
 * @date: 2023/7/30 16:13
 */

public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     *
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
