package com.shijing.tobecomegod.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

/**
 * Description: 本类用于检测某个服务是否正在运行.
 * (本例:来电显示的服务).感觉不合理,因为关机重启后,来电显示服务就被关了,而这显然不是想要的结果
 * 本例调用示例:
 * //判断服务是否开启
 * if (ServiceStatusUtils.isServiceRunning(getApplicationContext(), AddressService.class)) {
 * //关掉服务
 * stopService(new Intent(getApplicationContext(), AddressService.class));
 * //切换图片:关
 * sivAddress.setToggleOn(false);
 * } else {
 * //启动归属地服务
 * startService(new Intent(getApplicationContext(), AddressService.class));
 * //切换图片:开
 * sivAddress.setToggleOn(true);
 * }
 * <p>
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/1/24 on 14:36.
 */

public class ServiceStateUtils {

    private static AccessibilityServiceInfo accessibilityServiceInfo;

    //判断服务有没有运行     <? extends Service>:加这一句,只有服务类才能判断,否则不能判断
    public static boolean isServiceRunning(Context context, Class<? extends Service> clazz) {
        /**
         * ActivityManager:活动管理器, 和activity组件没有什么关系
         * 管理系统所有正在活动的东西
         */
        //getSystemService(@ServiceName @NonNull String name);
        ActivityManager systemService = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);

        //获取正在运行的服务,最多返回100个
        List<ActivityManager.RunningServiceInfo> runningServices = systemService
                .getRunningServices(100);

        //遍历每一个正在运行的服务对象    runningServices.for
        for (ActivityManager.RunningServiceInfo runningService : runningServices) {
            //当前正在运行的服务的类名
            String className = runningService.service.getClassName();
            //和待判断的服务类名进行比对, 如果一致, 说明该服务正在运行
            if (className.equals(clazz.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前辅助功能服务是否正在运行,这儿来自抢红包app里的判断!,不知道service怎么传进来
     * */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isAccessibilityRunning(Service service) {
        if(service == null) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) service.getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityServiceInfo = getAccessibilityServiceInfo();
        if(accessibilityServiceInfo == null) {
            return false;
        }
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
//        Iterator<AccessibilityServiceInfo> iterator = list.iterator();

        boolean isConnect = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(accessibilityServiceInfo.getId())) {
                isConnect = true;
                break;
            }
        }
//        while (iterator.hasNext()) {
//            AccessibilityServiceInfo i = iterator.next();
//            if(i.getId().equals(accessibilityServiceInfo.getId())) {
//                isConnect = true;
//                break;
//            }
//        }
        if(!isConnect) {
            return false;
        }
        return true;
    }

    private static AccessibilityServiceInfo getAccessibilityServiceInfo(){
        if (accessibilityServiceInfo == null) accessibilityServiceInfo = new AccessibilityServiceInfo();
        return accessibilityServiceInfo;
    }

    /**
     * 获取service(如果存在)
     *
     * @param mContext
     * @param clazz
     * @return 如果存在返回service否则返回null
     * <p>
     * 示例用法:
     * serviceManager = new ServiceManager(this);
     * serviceManager.setNotificationIcon(R.drawable.ic_launcher);
     * RunningServiceInfo runningServiceInfo = ServiceUtil.isServiceRunning(this,
     * NotificationService.class);
     * if(runningServiceInfo == null){
     *     serviceManager.startService();
     *     serviceManager.setAlias("xuyusong");//智慧警务中设置别名
     * }else{
     *     // 获得该Service的组件信息 可能是pkgname/servicename
     *     ComponentName serviceCMP = runningServiceInfo.service;
     *     // 设置该service的组件信息
     *     Intent intent = new Intent();
     *     intent.setComponent(serviceCMP);
     *     stopService(intent);
     *     serviceManager.startService();
     *     serviceManager.setAlias("xuyusong");
     * }
     */
    public static ActivityManager.RunningServiceInfo getServiceIfExist(Context mContext, Class<?
            extends Service> clazz) {

        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices
                (100);

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(clazz.getName())) {
                return serviceList.get(i);
            }
        }
        return null;
    }
}
