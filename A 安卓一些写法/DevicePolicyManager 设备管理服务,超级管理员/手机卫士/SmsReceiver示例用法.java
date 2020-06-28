package com.heima.mobilesafe_work.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.heima.mobilesafe_work.R;
import com.heima.mobilesafe_work.service.LocationService;

/**
 * Created by Kevin.
 * 拦截短信的广播
 * 必须有AdminReceiver.java设备管理员组件
 * 1.添加接收短信的权限
 * <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 * 2.在清单文件中注册,并添加优先级int的最大值21亿
 * <receiver android:name=".receiver.SmsReceiver">
 * <intent-filter android:priority="2147483647">
 * <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
 * </intent-filter>
 * </receiver>
 */

public class SmsReceiver extends BroadcastReceiver {

    private DevicePolicyManager mDPM;

    private ComponentName mComponent;//组件,需要设备管理员组件AdminReceiver.java

    @Override
    public void onReceive(Context context, Intent intent) {
        //设备管理器
        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponent = new ComponentName(context, AdminReceiver.class);//初始化组件

        Object[] objs = (Object[]) intent.getExtras().get("pdus");

        for (Object obj : objs) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) obj);
            String originatingAddress = message.getOriginatingAddress();
            String messageBody = message.getMessageBody();
            System.out.println("地址:" + originatingAddress + ";内容:" + messageBody);

            if ("#*location*#".equals(messageBody)) {
                //手机定位
                //中断广播传递
                //4.3+系统上此方法没用, 必须是默认短信app才可以使用此方法, (解决办法: 可以从短信数据库中删除短信记录)
                abortBroadcast();

                //比较耗时->后台长时间运行->Service        LocationService:位置服务
                context.startService(new Intent(context, LocationService.class));

            } else if ("#*alarm*#".equals(messageBody)) {
                //报警音乐
                abortBroadcast();

                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                player.setVolume(1, 1);//左右声道音量 0-1, 最大, 使用的是多媒体的音量通道, 所以即使电话静音,也能够播放
                player.setLooping(true);//单曲循环
                player.start();
            } else if ("#*wipedata*#".equals(messageBody)) {
                //清除数据
                abortBroadcast();

                //判断是否激活设备管理员
                if (mDPM.isAdminActive(mComponent)) {
                    //mDPM.wipeData(0);//只是清理内部存储, 不清理sdcard
                    mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//同时清理sdcard
                }
            } else if ("#*lockscreen*#".equals(messageBody)) {
                //远程锁屏
                abortBroadcast();

                //判断是否激活设备管理员
                if (mDPM.isAdminActive(mComponent)) {
                    mDPM.lockNow();//立即锁屏
                    mDPM.resetPassword("123", 0);//重新设置密码123
                }
            }
        }
    }
}
