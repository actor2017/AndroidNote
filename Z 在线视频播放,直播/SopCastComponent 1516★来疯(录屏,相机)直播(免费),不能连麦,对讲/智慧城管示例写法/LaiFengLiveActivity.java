package com.kuchuanyun.wisdomcitymanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.jaeger.library.StatusBarUtil;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.bean.VideoCloseBean;
import com.kuchuanyun.wisdomcitymanagement.global.Global;
import com.laifeng.sopcastsdk.camera.CameraListener;
import com.laifeng.sopcastsdk.configuration.AudioConfiguration;
import com.laifeng.sopcastsdk.configuration.CameraConfiguration;
import com.laifeng.sopcastsdk.configuration.VideoConfiguration;
import com.laifeng.sopcastsdk.stream.packer.rtmp.RtmpPacker;
import com.laifeng.sopcastsdk.stream.sender.rtmp.RtmpSender;
import com.laifeng.sopcastsdk.ui.CameraLivingView;
import com.laifeng.sopcastsdk.utils.SopCastLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 直播页面
 */
public class LaiFengLiveActivity extends BaseActivity {

    @BindView(R.id.clv_cameraLivingView)
    CameraLivingView clvCameraLivingView;
    private VideoConfiguration mVideoConfiguration;
    private RtmpSender mRtmpSender;
    private int mCurrentBps;
    private boolean isRecording;
    private String rtmpAddress;
    public static final String TAG = LaiFengLiveActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lai_feng_live);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucentForImageView(this, 30, null);//设置透明度
        rtmpAddress = getIntent().getStringExtra(Global.URL);
        if (TextUtils.isEmpty(rtmpAddress)) {
            return;
        }
//        rtmpAddress = "rtmp://192.168.0.191/live/1001";//rtmp://192.168.1.104:1935/live/12345
        initLiveView();//初始化直播
        clvCameraLivingView.mute(true);//无声=true
        EventBus.getDefault().register(this);//①.所谓的注册，其实就是将当前这个对象放到一个集合中保存起来
    }

    private void initLiveView() {//初始化直播
        SopCastLog.isOpen(true);
        clvCameraLivingView.init();
        CameraConfiguration.Builder cameraBuilder = new CameraConfiguration.Builder();
        cameraBuilder.setOrientation(CameraConfiguration.Orientation.PORTRAIT)
                .setFacing(CameraConfiguration.Facing.BACK);
        CameraConfiguration cameraConfiguration = cameraBuilder.build();
        clvCameraLivingView.setCameraConfiguration(cameraConfiguration);

        VideoConfiguration.Builder videoBuilder = new VideoConfiguration.Builder();
        videoBuilder.setSize(640, 360);
        mVideoConfiguration = videoBuilder.build();
        clvCameraLivingView.setVideoConfiguration(mVideoConfiguration);

        //初始化flv打包器
        RtmpPacker packer = new RtmpPacker();
        packer.initAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        clvCameraLivingView.setPacker(packer);
        //设置发送器
        mRtmpSender = new RtmpSender();
        mRtmpSender.setVideoParams(640, 360);
        mRtmpSender.setAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mRtmpSender.setSenderListener(mSenderListener);
        clvCameraLivingView.setSender(mRtmpSender);
        clvCameraLivingView.setLivingStartListener(new CameraLivingView.LivingStartListener() {
            @Override
            public void startError(int error) {
                //开始直播失败start living fail
                toast("开始直播失败");
                clvCameraLivingView.stop();
            }

            @Override
            public void startSuccess() {
                //直播成功start living
                toast("直播成功");
            }
        });

        //设置预览监听
        clvCameraLivingView.setCameraOpenListener(new CameraListener() {
            @Override
            public void onOpenSuccess() {//camera open success
                toast("相机打开成功");
                startLiving(rtmpAddress);
            }

            @Override
            public void onOpenFail(int error) {//camera open fail
                toast("相机打开失败");
            }

            @Override
            public void onCameraChange() {//camera switch
                toast("相机切换成功");
            }
        });
    }

    //推流监听
    private RtmpSender.OnSenderListener mSenderListener = new RtmpSender.OnSenderListener() {
        @Override
        public void onConnecting() {//onConnecting
            toast("连接中...");
        }

        @Override
        public void onConnected() {//onConnected
            toast("连接成功");
//            mProgressConnecting.setVisibility(View.GONE);
            clvCameraLivingView.start();
            mCurrentBps = mVideoConfiguration.maxBps;
        }

        @Override
        public void onDisConnected() {//断开连接
//            mProgressConnecting.setVisibility(View.GONE);
//            btnRecord.setBackgroundResource(R.drawable.ic_record_start);
            toast("断开连接");
            clvCameraLivingView.stop();
            isRecording = false;
        }

        @Override
        public void onPublishFail() {//fail to publish stream
//            mProgressConnecting.setVisibility(View.GONE);
            toast("视频连接失败");
//            btnRecord.setBackgroundResource(R.drawable.ic_record_start);
            isRecording = false;
        }

        @Override
        public void onNetGood() {
            if (mCurrentBps + 50 <= mVideoConfiguration.maxBps) {
                SopCastLog.d(TAG, "BPS_CHANGE good up 50");
                int bps = mCurrentBps + 50;
                if (clvCameraLivingView != null) {
                    boolean result = clvCameraLivingView.setVideoBps(bps);
                    if (result) {
                        mCurrentBps = bps;
                    }
                }
            } else {
                SopCastLog.d(TAG, "BPS_CHANGE good good good");
            }
            SopCastLog.d(TAG, "Current Bps: " + mCurrentBps);
        }

        @Override
        public void onNetBad() {
            toast("网速差");
            if (mCurrentBps - 100 >= mVideoConfiguration.minBps) {
                SopCastLog.d(TAG, "BPS_CHANGE bad down 100");
                int bps = mCurrentBps - 100;
                if (clvCameraLivingView != null) {
                    boolean result = clvCameraLivingView.setVideoBps(bps);
                    if (result) {
                        mCurrentBps = bps;
                    }
                }
            } else {
                SopCastLog.d(TAG, "BPS_CHANGE bad down 100");
            }
            SopCastLog.d(TAG, "Current Bps: " + mCurrentBps);
        }
    };

    private void startLiving(String rtmpAddress) {
        if (TextUtils.isEmpty(rtmpAddress)) {
            toast("Upload address is empty!");
        } else {//start connecting
            mRtmpSender.setAddress(rtmpAddress);
//                    mProgressConnecting.setVisibility(View.VISIBLE);
            toast("开始直播");
//            btnRecord.setBackgroundResource(R.drawable.ic_record_stop);
            mRtmpSender.connect();
            isRecording = true;
        }
    }

    //领导端结束视频
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void videoClose(VideoCloseBean videoCloseBean) {
        if (videoCloseBean.isClose) {
            toast("对方关闭了视频!");
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        clvCameraLivingView.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        clvCameraLivingView.resume();
    }

    private Timer timer = new Timer();
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        rtmpAddress = intent.getStringExtra(Global.URL);
        clvCameraLivingView.stop();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startLiving(rtmpAddress);
            }
        },2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//③.所谓的注销，其实就是将当前这个对象从集合中移除
        clvCameraLivingView.stop();
        clvCameraLivingView.release();
    }
}
