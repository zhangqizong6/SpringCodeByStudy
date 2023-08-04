package com.austin.springcode.beans.factory;

/**
 * @ClassName: FactoryBean
 * @author: zqz
 * @date: 2023/8/3 17:09
 */

public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
