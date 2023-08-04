package com.austin.springcode.beans.factory;

import com.austin.springcode.beans.BeansException;

/**
 * @ClassName: BeanClassLoaderAware
 * @author: zqz
 * @date: 2023/8/2 09:26
 */

public interface BeanClassLoaderAware extends Aware {

    /**
     * 实现此接口，既能感知到所属的 ClassLoader
     *
     * @param classLoader
     */
    void setBeanClassLoader(ClassLoader classLoader);

}
