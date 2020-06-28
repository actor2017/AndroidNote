package cn.itcast.mobilesafe12.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import cn.itcast.mobilesafe12.R;

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        //启动渐变动画
        AlphaAnimation anim = new AlphaAnimation(0.3f, 1);
        anim.setDuration(2000);
        rlRoot.startAnimation(anim);
    }
}
