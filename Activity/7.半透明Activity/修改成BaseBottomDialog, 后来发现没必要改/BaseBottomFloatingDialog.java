package com.actor.myandroidframework.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;

/**
 * description: 输入框和布局悬浮在 "输入法" 之上的Dialog, 可用于输入评论.
 * author     : 李大发
 * date       : 2020/4/13 on 16:13
 *
 * @version 1.0
 */
public abstract class BaseBottomFloatingDialog extends BaseBottomDialog {

//    protected View space;
//    protected Activity activity;
//    protected int preSoftInputMode;//之前的mode
//    protected static final String KEYBOARD_HEIGHT = "KEYBOARD_HEIGHT_FOR_ACTOR_APPLICATION";

    public BaseBottomFloatingDialog(@NonNull Activity context) {
        super(context);
//        this.activity = context;
    }

    public BaseBottomFloatingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseBottomFloatingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

//    @Override
//    public void setContentView(int layoutResID) {
//        View child = View.inflate(getContext(), layoutResID, null);
//        ViewGroup parent = (ViewGroup) View.inflate(getContext(), R.layout.dialog_base_bottom_floating, null);
//        parent.addView(child, 0);
//        super.setContentView(parent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        space = findViewById(R.id.space_for_base_bottom_floating_dialog);

        //设置space的高度 = 键盘高度
//        ViewGroup.LayoutParams layoutParams = space.getLayoutParams();
//        layoutParams.height = SPUtils.getInt(KEYBOARD_HEIGHT, 831);
//        space.setLayoutParams(layoutParams);
    }

    protected KeyboardUtils.OnSoftInputChangedListener listener = height -> {
//        SPUtils.putInt(KEYBOARD_HEIGHT, height);
//        if (space != null) {
//            //设置space的高度 = 键盘高度
//            ViewGroup.LayoutParams layoutParams = space.getLayoutParams();
//            layoutParams.height = height/*SPUtils.getInt(KEYBOARD_HEIGHT, 831)*/;
//            space.setLayoutParams(layoutParams);
//        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        KeyboardUtils.registerSoftInputChangedListener(activity, listener);
        //stateAlwaysVisible|adjustResize
//        preSoftInputMode = activity.getWindow().getAttributes().softInputMode;
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE & WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onDetachedFromWindow() {
//        KeyboardUtils.unregisterSoftInputChangedListener(activity.getWindow());
//        activity.getWindow().setSoftInputMode(preSoftInputMode);
        super.onDetachedFromWindow();
    }
}
