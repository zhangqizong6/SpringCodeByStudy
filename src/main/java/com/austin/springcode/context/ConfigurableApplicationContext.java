package com.austin.springcode.context;

import com.austin.springcode.beans.BeansException;

/**
 * @ClassName: ConfigurableApplicationContext
 * @author: zqz
 * @date: 2023/7/30 16:32
 */

public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     */
    void refresh() throws BeansException;

    /**
     * 虚拟机关闭钩子注册调用销毁方法
     *
     * registerShutdownHook 和手动执行关闭的方法 close。
     */
    void registerShutdownHook();

    void close();

}
