package com.ly.wechat.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ly.wechat.R;

/**
 * Description: 微信Item切换效果
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/14 on 23:51
 */
public class TabView extends FrameLayout {

    private ImageView ivIcon;//灰色
    private ImageView ivIconSelected;//绿色
    private TextView  tvTitle;

    private static final int COLOR_DEFAULT = Color.parseColor("#FF000000");//黑色
    private static final int COLOR_SELECT = Color.parseColor("#FF45C01A");//绿色

    public TabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.tab_view, this);

        ivIcon = findViewById(R.id.iv_icon_4_tab_view);
        ivIconSelected = findViewById(R.id.iv_icon_select_4_tab_view);
        tvTitle = findViewById(R.id.tv_title_4_tab_view);
        setProgress(0);//默认灰色
    }

    public void setProgress(@FloatRange(from = 0, to = 1) float progress) {
        ivIcon.setAlpha(1 - progress);
        ivIconSelected.setAlpha(progress);
        tvTitle.setTextColor(evaluate(progress, COLOR_DEFAULT, COLOR_SELECT));
    }

    //设置icon  text
    //方式一：抽取自定义属性，通过xml设置
    //方式二：直接对外开放方法设置
    public void setIconAndText(@DrawableRes int icon , @DrawableRes int iconSelect, String text){
        ivIcon.setImageResource(icon);
        ivIconSelected.setImageResource(iconSelect);
        tvTitle.setText(text);
    }

    /**
     * 评估, 估值器, 不同版本写法不一样
     * copy from {@link android.animation.ArgbEvaluator#evaluate(float, Object, Object)}
     * ObjectAnimator.ofArgb(tvTitle, "TextColor", startValue, endValue);
     */
    public int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }
}
