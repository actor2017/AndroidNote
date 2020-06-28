package com.ly.hihifriend.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import com.ly.hihifriend.utils.tencentim.UIKitConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Description: 录制音频等
 * <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/24 on 14:56
 */
public class AudioRecordUtils {

    private static      AudioRecordUtils                       instance = new AudioRecordUtils();
    private boolean playing, innerRecording;
    private long   startTime, endTime;
    //Environment.getExternalStorageDirectory().getAbsolutePath() + /PackageName/record/auto_
    public static final String                                 CURRENT_RECORD_FILE = UIKitConstants.RECORD_DIR + "auto_";
    private             String                                 recordAudioPath;
    private volatile    Boolean                                recording           = false;
    private        MediaPlayer          mPlayer;
    private             AudioRecordCallback mRecordCallback;
    private AudioPlayCallback mPlayCallback;
    private AudioRecord audioRecord;

    public static AudioRecordUtils getInstance() {
        return instance;
    }

    /**
     * 录制.wav
     */
     public void startRecordingWav(AudioRecordCallback callback) {
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
                if (audioRecord != null && innerRecording) {
                    try {
                        innerRecording = false;
                        audioRecord.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getRecordAudioPath() {
        return recordAudioPath;
    }

    //ms
    public long getDuration() {
        return endTime - startTime;
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

    public class RecordThread extends Thread {

        private static final String TAG = "RecorderThread";

        private boolean     isRecording;
        private int         channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
        private int         audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        private int         sampleRate = 44100;
        private int         bufferSizeInBytes;

        public RecordThread() {
            bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRate,
                    channelConfiguration, audioEncoding); // need to be larger than size of a frame

            Log.i(TAG, "bufferSizeInBytes=" + bufferSizeInBytes);

            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    sampleRate, channelConfiguration, audioEncoding,
                    bufferSizeInBytes); //麦克风
//      audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_CALL,
//              sampleRate, channelConfiguration, audioEncoding,
//              bufferSizeInBytes);
        }

        public AudioRecord getAudioRecord() {
            return audioRecord;
        }

        public boolean isRecording() {
            return this.isAlive() && isRecording;
        }

        public void startRecording() {
            try {
                startTime = System.currentTimeMillis();
                audioRecord.startRecording();
                isRecording = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stopRecording() {
            try {
                audioRecord.stop();
                isRecording = false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {

//            File dir = new File(Environment.getExternalStorageDirectory()
//                    .getAbsolutePath(), "ivr_record");
            File dir = new File(CURRENT_RECORD_FILE);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File recordingFile = new File(dir, "ivr_" + System.currentTimeMillis() + ".wav");

            recordAudioPath = recordingFile.getAbsolutePath();
            Log.i(TAG, "start recording, 文件路径=" + recordAudioPath);

            OutputStream out = null;
            ByteArrayOutputStream baos = null;

            try {
                baos = new ByteArrayOutputStream();

                startRecording();

                byte[] buffer = new byte[bufferSizeInBytes];

                int bufferReadResult = 0;

                innerRecording = true;
                while (isRecording && innerRecording) {

                    bufferReadResult = audioRecord.read(buffer, 0,
                            bufferSizeInBytes);

                    if(bufferReadResult>0){
                        baos.write(buffer, 0, bufferReadResult);
                    }
                }

                Log.i(TAG, "stop recording,file=" + recordAudioPath);

                buffer = baos.toByteArray();

                Log.i(TAG, "audio byte len="+buffer.length);

                out = new FileOutputStream(recordingFile);
                out.write(getWavHeader(buffer.length));
                out.write(buffer);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(baos!=null){
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public byte[] getWavHeader(long totalAudioLen){
            int mChannels = 1;
            long totalDataLen = totalAudioLen + 36;
            long longSampleRate = sampleRate;
            long byteRate = sampleRate * 2 * mChannels;

            byte[] header = new byte[44];
            header[0] = 'R';  // RIFF/WAVE header
            header[1] = 'I';
            header[2] = 'F';
            header[3] = 'F';
            header[4] = (byte) (totalDataLen & 0xff);
            header[5] = (byte) ((totalDataLen >> 8) & 0xff);
            header[6] = (byte) ((totalDataLen >> 16) & 0xff);
            header[7] = (byte) ((totalDataLen >> 24) & 0xff);
            header[8] = 'W';
            header[9] = 'A';
            header[10] = 'V';
            header[11] = 'E';
            header[12] = 'f';  // 'fmt ' chunk
            header[13] = 'm';
            header[14] = 't';
            header[15] = ' ';
            header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
            header[17] = 0;
            header[18] = 0;
            header[19] = 0;
            header[20] = 1;  // format = 1
            header[21] = 0;
            header[22] = (byte) mChannels;
            header[23] = 0;
            header[24] = (byte) (longSampleRate & 0xff);
            header[25] = (byte) ((longSampleRate >> 8) & 0xff);
            header[26] = (byte) ((longSampleRate >> 16) & 0xff);
            header[27] = (byte) ((longSampleRate >> 24) & 0xff);
            header[28] = (byte) (byteRate & 0xff);
            header[29] = (byte) ((byteRate >> 8) & 0xff);
            header[30] = (byte) ((byteRate >> 16) & 0xff);
            header[31] = (byte) ((byteRate >> 24) & 0xff);
            header[32] = (byte) (2 * mChannels);  // block align
            header[33] = 0;
            header[34] = 16;  // bits per sample
            header[35] = 0;
            header[36] = 'd';
            header[37] = 'a';
            header[38] = 't';
            header[39] = 'a';
            header[40] = (byte) (totalAudioLen & 0xff);
            header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
            header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
            header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

            return header;
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

    public interface AudioRecordCallback {
        void recordComplete(String audioPath, long duration);
        void recordCancel();
    }

    public interface AudioPlayCallback {
        void playComplete();
    }
}
