package com.ly.hihifriend.utils.tencentim;


import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.ly.hihifriend.utils.ToastUtils;

/**
 * Description: 来自腾讯TUIKit Demo中的录音和播放
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/5/30 on 17:43
 */
public class UIKitAudioArmMachine {

    private boolean playing, innerRecording;
    private volatile Boolean             recording           = false;
    //Environment.getExternalStorageDirectory().getAbsolutePath() + /PackageName/record/auto_
    public static    String              CURRENT_RECORD_FILE = UIKitConstants.RECORD_DIR + "auto_";
    private          AudioRecordCallback mRecordCallback;
    private          AudioPlayCallback   mPlayCallback;

    private String recordAudioPath;
    private long   startTime, endTime;
    private        MediaPlayer          mPlayer;
    private        MediaRecorder        mRecorder;
    private static UIKitAudioArmMachine instance = new UIKitAudioArmMachine();

    private UIKitAudioArmMachine() {
//        throw new RuntimeException("u can't instance me!");
    }

    public static UIKitAudioArmMachine getInstance() {
        return instance;
    }

    /**
     * 开始录音
     */
    public void startRecord(AudioRecordCallback callback) {
        synchronized (recording) {
            mRecordCallback = callback;
            recording = true;
            new RecordThread().start();
        }
    }

    /**
     * 结束录音
     * @param isCanceled 是否已经取消
     */
    public void stopRecord(boolean isCanceled) {
        synchronized (recording) {
            if (recording) {
                recording = false;
                endTime = System.currentTimeMillis();
                if (mRecordCallback != null) {
                    if (isCanceled) {
                        mRecordCallback.recordCancel();//子线程
                    } else mRecordCallback.recordComplete(getRecordAudioPath(), endTime - startTime);//子线程
                }
                if (mRecorder != null && innerRecording) {
                    try {
                        innerRecording = false;
                        mRecorder.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 播放录音
     */
    public void playRecord(String filePath, AudioPlayCallback callback) {
        this.mPlayCallback = callback;
        new PlayThread(filePath).start();
    }

    /**
     * 停止播放录音
     */
    public void stopPlayRecord() {
        if (mPlayer != null) {
            mPlayer.stop();
            playing = false;
            mPlayCallback.playComplete();
        }
    }

    public boolean isPlayingRecord() {
        return playing;
    }


    public String getRecordAudioPath() {
        return recordAudioPath;
    }

    //ms
    public long getDuration() {
        return endTime - startTime;
    }

    public interface AudioRecordCallback {
        void recordComplete(String audioPath, long duration);
        void recordCancel();
    }

    public interface AudioPlayCallback {
        void playComplete();
    }


    private class RecordThread extends Thread {
        @Override
        public void run() {
            //根据采样参数获取每一次音频采样大小
            try {
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //RAW_AMR虽然被高版本废弃，但它兼容低版本还是可以用的
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                recordAudioPath = CURRENT_RECORD_FILE + System.currentTimeMillis() + ".amr";
                mRecorder.setOutputFile(recordAudioPath);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                startTime = System.currentTimeMillis();
                synchronized (recording) {
                    if (!recording) return;
                    mRecorder.prepare();
                    mRecorder.start();
                }
                innerRecording = true;
                new Thread() {
                    @Override
                    public void run() {
                        while (recording && innerRecording) {
                            try {
                                RecordThread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (System.currentTimeMillis() - startTime >= TencentImUtils.getBaseConfigs().getAudioRecordMaxTime() * 1000) {
                                stopRecord(false);
                                return;
                            }
                        }
                    }
                }.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class PlayThread extends Thread {
        String audioPath;

        PlayThread(String filePath) {
            audioPath = filePath;
        }

        public void run() {
            try {
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(audioPath);
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO: 2019/6/3 新增判空
                        if (mPlayCallback != null) mPlayCallback.playComplete();
                        playing = false;
                    }
                });
                mPlayer.prepare();
                mPlayer.start();
                playing = true;
            } catch (Exception e) {
                ToastUtils.showDefault("语音文件已损坏或不存在");
                e.printStackTrace();
                // TODO: 2019/6/3 新增判空
                if (mPlayCallback != null) mPlayCallback.playComplete();
                playing = false;
            }
        }
    }
}
