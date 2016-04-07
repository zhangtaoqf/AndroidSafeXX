package com.zt.jiamishouji.bean;

/**
 * 传感器的实体类
 */
public class SensorEntity {
    private String name;
    private boolean isValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValue() {
        return isValue;
    }

    public void setIsValue(boolean isValue) {
        this.isValue = isValue;
    }
}
