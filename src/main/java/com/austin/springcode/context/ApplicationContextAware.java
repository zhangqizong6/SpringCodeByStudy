package com.austin.springcode.context;

import com.austin.springcode.beans.BeansException;
import com.austin.springcode.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
