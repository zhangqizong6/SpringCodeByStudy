package com.austin.springcode.beans.factory.support;

import com.austin.springcode.beans.factory.config.BeanDefinition;
import org.springframework.beans.BeansException;

import java.lang.reflect.Constructor;

/**
 * @ClassName: InstantiationStrategy
 * @author: zqz
 * @date: 2023/7/23 23:59
 */

public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
