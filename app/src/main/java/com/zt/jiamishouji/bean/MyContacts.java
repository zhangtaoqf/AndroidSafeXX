package com.zt.jiamishouji.bean;

/**
 * Created by Administrator on 2016/2/29 0029.
 */
public class MyContacts {
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name+":"+phone;
    }
}
