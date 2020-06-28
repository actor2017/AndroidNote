package com.shijing.dagger2sample;

/**
 * Description: 示例的第二个bean类,Module里面可以加入多个bean类
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/3 on 23:39.
 */

public class Person {
    String name;
    int money;

    //生成有参构造
    public Person(String name, int money) {
        this.name = name;
        this.money = money;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
