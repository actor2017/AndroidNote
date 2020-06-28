package com.itheima.androidl.fragment;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.LinearInterpolator;

import com.itheima.androidl.R;

/**
 * 全新动画
 */
public class AnimationFragment extends BaseFragment implements View.OnClickListener {

    protected View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_animation, null);
		view.findViewById(R.id.btn1).setOnClickListener(this);
        view.findViewById(R.id.bt_circle).setOnClickListener(this);
        view.findViewById(R.id.btn2).setOnClickListener(this);
        view.findViewById(R.id.ll_btn).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
            case R.id.bt_circle:
            case R.id.ll_btn:
                /**
                 * View view        你要进行圆形缩放的view
                 * int centerX      开始缩放点的x坐标
                 * int centerY      开始缩放点的y坐标
                 * float startRadius开始缩放时的半径
                 * float endRadius  结束缩放后的半径
                 */
                Animator animator1 = ViewAnimationUtils.createCircularReveal(v, v.getWidth() / 2, v.getHeight() / 2,
                        v.getWidth(), 0);
                animator1.setInterpolator(new LinearInterpolator());//线性匀速改变
                animator1.setDuration(1000);
                animator1.start();
                break;
            case R.id.btn2:
                Animator animator2 = ViewAnimationUtils.createCircularReveal(v, 0, v.getHeight(),//开始坐标
                        0, (float) Math.hypot(v.getWidth(), v.getHeight()));//结束位置
                animator2.setDuration(1000);
                animator2.start();
                break;
        }
    }
}
