package com.austin.springcode.core.io;

/**
 * @ClassName: ResourceLoader
 * @author: zqz
 * @date: 2023/7/28 15:56
 */

public interface ResourceLoader {
    //固定资源前缀
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);

}
