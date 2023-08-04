package com.austin.springcode.beans.factory.support;

import com.austin.springcode.beans.factory.config.BeanDefinition;
import org.springframework.beans.BeansException;

/**
 * @ClassName: BeanDefinitionRegistry
 * @author: zqz
 * @date: 2023/7/21 09:15
 */

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeansException;

    BeanDefinition getBeanDefinition(String beanName) throws Exception;

    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

}
