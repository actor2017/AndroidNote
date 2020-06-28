package com.heima.youku.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Description: 用于优酷,菜单动画,的旋转工具
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/2/16 on 19:24.
 */

public class YouKuRotateAnimUtils {
    //重载: 当需要给原来的方法加参数, 但又不想影响以前的代码时, 可以重载该方法
    public static void show(View view) {//ViewGroup
        show(view, 0);
    }

    public static void show(View view, long delay) {//ViewGroup
        //旋转动画, 围绕底边中心点旋转
        RotateAnimation anim = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);

        anim.setDuration(500);

        anim.setFillAfter(true);//动画结束后保持原位

        anim.setStartOffset(delay);//延时多长时间之后再启动动画

        view.startAnimation(anim);
    }

    //重载: 当需要给原来的方法加参数, 但又不想影响以前的代码时, 可以重载该方法
    public static void hide(View view) {//ViewGroup
        hide(view, 0);
    }

    //隐藏动画
    public static void hide(View view, long delay) {//ViewGroup
        //旋转动画, 围绕底边中心点旋转
        RotateAnimation anim = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1);

        anim.setDuration(500);

        anim.setFillAfter(true);//动画结束后保持原位

        anim.setStartOffset(delay);//延时多长时间之后再启动动画

        view.startAnimation(anim);
    }
}
