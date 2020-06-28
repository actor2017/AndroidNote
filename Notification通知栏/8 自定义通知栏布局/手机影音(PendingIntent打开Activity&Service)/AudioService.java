package com.itheima.mobileplayer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.RemoteViews;

import com.itheima.mobileplayer.R;
import com.itheima.mobileplayer.bean.AudioBean;
import com.itheima.mobileplayer.ui.activity.AudioPlayerActivity;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 播放音乐的服务
 * C ：播放模式、上一曲、下一曲、播放、暂停、展示歌词、显示通知栏
 * Copyright  : Copyright (c) 2016
 * Cpany    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/17 10:23
 */
public class AudioService extends Service {

    public static final  int    PLAY_MODE_REPATE_ALL    = 0;//播放模式:循环所有
    public static final  int    PLAY_MODE_REPATE_SINGLE = 1;//播放模式:单曲循环
    public static final  int    PLAY_MODE_RANDOM        = 2;//播放模式:随机

    @IntDef({PLAY_MODE_REPATE_ALL, PLAY_MODE_REPATE_SINGLE, PLAY_MODE_RANDOM})
    @Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在. class 文件.
    @interface PlayMode {}

    private int playMode = PLAY_MODE_REPATE_ALL;//播放模式,默认循环所有

    public static final  int    NOTIFY_PRE              = 0;//上一首
    public static final  int    NOTIFY_NEXT             = 1;//下一首
    public static final  int    NOTIFY_CONTENT          = 2;//notification被点击了

    private String notifyType = "notifyType";
    private SharedPreferences  sharedPreferences;
    private MediaPlayer          mediaPlayer;
    private ArrayList<AudioBean> audioList;//音乐列表
    private int                  positionPlayed;//播放位置
    private NotificationManager  notificationManager;

    private MediaPlayer.OnPreparedListener onPreparedListener;//音乐准备完成监听
    private OnPlayListener     onPlayListener;//播放监听

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 移除通知栏
     */
    public void removeNotify() {
        notificationManager.cancel(0);
    }

    /**
     * 显示通知栏
     */
    public void showNotify() {
        Notification.Builder builder = new Notification.Builder(this);
        //状态来
        builder.setTicker("正在播放：" + audioList.get(positionPlayed).getTitle());
        builder.setSmallIcon(R.drawable.notification_music_playing);

        //通知栏
        builder.setContent(getRemoteView());
        builder.setContentIntent(getPendingIntent());


        Notification notification = builder.build();

        //显示通知栏
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    /**
     * 获取自定义通知栏的布局
     */
    private RemoteViews getRemoteView() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_audio);
        remoteViews.setTextViewText(R.id.tv_notify_title, audioList.get(positionPlayed).getTitle());
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_pre, getPrePendingIntent());
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_next, getNextPendingIntent());
        return remoteViews;
    }

    /**
     * 点击通知栏，打开音乐播放界面Activity
     */
    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, AudioPlayerActivity.class);
        intent.putExtra(notifyType, NOTIFY_CONTENT);
        PendingIntent pi = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    /**
     * 点击通知栏上一曲, 播放上一曲Service
     */
    private PendingIntent getPrePendingIntent() {
        Intent intent = new Intent(this, AudioService.class);
        intent.putExtra(notifyType, NOTIFY_PRE);
        //打开Service
        PendingIntent pi = PendingIntent.getService(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    /**
     * 点击通知栏下一曲, 播放下一曲Service
     */
    private PendingIntent getNextPendingIntent() {
        Intent intent = new Intent(this, AudioService.class);
        intent.putExtra(notifyType, NOTIFY_NEXT);
        //打开Service
        PendingIntent pi = PendingIntent.getService(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }
}
