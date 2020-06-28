package com.kuchuanyun.cpptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView        iv;
    @BindView(R.id.tv)
    TextView         tv;
    @BindView(R.id.sb)
    AppCompatSeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        /**
         * Drawable子类之——LevelListDrawable （等级列表图片）
         * 常用场景
         * 1、音量大小显示调节
         * 2、简单的进度条
         * 3、wifi信号信息等
         * 4、开灯关灯切换
         * 5、...
         */
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                iv.setImageLevel(progress);//关键代码
                tv.setText("setImageLevel: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
