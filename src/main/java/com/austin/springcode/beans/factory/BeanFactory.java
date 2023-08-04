package com.austin.springcode.beans.factory;



import com.austin.springcode.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: BeanFactory
 * @author: zqz
 * @date: 2023/7/19 23:37
 */

/**
 * bean工厂 bean的注册和定义
 */
public interface BeanFactory {

    Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    Object getBean(String name) throws Exception;

    Object getBean(String beanName,Object[] args) throws Exception;
    //获取bean
//    private Object getBean(String name) {
//        return beanDefinitionMap.get(name).getBean();
//    }


    //注册bean
    private void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }


}
