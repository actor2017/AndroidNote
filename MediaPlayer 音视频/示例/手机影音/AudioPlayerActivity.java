package com.itheima.mobileplayer.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itheima.mobileplayer.R;
import com.itheima.mobileplayer.bean.AudioBean;
import com.itheima.mobileplayer.lyric.LyricView;
import com.itheima.mobileplayer.service.AudioService;
import com.itheima.mobileplayer.utils.StringUtils;

import java.util.List;

/**
 * Description:音乐播放界面
 * View:只做界面展示
 * Copyright  : Copyright (c) 2016
 * Company    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/17 9:08
 * TODO 把Service中的Notification移过来
 */
public class AudioPlayerActivity extends BaseActivity implements View.OnClickListener {


    private View                      ivBack;//返回键
    private TextView                       tvTitle;//标题
    private View                      ivWave;//示波器的帧动画
    private LyricView                      mLyricView;//歌词
    private TextView                       tvPlayTotal;//已播放时间/总时间
    private SeekBar                        sbProgress;//播放进度
    private View                         btnPlayMode;//播放模式
    private View                         btnPre;//上一曲
    private View                         btnPlayPause;//暂停/播放
    private View                         btnNext;//下一曲

    private static final int MSG_UPDATE_PLAY_TIME = 0;//更新播放时间
    private static final int MSG_ROLL_LYRICS      = 1;//滚动歌词

    private ServiceConnection   serviceConnection;//连接Service
    private AudioService        mService;//Service

    @Override
    public int getLayout() {
        return R.layout.activity_audio_player;
    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_audio_back);
        tvTitle = (TextView) findViewById(R.id.tv_audio_title);
        ivWave = findViewById(R.id.iv_wave);
        mLyricView = (LyricView) findViewById(R.id.lyric_view);
        tvPlayTotal = (TextView) findViewById(R.id.tv_play_total_time);
        sbProgress = (SeekBar) findViewById(R.id.sb_audio_progress);
        btnPlayMode = findViewById(R.id.btn_play_mode);
        btnPre = findViewById(R.id.btn_pre);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        btnNext = findViewById(R.id.btn_next);
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        intent.setClass(this, AudioService.class);

        /**
         * 先bind绑定服务,如果先startService的话,音乐准备完成而绑定没完成,造成第一首歌不能OnPrepared回调
         * 可以调用服务里的方法
         */
        serviceConnection = new ServiceConnection();
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

        //start开启服务，让其长期运行在后台
        startService(intent);

        //开启示波器的帧动画
        AnimationDrawable ad = (AnimationDrawable) ivWave.getBackground();
        ad.start();
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        sbProgress.setOnSeekBarChangeListener(new AudioSeekBarChangeListener());
        btnPlayMode.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnPlayPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_audio_back://返回
                finish();
                break;
            case R.id.btn_play_mode://设置播放模式
                switchPlayMode();
                break;
            case R.id.btn_pre://上一曲
                mService.playPre();
                break;
            case R.id.btn_play_pause://播放、暂停
                switchPlayPause();
                break;
            case R.id.btn_next://下一曲
                mService.playNext();
                break;
        }
    }

    private void switchPlayMode() {
        mService.switchPlayMode();
        updatePlayModeStatus();
    }

    /**
     * 修改播放模式的背景图片
     */
    public void updatePlayModeStatus() {
        switch (mService.getPlayMode()) {
            case AudioService.PLAY_MODE_REPATE_ALL: //顺序播放
                btnPlayMode.setBackgroundResource(R.drawable.audio_playmode_repate_all_selector);
                break;
            case AudioService.PLAY_MODE_REPATE_SINGLE: //单曲循环
                btnPlayMode.setBackgroundResource(R.drawable.audio_playmode_repate_single_selector);
                break;
            case AudioService.PLAY_MODE_RANDOM: //随机播放播放
                btnPlayMode.setBackgroundResource(R.drawable.audio_playmode_repate_random_selector);
                break;
        }
    }

    /**
     * 播放、暂停
     */
    private void switchPlayPause() {
        mService.switchPlayPause();
        updatePlayPauseStatus();
    }

    private class ServiceConnection implements android.content.ServiceConnection {
        //服务连接成功后调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.Music music = (AudioService.Music) service;
            mService = music.getService();

            mService.setOnPlayListener(new AudioService.OnPlayListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    updateUI();
                }

                @Override
                public void onPlay(MediaPlayer mp, List<AudioBean> audioList, int position) {
                    System.out.println("onPlay");
                    updateUI();
                }
            });
        }

        //服务失去连接
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private void updateUI() {
                updatePlayPauseStatus();

                //设置音乐标题
                AudioBean bean = mService.getPlayingMusic();
                String title = bean.getTitle();
                tvTitle.setText(title);

                //把进度的最大值和音乐的总是关联起来
                sbProgress.setMax(mService.getTotalTime());
                //设置音乐已播放、总时间
                startPlayTotalTime();

                //修改播放模式的背景图片
                updatePlayModeStatus();

                //从歌词文件中加载歌词
//            File file = new File("/mnt/sdcard/Download/" + title+".lrc");
//            mLyricView.parseLyrics(file);
                mLyricView.parseLyrics(title);

                //滚动歌词
                rollLyrics();
    }

//            }
//        }
//    }

    private void rollLyrics() {
        mLyricView.roll(mService.getCurrentTime(), mService.getTotalTime());
        //实时处理消息
        mHandler.sendEmptyMessage(MSG_ROLL_LYRICS);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_PLAY_TIME://已播放时间
                    startPlayTotalTime();
                    break;
                case MSG_ROLL_LYRICS:
                    rollLyrics();
                    break;
            }
        }
    };

    /**
     * 设置音乐已播放、总时间
     */
    private void startPlayTotalTime() {
        int currentTime = mService.getCurrentTime();

        updatePlayTime(currentTime);

        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PLAY_TIME, 500);
    }

    /**
     * 更新音乐的已播放时间、修改进度条的进度
     */
    private void updatePlayTime(int currentTime) {
        int totalTime = mService.getTotalTime();
        tvPlayTotal.setText(StringUtils.formatDuration(currentTime) + "/" + StringUtils.formatDuration(totalTime));
        sbProgress.setProgress(currentTime);
    }

    /**
     * 更新播放、暂停按钮的背景图片
     */
    private void updatePlayPauseStatus() {
        if (mService.isPlaying()) {
            btnPlayPause.setBackgroundResource(R.drawable.audio_pause_selector);
        } else {
            btnPlayPause.setBackgroundResource(R.drawable.audio_play_selector);
        }
    }

    /**
     * 音乐播放进度的改变
     */
    private class AudioSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mService.seekTo(progress);
                updatePlayTime(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
