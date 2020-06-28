package com.shijing.huanxin.global;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;

import static android.content.ContentValues.TAG;

/**
 * Created by zhengping on 2017/3/18,9:08.
 * <p>
 * Application的特点：
 * 1、生命周期长,(内存泄露)
 * 2、单实例（一个进程就只有一个Application的实例对象）
 * 3、onCreate的方法，可以认为一个应用程序的入口。做一些初始化的事情
 * 4、不能自己new出Application的实例对象
 * <p>
 * 注意：记得在清单文件中注册
 */

public class HuanXinApplication extends Application {

    public static HuanXinApplication instance;//注意这种写法

//    public Context globalContext;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;        //注意这种写法

        //另外一种写法
//        globalContext = getApplicationContext();

        initHuanXin();
        //第一：默认初始化Bmob
        Bmob.initialize(this, "6d29e6ca3e4d1cd96a6fa301e2b08481");
    }

    //另外一种写法
//    public Context getApplicationContext(){
//        return globalContext;
//    }

    private void initHuanXin() {		//onCreate中调用这个方法

        //appContext = this;        //去掉
        //获取当前应用的进程id
        int pid = android.os.Process.myPid();
        //获取当前应用的进程名/包名
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(getApplicationContext()
                .getPackageName())) {//appContext改成getApplicationContext()
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，false:改成需要验证
        options.setAcceptInvitationAlways(false);
        //是否自动登录
        options.setAutoLogin(true);
        //初始化
        EMClient.getInstance().init(getApplicationContext(), options);
        //applicationContext改成getApplicationContext()
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    //注：如果你的 APP 中有第三方的服务启动，请在初始化 SDK（EMClient.getInstance().init(applicationContext, options)）
    // 方法的前面添加以下相关代码（相应代码也可参考 Demo 的application），使用 EaseUI 库的就不用理会这个。
    //如何获取processAppName请参考以下方法
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)
                    (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
