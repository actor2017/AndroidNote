package com.shijing.dagger2sample;

/**
 * Description: 1.先写一个javaBean(本例),是一个要注入的javaBean
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/3 on 21:02.
 */

public class User {
    String name;
    int age;

    //Generate...constructor    生成有参构造
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
