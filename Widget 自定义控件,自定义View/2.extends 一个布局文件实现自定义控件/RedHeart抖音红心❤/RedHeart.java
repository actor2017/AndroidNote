package com.example.administrator.douyin;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Description: 抖音点击屏幕任意位置飘红心❤效果
 * 使用:
 * <com.example.administrator.douyin.RedHeart
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent" />
 *
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-9-2 on 10:38
 * @version 1.0
 */
public class RedHeart extends RelativeLayout {
    private Context context;
    float[] num = {-20, -10, 0, 10, 20};//随机心形图片角度
    private long clickTime;//点击事件时间

    public RedHeart(Context context) {
        super(context);
        initView(context);
    }

    public RedHeart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RedHeart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            long currentTime = System.currentTimeMillis();
            long timeInterval = currentTime - clickTime;//时间间隔
            clickTime = currentTime;
            /**
             * 手势识别器的双击
             * @see android.view.GestureDetector#isConsideredDoubleTap(MotionEvent, MotionEvent, MotionEvent)
             * @see ViewConfiguration#getDoubleTapMinTime() //40
             * @see ViewConfiguration#getDoubleTapTimeout() //300
             */
            if (timeInterval >= 40 && timeInterval <= ViewConfiguration.getDoubleTapTimeout()) {
                ImageView imageView = new ImageView(context);
                LayoutParams params = new LayoutParams(300, 300);
                params.leftMargin = (int) event.getX() - 150;
                params.topMargin = (int) event.getY() - 300;
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_like_after));
                imageView.setLayoutParams(params);
                addView(imageView);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(scale(imageView, "scaleX", 2f, 0.9f, 100, 0))//x缩小, duration=100, delay=0
                        .with(scale(imageView, "scaleY", 2f, 0.9f, 100, 0))//y缩小, duration=100
                        .with(rotation(imageView, 0, 0, num[new Random().nextInt(4)]))//旋转, duration=0
                        .with(alpha(imageView, 0, 1, 100, 0))//渐变0->1, duration=100
                        .with(scale(imageView, "scaleX", 0.9f, 1, 50, 150))//回弹, duration=50, delay=150
                        .with(scale(imageView, "scaleY", 0.9f, 1, 50, 150))//回弹
                        .with(translationY(imageView, 0, -600, 800, 400))//向上平移0->-600, duration=800, delay=400
                        .with(alpha(imageView, 1, 0, 300, 400))//渐变1->0, duration=300, delay=400
                        .with(scale(imageView, "scaleX", 1, 3f, 700, 400))//缩放1->3, duration=700, delay=400
                        .with(scale(imageView, "scaleY", 1, 3f, 700, 400));

                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        removeViewAt(0);
//                removeViewInLayout(imageView);
                    }
                });
            }
        }
        return super.onTouchEvent(event);
    }

    public static ObjectAnimator scale(View view, String propertyName, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, propertyName, from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator translationX(View view, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationX", from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator translationY(View view, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator alpha(View view, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "alpha", from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator rotation(View view, long time, long delayTime, float... values) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", values);
        rotation.setDuration(time);
        rotation.setStartDelay(delayTime);
        rotation.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        return rotation;
    }
}

