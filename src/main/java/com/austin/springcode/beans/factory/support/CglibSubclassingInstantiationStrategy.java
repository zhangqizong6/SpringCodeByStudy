package com.austin.springcode.beans.factory.support;

import com.austin.springcode.beans.factory.config.BeanDefinition;
import org.springframework.beans.BeansException;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @ClassName: CglibSubclassingInstantiationStrategy
 * @author: zqz
 * @date: 2023/7/24 00:13
 */

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == ctor) return enhancer.create();
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
