package com.austin.springcode.beans.factory.support;

import com.austin.springcode.beans.factory.config.BeanDefinition;
import com.austin.springcode.beans.factory.support.InstantiationStrategy;
import org.springframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName: SimpleInstantiationStrategy
 * @author: zqz
 * @date: 2023/7/24 00:02
 */

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        try {
            //如果该类不为空的 话就是有构造函数的实例化
            if (null != ctor) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
