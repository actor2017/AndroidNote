package com.tuacy.transitiondemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SceneTransitionImgActivity extends AppCompatActivity {

    @BindView(R.id.rl)
    RelativeLayout rl;
    private Scene sceneStart;
    private Scene sceneEnd1;
    private Scene sceneEnd2;
    private boolean isSceneStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition_img);
        ButterKnife.bind(this);

        View startView = LayoutInflater.from(this).inflate(R.layout.scene_change_img_start, rl, false);
        startView.findViewById(R.id.img).setClipBounds(new Rect(0, 0, 300, 300));

        //这儿其实就和start一样的布局
        View endView1 = LayoutInflater.from(this).inflate(R.layout.scene_change_img_start, rl, false);
        endView1.findViewById(R.id.img).setClipBounds(new Rect(300, 300, 600, 600));

        View endView2 = LayoutInflater.from(this).inflate(R.layout.scene_change_img_end, rl, false);

        sceneStart = new Scene(rl, startView);
        sceneEnd1 = new Scene(rl, endView1);
        sceneEnd2 = new Scene(rl, endView2);

        TransitionManager.go(sceneStart);
        isSceneStart = true;
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1://图片大小动态切换
                TransitionManager.go(isSceneStart ? sceneEnd1 : sceneStart, new ChangeClipBounds());
                isSceneStart = !isSceneStart;
                break;
            case R.id.btn2:
                TransitionManager.go(isSceneStart ? sceneEnd2 : sceneStart, new ChangeImageTransform());
                isSceneStart = !isSceneStart;
                break;
        }
    }
}
