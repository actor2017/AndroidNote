package com.heima.mobilesafe_work.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.heima.mobilesafe_work.global.GlobalConstants;
import com.heima.mobilesafe_work.utils.GetSharedPreferencesUtils;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Description: 接收开机启动的广播,检查sim卡,发送报警短信
 * 1.创建一个文件夹receiver,写一个类BootReceiver继承BroadcastReceiver
 *
 * 2.添加权限
 * <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
 * <uses-permission android:name="android.permission.SEND_SMS"/>
 * 3.在清单文件中注册
 * <receiver android:name=".receiver.BootReceiver">
 * <intent-filter>
 * <action android:name="android.intent.action.BOOT_COMPLETED"/>
 * </intent-filter>
 * </receiver>

 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/1/16 on 8:28.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("开机重启啦!!");

        //判断是否开启总开关
        boolean aBoolean = GetSharedPreferencesUtils.getBoolean(context, GlobalConstants
                .STEAL_TOGGLE, false);
        if (!aBoolean) {
            return;
        }
        //获取sim卡序列号,判断是否更换,如果更换,就发短信
        String simSeq = GetSharedPreferencesUtils.getString(context, GlobalConstants.SIM_BIND,null);
        if (TextUtils.isEmpty(simSeq)) {//没绑定
            return;
        }
        //1.获取电话管理器
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        //2.获取sim卡序列号       //当前手机的sim卡, 改变sim卡, 方便测试, 测完之后一定要改回来
        // TODO: 2017/1/16  方便测试, 测完之后一定要改回来
        // FIXME: 2017/1/16 方便测试, 测完之后一定要改回来
        String simSerialNumber = tm.getSimSerialNumber();//+"1"
        //3.判断如果不相同
        if (!simSeq.equals(simSerialNumber)) {
            System.out.println("sim卡发生变化了");
            //1.获取安全号码
            String safeNum = GetSharedPreferencesUtils.getString(context, GlobalConstants
                    .SAFE_TELENUM, null);
            //2.发送短信
            if (!TextUtils.isEmpty(safeNum)) {
                SmsManager sm = SmsManager.getDefault();
                sm.sendTextMessage(safeNum, null, "sim card changed!!!!", null, null);
                System.out.println("短信已发送");
            }
        }
    }
}
