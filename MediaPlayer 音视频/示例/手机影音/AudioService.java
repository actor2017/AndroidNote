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
    private int                  positionPlayed = -1;//播放位置
    private NotificationManager  notificationManager;

    private OnPlayListener     onPlayListener;//播放监听

    // TODO: 2019/1/16 不要notification,移到Activity中
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        playMode = sharedPreferences.getInt("play_mode", PLAY_MODE_REPATE_ALL);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int type = intent.getIntExtra(notifyType, -1);
        switch (type) {
            case NOTIFY_CONTENT:  //点击通知栏空白处
                if (onPlayListener != null) onPlayListener.onPlay(mediaPlayer, audioList, positionPlayed);
                break;
            case NOTIFY_PRE:  //上一曲
                playPre();
                break;
            case NOTIFY_NEXT:  //下一曲
                playNext();
                break;
            case -1:
            default:
                audioList = (ArrayList<AudioBean>) intent.getSerializableExtra("list");
                int position = intent.getIntExtra("Position", -1);
                if (positionPlayed == position) {
                    //同一首歌
                    if (onPlayListener != null) onPlayListener.onPlay(mediaPlayer, audioList, positionPlayed);
                } else {
                    positionPlayed = position;
                    playItem();
                }
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Music();
    }

    public class Music extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }
    }

    public int getPlayMode() {
        return playMode;
    }

    /**
     * 获取音乐列表
     */
    public List<AudioBean> getMusicList() {
        return audioList;
    }

    /**
     * 获取播放位置
     */
    public int getPlayPosition() {
        return positionPlayed;
    }

    /**
     * 获取正在播放的音乐
     */
    public AudioBean getPlayingMusic() {
        return audioList.get(positionPlayed);
    }

    /**
     * 修改音乐的播放进度
     * @param progress
     */
    public void seekTo(int progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
        }
    }

    /**
     * 设置播放模式
     * @param playMode {@link PlayMode}
     */
    public void setPlayMode(@PlayMode int playMode) {
        this.playMode = playMode;
        sharedPreferences.edit().putInt("play_mode", playMode).commit();//存储播放模式
    }

    /**
     * 切换播放模式
     */
    public void switchPlayMode() {
        switch (playMode) {
            case PLAY_MODE_REPATE_ALL: //顺序播放
                setPlayMode(PLAY_MODE_REPATE_SINGLE);
                break;
            case PLAY_MODE_REPATE_SINGLE: //单曲循环
                setPlayMode(PLAY_MODE_RANDOM);
                break;
            case PLAY_MODE_RANDOM: //随机播放播放
                setPlayMode(PLAY_MODE_REPATE_ALL);
                break;
        }
    }

    /**
     * 下一曲
     */
    public void playNext() {
        positionPlayed++;
        playItem();
//        Toast.makeText(this, "亲，已经是最后一首歌了，你还想咋地", Toast.LENGTH_SHORT).show();
    }

    /**
     * 上一曲
     */
    public void playPre() {
        positionPlayed--;
        playItem();
//        Toast.makeText(this, "亲，已经是第一首歌了，你还想咋地", Toast.LENGTH_SHORT).show();
    }

    private void playItem() {
        try {
            AudioBean audioBean = audioList.get(positionPlayed);
            if (mediaPlayer != null) {
                mediaPlayer.reset();
            } else {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnPreparedListener(new AudioPreparedListener());
                mediaPlayer.setOnCompletionListener(new CompletionListener());
            }
            mediaPlayer.setDataSource(audioBean.getData());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showNotify();
    }

    /**
     * 音乐准备完成
     */
    private class AudioPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (onPlayListener != null) onPlayListener.onPrepared(mp);
        }
    }

    /**
     * 音乐播放完成
     */
    private class CompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            autoPlayNext();
        }
    }

    /**
     * 根据播放模式自动播放下一首歌
     */
    private void autoPlayNext() {
        switch (playMode) {
            case PLAY_MODE_REPATE_ALL: //顺序播放
                if (positionPlayed == audioList.size() - 1) {
                    positionPlayed = 0;
                } else {
                    positionPlayed++;
                }
                break;
            case PLAY_MODE_REPATE_SINGLE: //单曲循环
                break;
            case PLAY_MODE_RANDOM: //随机播放播放
                Random random = new Random();
                int r = random.nextInt(audioList.size() - 1);
                positionPlayed = r;
                break;
        }
        playItem();
    }

    /**
     * 设置播放监听
     */
    public void setOnPlayListener(OnPlayListener onPlayListener) {
        this.onPlayListener = onPlayListener;
    }
    public interface OnPlayListener {
        /**
         * 准备完成
         */
        void onPrepared(MediaPlayer mp);

        /**
         * 开始播放
         */
        void onPlay(MediaPlayer mp, List<AudioBean> audioList, int position);
    }

    /**
     * 播放、暂停音乐
     */
    public void switchPlayPause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                removeNotify();
            } else {
                mediaPlayer.start();
                showNotify();
            }
        }
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    /**
     * 获取视频的总时间
     */
    public int getTotalTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * 获取音乐播放的当前时间
     */
    public int getCurrentTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
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
