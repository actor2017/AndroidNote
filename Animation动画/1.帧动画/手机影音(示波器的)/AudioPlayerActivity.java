package com.itheima.mobileplayer.ui.activity;

import android.graphics.drawable.AnimationDrawable;

public class AudioPlayerActivity extends BaseActivity {


    private View                      ivWave;//示波器的帧动画

    @Override
    public void initView() {
        ivWave = findViewById(R.id.iv_wave);
		//开启示波器的帧动画
        AnimationDrawable ad = (AnimationDrawable) ivWave.getBackground();
        ad.start();
    }
}
