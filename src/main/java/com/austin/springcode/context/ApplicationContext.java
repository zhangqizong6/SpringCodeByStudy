package com.austin.springcode.context;

/**
 * @ClassName: ApplicationContext
 * @author: zqz
 * @date: 2023/7/30 16:29
 */

import com.austin.springcode.beans.factory.HierarchicalBeanFactory;
import com.austin.springcode.beans.factory.ListableBeanFactory;
import com.austin.springcode.core.io.ResourceLoader;

/**
 * 定义上下文接口
 * ApplicationContext，继承于 ListableBeanFactory，也就继承了关于 BeanFactory 方法，比如一些 getBean 的方法。
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
