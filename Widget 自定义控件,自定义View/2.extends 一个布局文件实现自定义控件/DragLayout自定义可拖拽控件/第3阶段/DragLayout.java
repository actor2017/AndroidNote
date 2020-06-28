package com.heima.android50.widght;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.heima.android50.R;

/**
 * Description: 自定义可拖拽的自定义控件
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/4/11 on 9:12.
 */

public class DragLayout extends FrameLayout {

    private ViewDragHelper viewDragHelper;//注意这种写法
    private View fl_sec;
    private View fl_main;
    private int mainWidth;
    private int mainHeight;
    private int secWidth;
    private int secHeight;
    private int measuredWidth;
    private int measuredHeight;

    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            @Override
            //pointerId:在多点触控的时候标识触摸点的id
            public boolean tryCaptureView(View child, int pointerId) {
                return true;//表示child这个vidw对象是否可以被拖动
            }

            @Override
            //被拖动的水平距离(子view还没有发生位置的改变,return后才位置才发生改变)
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            //被拖动的垂直距离(子view还没有发生位置的改变,return后才位置才发生改变)
            public int clampViewPositionVertical(View child, int top, int dy) {
                return 0;//让子控件child不能垂直移动
            }

            @Override
            //控件被拖动,子View位置发生改变的时候,会进行回调(子控件,被拖动的水平距离,被拖动的垂直距离,x轴偏移量,y轴偏移量)
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                if (changedView == fl_sec) {
                    //           (childView左上角x坐标,childView左上角y坐标,childView宽度,childView高度)
                    fl_sec.layout(0,0,secHeight,secHeight);
                    int currentLeft = fl_main.getLeft();//获取main左边X坐标
                    left = currentLeft + dx < 0? 0:currentLeft + dx;
                    left = currentLeft + dx > measuredWidth * 0.6f+0.5? (int) (measuredWidth * 0.6f+0.5):currentLeft + dx;
                }else {
                    left = left < 0? 0:left;
                    left = left > measuredWidth * 0.6f+0.5? (int) (measuredWidth * 0.6f+0.5):left;
                }
                fl_main.layout(left,0,left + mainWidth,mainHeight);
            }

            //view被释放后(up事件)
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    //将 事件的 拦截 交给ViewDragHelper来处理
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }

    //将 事件的 响应 交给ViewDragHelper来处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    //当控件包括子控件填充完毕后,回调
    @Override
    protected void onFinishInflate() {
        fl_sec = findViewById(R.id.fl_sec);
        fl_main = findViewById(R.id.fl_main);
    }

    //onMeasure也可以获取控件宽高
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mainWidth = fl_main.getMeasuredWidth();
//    }

    //尺寸改变后回调,获取控件宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //super.onSizeChanged(w, h, oldw, oldh);
        mainWidth = fl_main.getMeasuredWidth();
        mainHeight = fl_main.getMeasuredHeight();
        secWidth = fl_sec.getMeasuredWidth();
        secHeight = fl_sec.getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
    }
}
