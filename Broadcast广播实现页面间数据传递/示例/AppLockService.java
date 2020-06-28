package com.heima.mobilesafe_work.service;


import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;

import com.heima.mobilesafe_work.activity.EnterPwdActivity;
import com.heima.mobilesafe_work.db.AppLockDao;
import com.heima.mobilesafe_work.global.GlobalConstants;


/**
 * Created by Kevin.
 * 1. 继承辅助功能AccessibilityService
 * 2. 在清单文件中配置service, 参考官方文档
 * 3. 在res/xml中创建配置文件, 参考官方文档
 * 4. 运行app, 在设置->辅助功能中可以找到开启服务的入口
 * <p>
 * 辅助功能服务的开启和关闭,都必须由用户在设置->辅助功能中进行
 * <p>
 * 程序锁原理:
 * 1. 监听系统页面变化
 * 2. 判断当前页面是否是已加锁的页面
 * 3. 如果已加锁, 就跳到输入密码页面进行验证
 */

public class AppLockService extends AccessibilityService {

    private AppLockDao mDao;
    private AppLockReceiver mReceiver;
    private String mSkipPackage;//要跳过验证的包名

    @Override
    public void onCreate() {
        super.onCreate();

        mDao = AppLockDao.getInstance(this);

        //注册广播, 监听是否需要跳过某个app的验证
        IntentFilter filter = new IntentFilter(GlobalConstants.ACTION_APPLOCK_SKIP);
        mReceiver = new AppLockReceiver();
        registerReceiver(mReceiver, filter);
    }

    class AppLockReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSkipPackage = intent.getStringExtra("package");
        }
    }

    //辅助功能相关事件发生后的回调
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //android:accessibilityEventTypes="typeWindowStateChanged"
        //页面窗口状态发生变化
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = event.getPackageName().toString();//当前页面包名
            System.out.println(packageName);

            //已经加锁
            if (mDao.find(packageName)) {
                //判断是否需要跳过验证,注:这样感觉不好,因为得另外启动一个需要输入密码的应用,or
                // 完全重启手机卫士,才能再次跳到输入密码界面
                if (!packageName.equals(mSkipPackage)) {
                    //如果不跳过,跳到输入密码的界面
                    Intent intent = new Intent(this, EnterPwdActivity.class);
                    intent.putExtra("package", packageName);

                    //如果从service中启动activity, 需要加标记,FLAG_ACTIVITY_NEW_TASK, 表示新建一个任务栈
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //异常:Calling startActivity() from outside of an Activity  context requires the
                    // FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent);
                }
            }

        }

    }

    @Override
    public void onInterrupt() {

    }
}
