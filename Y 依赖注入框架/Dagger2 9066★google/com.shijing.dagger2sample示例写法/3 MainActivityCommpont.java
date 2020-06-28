package com.shijing.dagger2sample;

import dagger.Component;

/**
 * Description: 3.写一个容器,是一个Interface
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/3 on 21:46.
 */
//1.必须写注解(组件)
@Component(modules = MainActivityModule.class)
public interface MainActivityCommpont {

    //MainActivity要在本容器中用User 的对象(括号里必须写:MainActivity,不能写Activity否则空指针)
    public void inject(MainActivity mainActivity);
}
