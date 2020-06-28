package com.kuchuan.wisdompolicehy.global;

import android.view.ScaleGestureDetector;

import com.kuchuan.wisdompolicehy.activity.BaseActivity;

/**
 * Description: 类的描述
 * Copyright  : Copyright (c) 2017
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2017/11/24 on 14:08
 */

public class ScaleGestureDetectorTest extends BaseActivity {

    private ScaleGestureDetector scaleGestureDetector;
    private void test(){
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener(){

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                //处理缩放逻辑
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                //一定要返回true才会进入onScale()这个函数
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });

        float currentSpan = scaleGestureDetector.getCurrentSpan();//获取现在2点之间的距离(单位像素)
        float currentSpanX = scaleGestureDetector.getCurrentSpanX();//获取X轴距离(单位像素)
        float currentSpanY = scaleGestureDetector.getCurrentSpanY();//获取Y轴距离(单位像素)
        long eventTime = scaleGestureDetector.getEventTime();//返回事件被捕捉时的时间(单位ms)
        //返回当前手势焦点的 X 坐标。 如果手势正在进行中，焦点位于组成手势的两个触点之间。
        // 如果手势正在结束，焦点为仍留在屏幕上的触点的位置。若 isInProgress（）返回 false，该方法的返回值未定义。
        float focusX = scaleGestureDetector.getFocusX();
        float focusY = scaleGestureDetector.getFocusY();//意思同上
        float previousSpan = scaleGestureDetector.getPreviousSpan();//返回2点的前一次距离。
        float previousSpanX = scaleGestureDetector.getPreviousSpanX();//返回2点的前一次X距离.
        float previousSpanY = scaleGestureDetector.getPreviousSpanY();//返回2点的前一次Y距离.
        float scaleFactor = scaleGestureDetector.getScaleFactor();//返回前一个伸缩事件至当前伸缩事件的伸缩比率getCurrentSpan()/getPreviousSpan()
        long timeDelta = scaleGestureDetector.getTimeDelta();//返回前一次接收到的伸缩事件距当前伸缩事件的时间差,以毫秒为单位
        boolean inProgress = scaleGestureDetector.isInProgress();//如果手势处于进行过程中,返回true 否则返回false
    }
}
