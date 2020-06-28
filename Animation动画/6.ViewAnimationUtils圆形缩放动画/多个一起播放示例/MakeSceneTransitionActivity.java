package com.tuacy.transitiondemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MakeSceneTransitionActivity extends AppCompatActivity {

    @BindView(R.id.view_bg_top)
    View viewBgTop;
    @BindView(R.id.view_bg_bottom)
    View viewBgBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_animation);
        ButterKnife.bind(this);

        //资源文件指定过渡动画
        //getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_target));
        //代码制定过渡动画
        final TransitionSet transitionSet = new TransitionSet();

        //滑动
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.image_shared);
        transitionSet.addTransition(slide);

        //爆炸,分解
        Explode explode = new Explode();
        explode.excludeTarget(android.R.id.statusBarBackground, true);
        explode.excludeTarget(android.R.id.navigationBarBackground, true);
        explode.excludeTarget(R.id.image_shared, true);
        transitionSet.addTransition(explode);

        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        //设置进入过渡
        getWindow().setEnterTransition(transitionSet);

        //添加监听
        transitionSet.addListener(new Transition.TransitionListener() {

            @Override
            public void onTransitionStart(Transition transition) {
                viewBgTop.setVisibility(View.GONE);
                viewBgBottom.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                viewBgTop.setVisibility(View.VISIBLE);
                viewBgBottom.setVisibility(View.VISIBLE);

                //上半部分View的中心
                Animator animationTop = ViewAnimationUtils.createCircularReveal(viewBgTop, viewBgTop.getWidth() / 2,
                        viewBgTop.getHeight() / 2, 0, Math.max(viewBgTop.getWidth() / 2, viewBgTop.getHeight() / 2));

                //下半部分View的右下角
                Animator animationBottom = ViewAnimationUtils.createCircularReveal(viewBgBottom, viewBgBottom.getWidth(),
                        viewBgBottom.getHeight(), 0, (float) Math.hypot(viewBgBottom.getWidth(), viewBgBottom.getHeight()));

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(500L);
                animatorSet.playTogether(animationTop, animationBottom);
                animatorSet.start();
                transitionSet.removeListener(this);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }
}