package com.austin.springcode.utils;

/**
 * @ClassName: ClassUtils
 * @author: zqz
 * @date: 2023/7/28 15:00
 */

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader(){
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }catch (Throwable ex){
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }

        if (cl == null){
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
        }

        return cl;
    }

}
