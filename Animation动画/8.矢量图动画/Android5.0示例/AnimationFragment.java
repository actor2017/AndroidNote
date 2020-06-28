package com.itheima.androidl.fragment;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.itheima.androidl.R;

/**
 * 全新动画
 */
public class AnimationFragment extends BaseFragment implements View.OnClickListener {

    protected View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_animation, null);
        view.findViewById(R.id.vector_anim).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vector_anim://矢量图动画
                Drawable drawable = v.getBackground();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                break;
        }
    }
}
