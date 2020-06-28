package com.kuchuan.wisdompolicehy.global;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Description: 还未学完
 * Copyright  : Copyright (c) 2017
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2017/11/24 on 14:08
 */

public class Main extends RelativeLayout {

    private ViewDragHelper viewDragHelper;

    public Main(Context context) {
        this(context,null);
    }

    public Main(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public Main(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //1.为this，表示该类生成的对象，他是ViewDragHelper的拖动处理对象，必须为ViewGroup。
        //2.敏感度,1.0f是正常的,参数参数越大越敏感。
        //3.自定义Callback extends Callback
        //public static ViewDragHelper create(ViewGroup forParent, float sensitivity, Callback cb)
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());

        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);//可以滑动到左边缘(上下左右)
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            viewDragHelper.cancel();
            return false;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
//        return super.onInterceptTouchEvent(ev);
    }

    //把事件传递给ViewDragHelper
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        viewDragHelper.processTouchEvent(ev);
        return true;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        /**
         * 这个方法是abstract,必须要重写
         * @return 判断哪个子view能否移动.
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;//return child == mDragView1;
//            return false;
        }

        /**
         * 横向拖动,如果不重写,默认返回的是0
         * @param child
         * @param left 当前拖动子view应该到达的x坐标
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int leftBound = getPaddingLeft();
            int rightBound = getWidth() - child.getWidth();//减去被拖动view的宽度
            int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
//            return super.clampViewPositionHorizontal(child, left, dx);
        }

        /**
         * 纵向拖动,如果不重写,默认返回的是0
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int topBound = getPaddingTop();
            int bottomBound = getHeight() - child.getHeight();//减去被拖动view的高度
            int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
//            return super.clampViewPositionVertical(child, top, dy);
        }

        /**
         * 当设置了mDragHelper.setEdgeTrackingEnabled(orientation)之后,这个方法回调
         * 这种情况下一般都是没有和子view接触的情况。
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        /**
         * 如果你想在边缘滑动的时候根据滑动距离移动一个子view，可以通过实现onEdgeDragStarted方法，
         * 并在onEdgeDragStarted方法中手动指定要移动的子View
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            viewDragHelper.captureChildView(mDragView2,pointerId);
        }
    }
}
