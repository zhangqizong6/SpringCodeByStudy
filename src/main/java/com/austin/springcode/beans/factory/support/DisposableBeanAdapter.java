package com.austin.springcode.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.DisposableBean;
import com.austin.springcode.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

/**
 * @ClassName: DisposableBeanAdapter
 * @author: zqz
 * @date: 2023/7/30 22:46
 */

/**
 * 定义销毁方法适配器
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        //实现接口 DisposableBean
        if (bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }

        //配置信息
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))){
            Method method = bean.getClass().getMethod(destroyMethodName);
            if (null == method){
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            //通过反射可以在运行状态是获得知道任意一个类中的属性和方法；对于任意一个对象都能调用它任意一个方法和属性
            method.invoke(bean);
        }
    }
}
