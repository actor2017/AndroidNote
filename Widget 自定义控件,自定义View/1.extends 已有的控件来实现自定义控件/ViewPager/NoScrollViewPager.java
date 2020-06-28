package com.heima.wisdomxian.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 本类是一个不能左右滑动ViewPager
 *
 * 布局文件示例用法:
 * <!--下面是一个自定义ViewPager,只是继承了一个第0页面才能往左划得viewpager
 * 详情在文件夹ViewPager中去找-->
 * <!--NewsMenuViewPager:如果是第0个pager,父类拦截,能左滑,否则请求不拦截,共12个pager
 * ,每一个pager里面装的是ListView,而ListView添加了头布局ViewPager()-->
 * <com.heima.wisdomxian.widget.NewsMenuViewPager
 *      android:id="@+id/vp_viewpager"
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"
 *      android:layout_below="@id/rl">
 * </com.heima.wisdomxian.widget.NewsMenuViewPager>
 *
 * 如何让Viewpager不能左右滑动
 * 1、给ViewPager设置一个触摸监听
 *      viewPager.setOnTouchListener(new View.OnTouchListener() {
 *          @Override
 *          public boolean onTouch(View v, MotionEvent event) {
 *              return true;
 *          }
 *      });
 *
 * 2.就像本例写的方法,如下:
 * viewPager为什么能够左右滑动，那是因为ViewPager重写了onTouchEvent，在onTouchEvent做的逻辑
 * 让ViewPager不要调用到onTouchEvent就可以了
 * 事件的分发  dispatchTouchEvent
 * 事件的拦截 onInterceptTouchEvent
 * 事件的响应 onTouchEvent
 * 一个事件如果到达了一个view上，最先最先调用的会是这个View中的dispatchTouchEvent
 * Date       : 2017/2/22 on 13:50.
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

	/**
     * 如果不重写此方法，子控件如果有触摸事件(例:有一个ViewPager)，
     * 在子类左滑到不能滑动后，return super.onInterceptTouchEvent(ev)
     * 中会有逻辑，父控件会响应侧滑事件(父控件能一点一点往左滑)，
     * 所以此处需要注掉return super.onInterceptTouchEvent(ev)，直接返回false
     * @param ev
     * @return
     */
    @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
        //  return super.onInterceptTouchEvent(ev);
            return false;
        //return true;      //拦截的意思,子类不能获取响应触摸事件
        }


    //在onTouchEvent中不做任何事情，ViewPager就不能左右滑动(详情查看源码)
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return super.onTouchEvent(ev);
        return true;
    }
}
