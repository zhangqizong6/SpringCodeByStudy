package com.austin.springcode.beans.factory;

/**
 * @ClassName: BeanFactoryAware
 * @author: zqz
 * @date: 2023/8/2 09:23
 */

import com.austin.springcode.beans.BeansException;

/**
 * 容器感知类
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 实现此接口，既能感知到所属的 BeanFactory
     * @param beanFactory
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
