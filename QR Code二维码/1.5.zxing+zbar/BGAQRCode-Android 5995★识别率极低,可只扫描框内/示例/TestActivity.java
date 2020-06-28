package com.actor.testapplication.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;

import com.actor.testapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class TestActivity extends BaseActivity {

    @BindView(R.id.zxingview)
    ZXingView mZXingView;
    @BindView(R.id.tv_result)//显示结果
    TextView  tvResult;
    @BindView(R.id.btn)
    Button    btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setTitle("测试页面");
        mZXingView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                tvResult.setText("扫描结果为：" + result);
                toast(result);
                vibrate();
                mZXingView.startSpot(); // 开始识别
            }

            @Override
            public void onCameraAmbientBrightnessChanged(boolean isDark) {
                // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
//                String tipText = mZXingView.getScanBoxView().getTipText();
//                String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
//                if (isDark) {
//                    if (!tipText.contains(ambientBrightnessTip)) {
//                        mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
//                    }
//                } else {
//                    if (tipText.contains(ambientBrightnessTip)) {
//                        tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
//                        mZXingView.getScanBoxView().setTipText(tipText);
//                    }
//                }
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                logError("打开相机出错");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
//        mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
    }
}
