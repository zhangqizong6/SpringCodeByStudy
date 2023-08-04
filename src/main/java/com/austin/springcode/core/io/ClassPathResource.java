package com.austin.springcode.core.io;

import cn.hutool.core.lang.Assert;
import com.austin.springcode.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: ClassPathResource
 * @author: zqz
 * @date: 2023/7/28 14:47
 */

/**
 * 通过 ClassLoader 读取ClassPath 下的文件信息
 */
public class ClassPathResource implements Resource {

    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.isNull(path, "path must not be null");
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(this.path + "cannot be open because it does not exist");
        }
        return is;
    }
}
