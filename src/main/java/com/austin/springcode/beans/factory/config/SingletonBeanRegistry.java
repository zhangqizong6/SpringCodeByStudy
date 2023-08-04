package com.austin.springcode.beans.factory.config;

/**
 * @ClassName: SingletonBeanRegistry
 * @author: zqz
 * @date: 2023/7/20 22:45
 */

/**
 * 创建一个单例模式
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);
    void registerSingleton(String beanName, Object singletonObject);

}
