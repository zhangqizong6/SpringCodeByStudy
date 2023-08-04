package com.austin.springcode.beans.factory;


import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.config.AutowireCapableBeanFactory;
import com.austin.springcode.beans.factory.config.ConfigurableBeanFactory;


public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    void preInstantiateSingletons() throws BeansException;

}
