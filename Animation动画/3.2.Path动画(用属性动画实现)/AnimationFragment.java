package com.itheima.androidl.fragment;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.view.View;
import android.view.animation.PathInterpolator;

import com.itheima.androidl.R;

/**
 * 全新动画
 */
public class AnimationFragment extends BaseFragment implements View.OnClickListener {

    protected View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_animation, null);
        view.findViewById(R.id.curved).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.curved:   //曲线动画
                curved(v);
                break;
        }
    }

    private void curved(View view) {
        Path path = new Path();
        //path.moveTo(view.getX(), view.getY());
        //path.lineTo(200, 200);
        //path.lineTo(600, 600);
        //path.arcTo(r,0,180,false);
        //path.rQuadTo(500,300,300,700);
        //path.cubicTo(100,100,500,300,300,700);
        //path.rCubicTo(100,100,500,300,300,700);
        //path.addArc(100,100,1000,700,-180,180);
        //path.cubicTo(100,100,200,200,300,300);
        path.moveTo(100, 100);
        path.quadTo(1000, 300, 300, 700);

        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);

        Path p = new Path();
        p.lineTo(0.6f, 0.9f);
        p.lineTo(0.75f, 0.2f);
        p.lineTo(1f, 1f);
        mAnimator.setInterpolator(new PathInterpolator(p));
        mAnimator.setDuration(3000);
        mAnimator.start();
    }
}
