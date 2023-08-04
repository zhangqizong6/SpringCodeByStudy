package com.austin.springcode.beans.factory.support;

/**
 * @ClassName: AbstractBeanDefinitionReader
 * @author: zqz
 * @date: 2023/7/30 14:32
 */

import com.austin.springcode.core.io.DefaultResourceLoader;
import com.austin.springcode.core.io.ResourceLoader;

/**
 * getRegistry()、getResourceLoader()，
 * 都是用于提供给后面三个方法的工具，加载和注册，
 * 这两个方法的实现会包装到抽象类中，以免污染具体的接口实现方法。
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;


    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
