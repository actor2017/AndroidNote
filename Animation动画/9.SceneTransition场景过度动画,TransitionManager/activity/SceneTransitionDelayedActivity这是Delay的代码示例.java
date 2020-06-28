package com.tuacy.transitiondemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class SceneTransitionDelayedActivity extends AppCompatActivity {

    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.image_scene_delayed_one)
    CircleImageView img1;
    @BindView(R.id.image_scene_delayed_two)
    CircleImageView img2;
    @BindView(R.id.image_scene_delayed_three)
    CircleImageView img3;
    @BindView(R.id.image_scene_delayed_four)
    CircleImageView img4;
    private boolean isSceneStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition_delayed);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        isSceneStart = !isSceneStart;
        //TransitionManager.beginDelayedTransition(ViewGroup sceneRoot, Transition transition)
        TransitionManager.beginDelayedTransition(rl, TransitionInflater.from(this)
                .inflateTransition(R.transition.change_bounds_and_transform));

        //四个角的View相互交换位置
        if (isSceneStart) {
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) img1.getLayoutParams();
            params1.removeRule(RelativeLayout.ALIGN_PARENT_START);
            params1.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            params1.removeRule(RelativeLayout.ALIGN_PARENT_END);
            params1.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params1.addRule(RelativeLayout.ALIGN_PARENT_END);
            params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            img1.setLayoutParams(params1);

            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) img2.getLayoutParams();
            params2.removeRule(RelativeLayout.ALIGN_PARENT_START);
            params2.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            params2.removeRule(RelativeLayout.ALIGN_PARENT_END);
            params2.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params2.addRule(RelativeLayout.ALIGN_PARENT_START);
            params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            img2.setLayoutParams(params2);

            RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) img3.getLayoutParams();
            params3.removeRule(RelativeLayout.ALIGN_PARENT_START);
            params3.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            params3.removeRule(RelativeLayout.ALIGN_PARENT_END);
            params3.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params3.addRule(RelativeLayout.ALIGN_PARENT_START);
            params3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            img3.setLayoutParams(params3);

            RelativeLayout.LayoutParams params4 = (RelativeLayout.LayoutParams) img4.getLayoutParams();
            params4.removeRule(RelativeLayout.ALIGN_PARENT_START);
            params4.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            params4.removeRule(RelativeLayout.ALIGN_PARENT_END);
            params4.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params4.addRule(RelativeLayout.ALIGN_PARENT_END);
            params4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            img4.setLayoutParams(params4);

            img2.setScaleX(0.5f);
            img2.setScaleY(0.5f);
            img4.setRotation(90);
        } else {
            RelativeLayout.LayoutParams oneParams = (RelativeLayout.LayoutParams) img1.getLayoutParams();
            oneParams.removeRule(RelativeLayout.ALIGN_PARENT_START);
            oneParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            oneParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
            oneParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            oneParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            oneParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            img1.setLayoutParams(oneParams);

            RelativeLayout.LayoutParams twoParams = (RelativeLayout.LayoutParams) img2.getLayoutParams();
            twoParams.removeRule(RelativeLayout.ALIGN_PARENT_START);
            twoParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            twoParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
            twoParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            twoParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            twoParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            img2.setLayoutParams(twoParams);

            RelativeLayout.LayoutParams threeParams = (RelativeLayout.LayoutParams) img3.getLayoutParams();
            threeParams.removeRule(RelativeLayout.ALIGN_PARENT_START);
            threeParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            threeParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
            threeParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            threeParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            threeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            img3.setLayoutParams(threeParams);

            RelativeLayout.LayoutParams fourParams = (RelativeLayout.LayoutParams) img4.getLayoutParams();
            fourParams.removeRule(RelativeLayout.ALIGN_PARENT_START);
            fourParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            fourParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
            fourParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            fourParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            fourParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            img4.setLayoutParams(fourParams);

            img2.setScaleX(1f);
            img2.setScaleY(1f);
            img4.setRotation(0);
        }
    }
}
