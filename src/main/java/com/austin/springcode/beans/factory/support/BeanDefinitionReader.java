package com.austin.springcode.beans.factory.support;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.core.io.Resource;
import com.austin.springcode.core.io.ResourceLoader;

/**
 * @ClassName: BeanDefinitionReader
 * @author: zqz
 * @date: 2023/7/30 14:19
 */

public interface BeanDefinitionReader{

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource);

    void loadBeanDefinitions(Resource... resources);

    void loadBeanDefinitions(String location);

    void loadBeanDefinitions(String... locations) throws BeansException;


}
