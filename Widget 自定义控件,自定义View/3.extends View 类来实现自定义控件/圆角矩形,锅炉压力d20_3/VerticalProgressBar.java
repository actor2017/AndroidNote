package com.kuchuanyun.cpptest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description: 垂直进度条
 * Copyright  : Copyright (c) 2017
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2017/8/9 on 22:48
 */

public class VerticalProgressBar extends View {

    private int progress = 0;
    private int max = 100;

    public VerticalProgressBar(Context context) {
        super(context);
    }

    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if (progress > 80) {
            paint.setColor(Color.RED);
        } else if (progress >= 50) {
            paint.setColor(Color.YELLOW);
        } else paint.setColor(Color.GREEN);
        int process = (int)(1.0 * progress / max * getHeight());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //rx, ry:圆角矩形两边的半径,示例:getWidth()/2
            canvas.drawRoundRect(0, getHeight() - process, getWidth(), getHeight(), 10, 10, paint);
        } else canvas.drawRect(0, getHeight() - process, getWidth(), getHeight(), paint);
    }

    //获取进度
    public int getProgress() {
        return progress;
    }

    //设置进度
    public void setProgress(int progress) {
        this.progress = progress;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate();
        } else postInvalidate();
    }

    //设置最大值
    public int getMax() {
        return max;
    }

    //设置最小值
    public void setMax(int max) {
        this.max = max;
    }
}
