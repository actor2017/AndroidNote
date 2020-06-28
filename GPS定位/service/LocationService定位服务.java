package com.heima.mobilesafe_work.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.heima.mobilesafe_work.global.GlobalConstants;
import com.heima.mobilesafe_work.utils.GetSharedPreferencesUtils;

/**
 * 手机定位服务,添加权限
 * <uses-permission android:name="android.permission.INTERNET"/>    应该还要加网络权限?
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/> 粗糙定位: 网络定位
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>   //良好定位: GPS定位
 *
 * 在清单文件中添加定位服务
 * <service
 * android:name=".service.LocationService"
 * android:enabled="true"
 * android:exported="true">
 * </service>
 *
 * 手机定位流程:
 * 1. 给手机发送短信指令#*location*#
 * 2. 收到短信指令, 启动LocationService服务,开始监听位置变化
 * 3. 用模拟器模拟位置变化
 * 4. 在LocationListener中收到经纬度信息
 * 5. 给安全号码发送经纬度信息, 销毁服务
 * <p>
 * LocationManager定位: 原生api, 此方法定位不稳定, 也不精准, 耗时也比较长
 * <p>
 * 第三方地图公司, 百度, 高德, 腾讯.. 将定位及地图接口开放给开发者, 只要开发者接入地图的sdk,
 * 就可以实现定位及地图展示等功能
 * <p>
 * 定位精准, 速度快, 稳定-->GPS + 4G  + wifi
 */
public class LocationService extends Service {

    private LocationManager mLM;//位置管理器
    private MyListener mListener;//位置监听器

    //绑定服务回调
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //位置管理器
        mLM = (LocationManager) getSystemService(LOCATION_SERVICE);
        //获取所有的位置提供者: gps, network, passive(被动)
        //List<String> allProviders = mLM.getAllProviders();
        //System.out.println("allProviders:" + allProviders);

        mListener = new MyListener();//位置监听器

        /**
         * 请求位置更新, 参1: 位置提供者; 参2: 最短更新时间间隔; 参3: 最短更新距离(米);
         * 参2,3改为0, 表示只要位置变化,立即更新,没有时间和空间限制
         *
         * 这儿会报错,解决方法:alt+enter--→Disable inspection(不检查)
         */
        mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);
    }

    class MyListener implements LocationListener {

        //位置变化回调
        //只有位置发生变化, 才会回调此方法
        //模拟器: 可以模拟位置变化
        @Override
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();//经度
            double latitude = location.getLatitude();//纬度
            double altitude = location.getAltitude();//海拔
            float accuracy = location.getAccuracy();//精确度

            System.out.println("longitude经度:" + longitude);
            System.out.println("latitude纬度:" + latitude);
            System.out.println("altitude海拔:" + altitude);
            System.out.println("accuracy精确度:" + accuracy);

            /**
             * 给安全号码发送经纬度信息
             * PrefUtils    :SharedPreferences
             * SAFE_TELENUM :接收位置信息的手机号码
             */
            String number = GetSharedPreferencesUtils.getString(getApplicationContext(),
                    GlobalConstants.SAFE_TELENUM, "");
            //发送短信
            SmsManager.getDefault().sendTextMessage(number, null, "longitude经度:" +
                    longitude + ";latitude纬度:" + latitude + ";altitude海拔" + altitude
                    + ";accuracy精确度:" + accuracy, null, null);

            //销毁本服务, 避免服务长期在后台运行, 耗费电量和话费
            //第一种销毁方式(隐式意图)
            //stopService(new Intent(getApplicationContext(), LocationService.class));
            //service自我销毁的方法  finish()
            stopSelf();
        }

        //GPS状态变化, 可用->不可用->可用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        //GPS开关打开, 可用
        @Override
        public void onProviderEnabled(String provider) {

        }

        //GPS开关关闭
        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    //如果不注销,服务无法停止,底层的GPS模块仍然在运行
    @Override
    public void onDestroy() {
        super.onDestroy();
        //手动停止位置更新, 必须调用此方法, 否则即使服务销毁, 底层的GPS模块仍然在运行
        mLM.removeUpdates(mListener);
        mListener = null;
    }
}
