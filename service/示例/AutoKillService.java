package com.heima.mobilesafe_work.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.heima.mobilesafe_work.engine.ProcessInfoProvider;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 锁屏清理进程(可定时清理)
 *
 * 如果复制本文件,可能在清单文件中没有自动注册,以下内容,就收不到锁屏广播
 *
 * <!--锁屏自动杀进程服务-->
 * <service
 *      android:name=".service.AutoKillService"
 *      android:enabled="true"
 *      android:exported="true">
 * </service>
 */
public class AutoKillService extends Service {

    private AutoKillReceiver mReceiver;
    private Timer mTimer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建广播
        mReceiver = new AutoKillReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);//屏幕关闭
        //注册广播接收者
        registerReceiver(mReceiver, filter);

        //定时清理
        //定时器Timer
        mTimer = new Timer();

        //按照固定频率执行某个任务; 参1:任务对象; 参2:第一次执行任务是的延时; 参3:任务执行间隔时间
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时清理任务啦!!!");
            }
        }, 0, 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mReceiver = null;

        //停止定时器
        mTimer.cancel();
    }

    //广播接收者,锁屏后,自动杀死所有进程
    class AutoKillReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("锁屏自动杀死所有进程,屏幕关闭了!");
            ProcessInfoProvider.killAllProccess(context);
        }
    }
}
