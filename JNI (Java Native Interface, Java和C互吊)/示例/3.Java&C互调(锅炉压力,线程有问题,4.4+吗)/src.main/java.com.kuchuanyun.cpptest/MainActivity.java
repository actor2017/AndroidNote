package com.kuchuanyun.cpptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.kuchuanyun.cpptest.utils.ThreadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pb)
    ProgressBar pb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    static {
        System.loadLibrary("native-lib");
    }

    public native void startMonitor();
    public native void stopMonitor();

    /**
     * 注意,由于线程问题,这个方法一调用就崩溃!!!!!!!!!!!!!!!
     * @param progress
     */
    public void setPress(int progress) {
        System.out.println(ThreadUtils.isRunOnUiThread());
        System.out.println(progress);
        try {
            pb.setProgress(progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.image_btn, R.id.url_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_btn:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        startMonitor();
                    }
                }.start();
                break;
            case R.id.url_btn:
                stopMonitor();
                break;
        }
    }
}
