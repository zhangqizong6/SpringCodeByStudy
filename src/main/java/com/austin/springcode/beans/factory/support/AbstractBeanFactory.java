package com.austin.springcode.beans.factory.support;

/**
 * @ClassName: AbstractBeanFactory
 * @author: zqz
 * @date: 2023/7/20 22:53
 */

import com.austin.springcode.beans.factory.FactoryBean;
import com.austin.springcode.beans.factory.config.BeanDefinition;
import com.austin.springcode.beans.factory.BeanFactory;
import com.austin.springcode.beans.factory.config.BeanPostProcessor;
import com.austin.springcode.beans.factory.config.ConfigurableBeanFactory;
import com.austin.springcode.utils.ClassUtils;
import org.springframework.beans.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象类定义模版方法
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String beanName, Object[] args) throws Exception {
        return doGetBean(beanName, args);
    }

    protected <T> T doGetBean(final String name, final Object[] args) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(bean, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)){
            return beanInstance;
        }
        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null){
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }


    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }
}
