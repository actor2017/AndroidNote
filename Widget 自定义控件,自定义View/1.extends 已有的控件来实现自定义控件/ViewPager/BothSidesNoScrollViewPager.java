package com.kuchuan.wisdompolice.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description: 这是一个第一页/最后一页pager才能往左/右划的viewpager,应用于:
 *              如果是第0个/最后一个pager,父类拦截,能左/右滑,否则请求不拦截
 *
 * 布局文件中示例用法:
 * <com.wisdomxian.widget.BothSidesNoScrollViewPager
 *      android:id="@+id/viewPager"
 *      android:layout_width="match_parent"
 *      android:layout_height="150dp">
 * </com.wisdomxian.widget.BothSidesNoScrollViewPager>

 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/2 on 12:15.
 *
 * @see #dispatchTouchEvent(MotionEvent)
 */

public class BothSidesNoScrollViewPager extends ViewPager {
    public BothSidesNoScrollViewPager(Context context) {
        super(context);
    }

    public BothSidesNoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写父类的方法
    //0-->down
    //2-->move
    //1-->up
    //3-->ACTION_CANCEL
    //事件序列：down开始-->一系列的move事件-->以up事件结束     正常的事件序列
    //任何一个事件都是从父控件传递给子控件
    //如果一个控件接收到了down事件，还接收到了一些move事件，但是在某一个move事件的时候，
    // 突然，被父控件拦截，会造成这个控件不得善终，此时会产生一个新的事件叫做cancel

    //* 事件的分发  dispatchTouchEvent
    //* 事件的拦截 onInterceptTouchEvent
    //* 事件的响应 onTouchEvent
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //请求父控件、祖宗控件不拦截事件
            //所谓请求父控件别拦截事件，意思，请求父控件别拦截接下来的move事件
            //对于当前的事件，子控件是无法控制父控件是否拦截

            //判断一下当前是否是第0个/最后一个item
            if (getCurrentItem() == 0 || getCurrentItem() == getAdapter().getCount() - 1) {
                //父控件.请求不允许拦截触摸事件 = false   即:拦截
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                //父控件.请求不允许拦截触摸事件 = true   即:不拦截
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
