package com.austin.springcode.beans;

/**
 * @ClassName: PropertyValue
 * @author: zqz
 * @date: 2023/7/27 00:10
 */

public class PropertyValue {

    private final String name;

    private final Object value;


    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


}
