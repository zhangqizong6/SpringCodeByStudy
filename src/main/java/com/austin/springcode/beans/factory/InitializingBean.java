package com.austin.springcode.beans.factory;

/**
 * @ClassName: InitializingBean
 * @author: zqz
 * @date: 2023/7/30 21:30
 */

public interface InitializingBean {
    /**
     * bean处理了属性填充后调用
     */
    void afterPropertiesSet() throws Exception;
}
