package com.heima.mobilesafe_work.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.heima.mobilesafe_work.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * 获取正在运行的进程数,正在运行的进程的集合,所有应用进程个数总和,杀死所有可杀死的进程
 * 正在运行的进程的集合信息:包名,应用名,应用图标,是否是用户进程,所占内存
 * <p>
 * 杀死所有进程<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
 * <p>
 * 获取可用内存,总内存(自己除外)
 * Created by Kevin.
 */
public class ProcessInfoProvider {

    /**
     * 获取正在运行的进程数
     */
    public static int getRunningProcessNum(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
        return am.getRunningAppProcesses().size();
    }

    /**
     * 获取正在运行的进程集合
     */
    public static ArrayList<ProcessInfo> getRunningProcess(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am
                .getRunningAppProcesses();

        PackageManager pm = ctx.getPackageManager();

        ArrayList<ProcessInfo> list = new ArrayList<>();

        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            ProcessInfo info = new ProcessInfo();

            //包名
            String packageName = runningAppProcess.processName;
            info.packageName = packageName;

            //获取当前app运行时占用的内存
            //pid: 进程id
            Debug.MemoryInfo[] processMemoryInfo = am.getProcessMemoryInfo(new
                    int[]{runningAppProcess.pid});
            int memory = processMemoryInfo[0].getTotalPrivateDirty() * 1024;//获取内存大小,
            // 单位KB
            info.memory = memory;

            try {
                //根据包名获取当前应用的信息
                ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
                String name = applicationInfo.loadLabel(pm).toString();//名称
                Drawable icon = applicationInfo.loadIcon(pm);//图标

                info.name = name;
                info.icon = icon;

                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    //系统进程
                    info.isUserProcess = false;
                } else {
                    //用户进程
                    info.isUserProcess = true;
                }

            } catch (PackageManager.NameNotFoundException e) {
                //某些系统级别的进程,没有图标和名称
                e.printStackTrace();

                info.name = info.packageName;//以包名作为默认名称
                info.icon = ctx.getResources().getDrawable(R.mipmap.ic_launcher);
                info.isUserProcess = false;
            }
            list.add(info);
        }
        return list;
    }

    /**
     * 当所有已安装app的进程都运行起来之后, 一共有多少个进程
     */
    public static int getTotalProcessNum(Context ctx) {
        //进程(工厂)&线程(车间)
        //Android中, 每个app至少独占一个进程
        //可以给四大组件配置process属性, 让其单独运行在某个进程
//        <service
//        android:process="cn.itcast.mobilesafe.address"
//       >

        //1. 获取所有已安装app, 同时还要获取每个app四大组件的信息
        //2. 获取四大组件的进程信息

        PackageManager pm = ctx.getPackageManager();
        //还要获取每个app四大组件的信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager
                .GET_ACTIVITIES | PackageManager.GET_SERVICES | PackageManager.GET_PROVIDERS |
                PackageManager.GET_RECEIVERS);

        int totalCount = 0;//总进程数
        for (PackageInfo info : installedPackages) {

            //不能有重复元素
            HashSet<String> list = new HashSet<>();

            //默认进程名称为应用程序的包名
            //1. 每个应用程序本身独占一个进程
            list.add(info.applicationInfo.processName);

            //2. 四大组件额外占用的进程

            //当前app的所有activity数组
            ActivityInfo[] activities = info.activities;
            if (activities != null) {
                for (ActivityInfo activity : activities) {
                    list.add(activity.processName);
                }
            }

            ServiceInfo[] services = info.services;
            if (services != null) {
                for (ServiceInfo service : services) {
                    list.add(service.processName);
                }
            }

            ProviderInfo[] providers = info.providers;
            if (providers != null) {
                for (ProviderInfo provider : providers) {
                    list.add(provider.processName);
                }
            }

            ActivityInfo[] receivers = info.receivers;
            if (receivers != null) {
                for (ActivityInfo receiver : receivers) {
                    list.add(receiver.processName);
                }
            }
            //更新总进程数
            totalCount += list.size();
        }
        return totalCount;
    }

    /**
     * 获取可用内存
     */
    public static long getAvailMemory(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);//获取当前内存信息, 此方法会给info的字段赋值

        return info.availMem;
    }

    /**
     * 获取总内存
     */
    public static long getTotalMemory(Context ctx) {
        //       ActivityManager am = (ActivityManager) ctx.getSystemService(Context
        // .ACTIVITY_SERVICE);

//        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
//        am.getMemoryInfo(info);//获取当前内存信息, 此方法会给info的字段赋值

        //return info.totalMem;//totalMem在api16+才可以使用

        //可以读取proc/meminfo文件, 第一行就是总内存信息
        //MemTotal:         510704 kB
        try {
            BufferedReader reader = new BufferedReader(new FileReader("proc/meminfo"));
            String line = reader.readLine();//读取第一行
            //MemTotal:         510704 kB

            StringBuffer sb = new StringBuffer();

            char[] chars = line.toCharArray();
            for (char c : chars) {
                //判断当前字符是否是数字
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
            }
            String s = sb.toString();
            return Long.parseLong(s) * 1024;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 杀死所有进程
     * 需要权限:一键清理<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
     */
    public static void killAllProccess(Context ctx) {
        //获取正在运行的进程集合
        ArrayList<ProcessInfo> runningProcess = getRunningProcess(ctx);
        //获取正在活动的服务
        ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
        //遍历集合
        for (ProcessInfo info : runningProcess) {
            //如果是自己,跳过
            if (info.packageName.equals(ctx.getPackageName())) {
                continue;
            }
            //杀死所有后台进程
            am.killBackgroundProcesses(info.packageName);
        }
    }

    /**
     * 杀死部分进程:
     * in:传进来一个需要杀死的进程的集合
     * out:返回节省的内存大小
     */
    public static long killPartProccess(Context ctx, ArrayList<ProcessInfo> killList) {
        //清理需权限 <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>

        //获取系统正在运行的服务
        ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);

        long savedMemory = 0;//总共节省的内存
        for (ProcessInfo processInfo : killList) {
            //如果是自己,跳过
            if (processInfo.packageName.equals(ctx.getPackageName())) {
                continue;
            }
            savedMemory += processInfo.memory;
            am.killBackgroundProcesses(processInfo.packageName);
        }
        //返回洁身的内存大小
        return savedMemory;
    }


    public static class ProcessInfo {
        public String packageName;     //包名
        public long memory;             //某个应用所占内存
        public String name;            //应用名
        public Drawable icon;          //应用图标
        public boolean isUserProcess;  //是否是用户进程
        public boolean isChecked;      //标记用户是否选泽了条目
    }
}
