package com.kuchuanyun.wisdompolice4sd.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.kuchuanyun.wisdompolice4sd.R;
import com.kuchuanyun.wisdompolice4sd.activity.MainActivity;
import com.kuchuanyun.wisdompolice4sd.application.MyApplication;

/**
 * Description: 类的描述
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2018/7/31 on 09:40
 */

public class NotificationUtils {

    private static final Context context = MyApplication.instance.applicationContext;
    private static final int largeIcon = R.drawable.icon;//设置通知左边的大图标
    private static final int smileIcon = R.drawable.icon_smile;//通知右边的小图标
    private static final NotificationManager notificationManager = (NotificationManager) context.
            getSystemService(context.NOTIFICATION_SERVICE);

    //抄自极光推送: JPushInterface.goToAppNotificationSettings(context);
    public static boolean goToAppNotificationSettings(Context var0) {
        try {
            Intent var1 = new Intent();
            var1.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            Uri var2 = Uri.fromParts("package", var0.getPackageName(), (String)null);
            var1.setData(var2);
            var0.startActivity(var1);
            return true;
        } catch (Throwable var3) {
            return false;
        }
    }

    /**
     * PendingIntent说明:
     * //获取一个用于启动 Activity 的 PendingIntent 对象
     * public static PendingIntent getActivity(Context context, int requestCode, Intent intent, int flags);
     * //获取一个用于启动 Service 的 PendingIntent 对象
     * public static PendingIntent getService(Context context, int requestCode, Intent intent, int flags);
     * //获取一个用于向 BroadcastReceiver 广播的 PendingIntent 对象
     * public static PendingIntent getBroadcast(Context context, int requestCode, Intent intent, int flags);
     *
     * FLAG_CANCEL_CURRENT:如果当前系统中已经存在一个相同的PendingIntent对象,那么就将先将已有的PendingIntent取消,然后重新生成一个PendingIntent对象。
     * FLAG_NO_CREATE:如果当前系统中不存在相同的 PendingIntent 对象，系统将不会创建该 PendingIntent 对象而是直接返回 null 。
     * FLAG_ONE_SHOT:该PendingIntent只作用一次。
     * FLAG_UPDATE_CURRENT:如果系统中已存在该PendingIntent对象,那么系统将保留该PendingIntent对象,但是会使用新的Intent来更新之前PendingIntent中的Intent对象数据
     */

    public static void showNotification(Intent intent, String ticker, String title, String content, String tag, int id) {
        showNotification(intent, ticker, title, content, tag, id, 0);
    }

    /**
     * 显示一个普通的通知
     * @param intent
     * @param ticker 刚接收到通知的时候显示的消息
     * @param title 通知的标题
     * @param content 这是一个通知的内容
     * @param tag 这个通知的字符串标识符,示例:task,alarm,msg
     * @param id 如果之前的通知还未被取消,则会直接更新该通知相关的属性.如果之前的通知已经被取消,则会重新创建一个新通知
     * @param soundRawId 自定义声音,示例:R.raw.you_received_an_new_alarm(res/raw这个文件夹)
     */
    public static void showNotification(Intent intent, String ticker, String title, String content, String tag, int id, int soundRawId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //设置通知左边的大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon))
                //设置通知右边的小图标
                .setSmallIcon(smileIcon)
                //通知首次出现在通知栏，带上升动画效果的
                .setTicker(ticker)
                //设置通知的标题
                .setContentTitle(title)
                //设置通知的内容
                .setContentText(content)
                //设置通知产生的时间,会在通知信息里显示,默认为系统发出通知的时间,通常不用设置
//                .setWhen(System.currentTimeMillis())
                //设置该通知优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //设置这个标志当用户单击面板就可以让通知将自动取消
                .setAutoCancel(true)
                //设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用
                //户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setOngoing(false)
                .setContentIntent(PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT));

        if (soundRawId != 0) {
            builder
//        .setSound(Uri.parse("file:///sdcard/xx/xx.mp3"));
//        .setSound(Uri.parse("file:///sdcard/xx/xx.mp3"))
//        .setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "5"))//获取Android多媒体库内的铃声
                    //.setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2"));//调用系统多媒体裤内的铃声
                    .setSound(Uri.parse("android.resource://" + context.getPackageName() +"/" + soundRawId));
        } else {
            builder
            //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置,使用defaults属性,可以组合:
//                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
            .setDefaults(Notification.DEFAULT_ALL);
        }

        Notification notification = builder.build();
        if (tag == null) tag = "";
        //发起通知
        notificationManager.notify(tag + id, id, notification);
    }

    /**
     * 显示一个下载带进度条的通知
     */
    public static void showNotificationProgress(String ticker, String title, String tag, int id) {
        //进度条通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(ticker)
                .setContentTitle(title)
                .setSmallIcon(smileIcon)
                .setProgress(100, 0, false);
        Notification notification = builder.build();
        //发送一个通知
        if (tag == null) tag = "";
        notificationManager.notify(tag + id, id, notification);

        //更新进度条
//        builder.setProgress(100, 1, false);
        //再次通知
//        notificationManager.notify(tag + id, id, notification);
        //进度条退出
//        notificationManager.cancel(tag + id, id);
    }

    /**
     * 悬挂式,支持 6.0 以上系统
     */
    @TargetApi(23)
    public static void showFullScreen() {
        if (Build.VERSION.SDK_INT >= 23) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            builder.setAutoCancel(true);
            builder.setContentTitle("悬挂式通知");
            //设置点击跳转
            Intent hangIntent = new Intent();
            hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            hangIntent.setClass(context, MainActivity.class);
            //如果描述的 PendingIntent 已经存在，则在产生新的 Intent 之前会先取消掉当前的
            PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setFullScreenIntent(hangPendingIntent, true);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            notificationManager.notify(3, builder.build());
        }
    }

    /**
     * 折叠式
     */
    public static void shwoNotifyCollapse() {
        //先设定 RemoteViews
//        RemoteViews view_custom = new RemoteViews(context.getPackageName(), R.layout.view_custom);
//        //设置对应 IMAGEVIEW 的 ID 的资源图片
//        view_custom.setImageViewResource(R.id.custom_icon, R.mipmap.icon);
//        view_custom.setTextViewText(R.id.tv_custom_title, "今日头条");
//        view_custom.setTextColor(R.id.tv_custom_title, Color.BLACK);
//        view_custom.setTextViewText(R.id.tv_custom_content, "金州勇士官方宣布球队已经解雇了主帅马克-杰克逊，随后宣布了最后的结果。");
//        view_custom.setTextColor(R.id.tv_custom_content, Color.BLACK);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//        mBuilder.setContent(view_custom)
//                .setContentIntent(PendingIntent.getActivity(context, 4, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT))
//                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
//                .setTicker("有新资讯")
//                .setPriority(Notification.PRIORITY_HIGH)// 设置该通知优先级
//                .setOngoing(false)//不是正在进行的   true 为正在进行  效果和.flag 一样
//                .setSmallIcon(R.mipmap.icon);
//        Notification notify = mBuilder.build();
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.notify(4, notify);
    }

    public static void cancel(String tag, int id) {
        //点击通知栏的清除按钮，会清除所有可清除的通知
        //设置了 setAutoCancel() 或 FLAG_AUTO_CANCEL 的通知，点击该通知时会清除它
        //下面手动清除
        if (tag == null) {
            notificationManager.cancel(id);//清除指定 ID 的通知
        } else {
            notificationManager.cancel(tag, id);//清除指定 TAG 和 ID 的通知
        }
    }

    //清除所有该应用之前发送的通知
    public static void cancelAll() {
        notificationManager.cancelAll();
    }
}
