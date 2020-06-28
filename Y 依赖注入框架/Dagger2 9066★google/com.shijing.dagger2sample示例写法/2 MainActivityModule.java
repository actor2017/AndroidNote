package com.shijing.dagger2sample;

import dagger.Module;
import dagger.Provides;

/**
 *
 * 类的功能描述: 2.写一个模块(本例),MainActivity要用User
 *              也可以写多个类,返回多个类的对象
 * Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/3 on 21:12.
 */
//1,必须写一个Module的注解
@Module
public class MainActivityModule {
    //2,然后拿到User的对象,必须注解Provides,必须写在Module这个类里面
    @Provides
    User getBean(){
        return new User("张三",23);
    }

    //可以创建不同的javaBean对象,下例: [必须写一个Person bean类,否则这儿报错]
    @provides
    Person getPerson(){
        return new Person("李四",24);
    }
}
