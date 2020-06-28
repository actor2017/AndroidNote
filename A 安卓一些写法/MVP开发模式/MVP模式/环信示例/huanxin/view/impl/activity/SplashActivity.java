package com.shijing.huanxin.view.impl.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.shijing.huanxin.R;
import com.shijing.huanxin.presenter.SplashPresenter;
import com.shijing.huanxin.view.ISplashView;

/**
 * mvp的全称为Model-View-Presenter，Model提供数据，View负责显示，
 * Controller/Presenter负责逻辑的处理
 */
public class SplashActivity extends BaseActivity implements ISplashView {

    SplashPresenter splashPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashPresenter = new SplashPresenter(this);//初始化p层

        ImageView iv = (ImageView) findViewById(R.id.iv);
        AlphaAnimation anim = new AlphaAnimation(0,1);
        anim.setDuration(2000);
        anim.setFillAfter(true);
        iv.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashPresenter.checkLogin();//调用p层的逻辑
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void switchToMainActivity() {
        startActivity(MainActivity.class,true);
    }

    @Override
    public void switchToLoginActivity() {
        startActivity(LoginActivity.class,true);
    }
}
