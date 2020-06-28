package com.kuchuanyun.cpptest;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Description:
 * IntentService的使用场景与特点:
 * IntentService是Service的子类，是一个异步的，会自动停止的服务，
 * 很好解决了传统的Service中处理完耗时操作忘记停止并销毁Service的问题.
 * 优点：
 * 1. 一方面不需要自己去new Thread  ，onHandlerIntent()方法就是在子线程运行的
 * 2.另一方面不需要考虑在什么时候关闭该Service
 * 3.可以启动IntentService多次
 *
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2017/5/17 on 14:32
 */

public class MyService extends IntentService {

    //如果不写空参构造,AndroidManifest中会报错
    public MyService() {
        super("MyService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyService(String name) {
        super(name);
    }

    //多了这一个方法,这个方法运行在子线程
    @Override
    protected void onHandleIntent(Intent intent) {//intent.putExtra("info", "good good study");
        String info = intent.getStringExtra("info");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        System.out.println("onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("onConfigurationChanged");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}
