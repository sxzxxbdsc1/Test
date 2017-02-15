package com.zhy.demo.mynews.model;

/**
 * Created Time: 2017/2/15.
 * Author:  zhy
 * 功能：
 */

public class User {
    private String name;
    private String age;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public User(String name, String age, boolean flag) {
        this.name = name;
        this.age = age;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
