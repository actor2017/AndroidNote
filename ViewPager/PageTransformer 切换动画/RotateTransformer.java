package com.ly.wechat.widget.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Description: ViewPager中 旋转 的Transformer, 示例:
 * viewPager.setPageTransformer(true, new RotateTransformer(25));
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/15 on 17:53
 */
public class RotateTransformer implements ViewPager.PageTransformer {

    private static float MAX_ROTATE_ANGLE;//最大旋转角度

    public RotateTransformer(int maxRotateAngle) {
        MAX_ROTATE_ANGLE = maxRotateAngle;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {//中间页面(, -1)
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight());
            page.setRotation(-MAX_ROTATE_ANGLE);
        } else if (position < 0) {//左边页面[-1 , 0)
            //当从 a 滑到 b 页时, position的值:
            //a: 0 -> -1
            //把 0 到 -1  ==> 变成 0.5宽度 到 1宽度
            page.setPivotX(page.getWidth() * 0.5F + page.getWidth() * 0.5F * (- position));
            page.setPivotY(page.getHeight());
            //旋转角度 0 到 -max
            float rotate = MAX_ROTATE_ANGLE * position;
            page.setRotation(rotate);
        } else if (position <= 1) {//右边页面[0 , 1]
            //当从 a 滑到 b 页时, position的值:
            //b: 1 -> 0
            //把 1 到 0 ==> 变成 1宽度 到 0.5宽度
            page.setPivotX(page.getWidth() * 0.5F * (1 - position));
            page.setPivotY(page.getHeight());
            //旋转角度 +max 到 0
            float rotate = MAX_ROTATE_ANGLE * position;
            page.setRotation(rotate);
        } else {//(1, )
            page.setPivotX(0);
            page.setPivotY(page.getHeight());
            page.setRotation(MAX_ROTATE_ANGLE);
        }
    }
}
