package com.tuacy.transitiondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SceneTransitionActivity extends AppCompatActivity {

    @BindView(R.id.rl)
    RelativeLayout rl;
    private Scene sceneStart;
    private Scene sceneEnd1;
    private Scene sceneEnd2;
    private Scene sceneEnd3;
    private boolean isSceneStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition);
        ButterKnife.bind(this);

        //初始化场景
        sceneStart = Scene.getSceneForLayout(rl, R.layout.scene_change_start, this);
        sceneEnd1 = Scene.getSceneForLayout(rl, R.layout.scene_change_end1, this);
        sceneEnd2 = Scene.getSceneForLayout(rl, R.layout.scene_change_end2, this);
        sceneEnd3 = Scene.getSceneForLayout(rl, R.layout.scene_change_end3, this);

        //切换到开始场景状态
        TransitionManager.go(sceneStart);
        isSceneStart = true;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7})
    public void onViewClicked(View view) {//两个场景之间相互切换
        switch (view.getId()) {
            case R.id.btn1:
                //不指定默认就是AutoTransition
                TransitionManager.go(isSceneStart ? sceneEnd1 : sceneStart);
                isSceneStart = !isSceneStart;
                break;
            case R.id.btn2:
                TransitionManager.go(isSceneStart ? sceneEnd1 : sceneStart, new ChangeBounds());
                isSceneStart = !isSceneStart;
                break;
            case R.id.btn3:
                TransitionManager.go(isSceneStart ? sceneEnd2 : sceneStart, new ChangeTransform());
                isSceneStart = !isSceneStart;
                break;
            case R.id.btn4:
                TransitionManager.go(isSceneStart ? sceneEnd3 : sceneStart, new Fade());
                isSceneStart = !isSceneStart;
                break;
            case R.id.btn5:
                TransitionManager.go(isSceneStart ? sceneEnd3 : sceneStart, new Slide());
                isSceneStart = !isSceneStart;
                break;
            case R.id.btn6:
                TransitionManager.go(isSceneStart ? sceneEnd3 : sceneStart, new Explode());//没看出规律
                isSceneStart = !isSceneStart;
                break;
            case R.id.btn7://多个Transition组合
                TransitionManager.go(isSceneStart ? sceneEnd2 : sceneStart, TransitionInflater
                        .from(this).inflateTransition(R.transition.change_bounds_and_transform));//多个Transition组合
                isSceneStart = !isSceneStart;
                break;
        }
    }
}
