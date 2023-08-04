package com.austin.springcode.beans.factory.support;

/**
 * @ClassName: AbstractAutowireCapableBeanFactory
 * @author: zqz
 * @date: 2023/7/20 23:55
 */

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.*;
import com.austin.springcode.beans.factory.config.AutowireCapableBeanFactory;
import com.austin.springcode.beans.factory.config.BeanDefinition;
import com.austin.springcode.beans.factory.config.BeanPostProcessor;
import com.austin.springcode.beans.factory.config.BeanReference;
import com.austin.springcode.beans.PropertyValue;
import com.austin.springcode.beans.PropertyValues;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 1.实例化bean类
 * <p>
 * <p>
 * 2.在Bean创建时完成前置和后置处理
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {


    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        return null;
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);

        } catch (Exception e) {
            throw new com.austin.springcode.beans.BeansException("Instantiation of bean failed", e);
        }
        //注册实现DisposableBean 接口的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        //将实例化的单例bean放到缓存里面
        //这里也要判断一下是否为单例的
        if (beanDefinition.isSingleton()){
            addSingleton(beanName, bean);
        }
        return bean;
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {

        //判断非singleton类型的bean不执行销毁方法
        if (!beanDefinition.isSingleton()) return;

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * bean属性注入
     *
     * @return
     */
    public void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {

        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                //属性填充
                BeanUtil.setFieldValue(bean, name, value);

            }
        } catch (Exception e) {
            throw new com.austin.springcode.beans.BeansException("Error setting property values：" + beanName);
        }


    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }


    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {

        //感知 invokeAwareMethods
        if (bean instanceof Aware){
            if (bean instanceof BeanFactoryAware){
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware){
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //invokeInitMethods(beanName, wrappedBean, beanDefinition);
        // 执行 Bean 对象的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }
        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {

        //实现接口 InitializingBean
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        //配置信息 （判断是为了避免二次执行销毁）
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method method = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == method) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            method.invoke(bean);
        }
    }

}
