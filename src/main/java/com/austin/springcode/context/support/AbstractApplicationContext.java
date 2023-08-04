package com.austin.springcode.context.support;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.ConfigurableListableBeanFactory;
import com.austin.springcode.beans.factory.config.BeanFactoryPostProcessor;
import com.austin.springcode.beans.factory.config.BeanPostProcessor;
import com.austin.springcode.beans.factory.config.ConfigurableBeanFactory;
import com.austin.springcode.context.ConfigurableApplicationContext;
import com.austin.springcode.context.event.*;
import com.austin.springcode.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: AbstractApplicationContext
 * @author: zqz
 * 在抽象应用上下文 AbstractApplicationContext#refresh 中，主要新增了 初始化事件发布者、注册事件监听器、发布容器刷新完成事件，三个方法用于处理事件操作。
 * 初始化事件发布者(initApplicationEventMulticaster)，主要用于实例化一个 SimpleApplicationEventMulticaster，这是一个事件广播器。
 * 注册事件监听器(registerListeners)，通过 getBeansOfType 方法获取到所有从 spring.xml 中加载到的事件配置 Bean 对象。
 * 发布容器刷新完成事件(finishRefresh)，发布了第一个服务器启动完成后的事件，这个事件通过 publishEvent 发布出去，其实也就是调用了 applicationEventMulticaster.multicastEvent(event); 方法。
 * 最后是一个 close 方法中，新增加了发布一个容器关闭事件。publishEvent(new ContextClosedEvent(this));
在· * @date: 2023/7/30 16:34
 */

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        //1.创建BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();

        //2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3.添加ApplicationContextAwareProcessor,让继承ApplicationContextAware的bean对象都能感知到ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //4.在Bean实例化之前，执行BeanFactoryPostProcessor(Invoke factory processors registered as beans in the context.)
        invokeBeanPostProcessors(beanFactory);

        //5.BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        //6. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 9. 发布容器刷新完成事件
        finishRefresh();

    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }


    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void invokeBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }

    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory() throws BeansException;

    @Override
    public Object getBean(String name) throws Exception {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String beanName, Object[] args) throws Exception {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }

}
