package com.kuchuan.wisdompolice.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());//初始化百度地图
    }
}