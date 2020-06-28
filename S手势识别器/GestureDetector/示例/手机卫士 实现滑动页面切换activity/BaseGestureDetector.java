package com.heima.mobilesafe_work.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.heima.mobilesafe_work.utils.ToastUtils;

/**
 * GestureDetector(接口): 手势识别器,识别手势,用于屏幕滑动
 * GestureDetector的实现类:SimpleOnGestureListener()
 * 使用说明:
 * 1.先继承本类,只重写本类的2个抽象方法
 * <p>
 * 2.布局文件上/下一页按钮的命名分别为:android:onClick="btn_pre",
 * android:onClick="btn_next"
 * <p>
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/1/15 on 20:13.
 */

public abstract class BaseGestureDetector extends AppCompatActivity {

    //把手势识别器声明成全局变量
    private GestureDetector gestureDetector;

    @Override
    //1.先重写onCreate方法,不用写布局
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //2.创建手势识别器,注意自己写{}
        /**
         * 3.重写onFling方法
         * e1:起点
         * e2:终点
         * velocityX:X轴速度
         * velocityY:Y轴速度
         */
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            
            /**
             * 3.重写onFling方法
             * e1:起点
             * e2:终点
             * velocityX:X轴速度
             * velocityY:Y轴速度
			 * onFling:飞,抛,快速滑动
             */
			 @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //避免斜着划,竖着划
                if (Math.abs(e1.getY() - e2.getY()) > 100) {
                    ToastUtils.show(getApplicationContext(), "手机不用时,请不要\"竖\"着放");
                    return true;
                }
                //避免划得太慢
                if (Math.abs(velocityX) < 150) {
                    ToastUtils.show(getApplicationContext(), "划这么慢,不给切换!");
                    return true;
                }
                //向右滑动
                if (e2.getX() - e1.getX() > 100) {
                    ToastUtils.show(getApplicationContext(), "向右划了哦!");
                    goToPre();
                }
                //向左滑动
                if (e1.getX() - e2.getX() > 100) {
                    ToastUtils.show(getApplicationContext(), "向左划了哦!");
                    goToNext();

                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * 重写onTouchEvent方法
     * 监听当前activity的触摸事件
     * 过滤出左右滑动的事件-->手势识别器
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件交给识别器来分析, 并过滤出我们想要的事件
        gestureDetector.onTouchEvent(event);

        //示例写法:
/*        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("按下");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("移动");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("抬起");
                break;
            default:
                break;
        }*/
        return super.onTouchEvent(event);

    }

    /**
     * 翻上一页的抽象方法,强制子类重写如下:
     * startActivity(new Intent(this, Setup3Activity改.class));
     * finish();
     * overridePendingTransition(R.anim.anim_pre_enter, R.anim.anim_pre_exit);
     */
    public abstract void goToPre();

    /**
     * 翻下一页的抽象方法,强制子类重写如下:
     * startActivity(new Intent(this, Setup5Activity改.class));
     * finish();
     * overridePendingTransition(R.anim.anim_next_enter, R.anim.anim_next_exit);
     */
    public abstract void goToNext();

    /**
     * 下一页按钮的监听
     * 下一页的按钮id命名:android:onClick="btn_pre"  否则找不到这个方法.也要调用翻下一页方法
     */
    public void btn_next(View view) {
        goToNext();
    }

    /**
     * 上一页按钮的监听
     * 上一页的按钮id命名:android:onClick="btn_next"    否则找不到这个方法.也要调用翻上一页的方法
     */
    public void btn_pre(View view) {
        goToPre();
    }
}
