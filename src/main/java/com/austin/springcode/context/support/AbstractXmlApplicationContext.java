package com.austin.springcode.context.support;

import com.austin.springcode.beans.factory.support.DefaultListableBeanFactory;
import com.austin.springcode.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @ClassName: AbstractXmlApplicationContext
 * @author: zqz
 * @date: 2023/7/30 18:16
 */

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();

}
