package com.austin.springcode.context.support;

/**
 * @ClassName: AbstractRefreshableApplicationContext
 * @author: zqz
 * @date: 2023/7/30 16:56
 */

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.ConfigurableListableBeanFactory;
import com.austin.springcode.beans.factory.support.DefaultListableBeanFactory;

/**
 * 获取Bean工厂和加载资源
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
