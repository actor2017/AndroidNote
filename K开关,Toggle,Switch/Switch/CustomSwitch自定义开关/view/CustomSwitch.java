package com.aswitch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aswitch.R;
import com.aswitch.utils.ToastUtils;

/**
 * Description: 自定义的开关,可以在xml中设置
 * 用法:
 * 1.在xml中设置本自定义开关需要把<switch或其它开关 改成:<com.aswitch.view.CustomSwitch
 * <p>
 * 2.如果要用本自定义控件中的属性,需要在xml中先声明命名空间:
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * <p>
 * 3.自定义属性如下:
 * 开关状态:        toggleState
 * 滑块图片:        slideSrc
 * 开关的背景图片:  slideBackground
 * <p>
 * 4.还有一种写法:初始化的时候不设置点击事件,而是在滑动的时候判断滑动是否>5像素,
 * 如果是滑动就写滑动逻辑,如果<5就写点击逻辑.(onTouchEvent返回:true)
 *
 * 5.注意优化写法:isOpen = !isOpen;       mSlideLeft = isOpen ? MAX_LEFT : 0;
 * <p>
 * 而本方法用的是设置一个boolean值记录是否是点击事件,感觉稍微麻烦.(点击事件只能返回:super)
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/2/18 on 14:50.
 * <p>
 * 方法执行顺序:onMeasure-->onLayout-->onDraw (测量控件宽高,设置控件位置,绘制控件内容)
 */

public class CustomSwitch extends View {

    //private Paint paint;        //画笔,用于刚开始测试画红色实心矩形,本例其实无用
    private Bitmap mSlideBg;     //背景图片
    private Bitmap mSlideButton;//滑块图片
    private float mSlideLeft;   //滑块的左边距
    private int MAX_LEFT;       //滑块的最大左边距
    private boolean isOpen;     //记录开关是否开启
    private float startX;       //按下的坐标
    private float endX;         //移动过程的坐标
    private boolean isClick;    //记录是否是点击
    private String NAME_SPACE = "http://schemas.android.com/apk/res-auto";//命名空间

    public CustomSwitch(Context context) {
        this(context, null);
    }

    public CustomSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.初始化布局,老师里面设置的有点击事件(其实可不写,祥见顶部第4点)
        //initView();

        //1.获取自定义属性(开关状态,滑块背景图片,滑块按钮)
        boolean toggleState = attrs.getAttributeBooleanValue(NAME_SPACE, "toggleState", false);
        int slideBackground = attrs.getAttributeResourceValue(NAME_SPACE, "slideBackground", -1);
        int slideSrc = attrs.getAttributeResourceValue(NAME_SPACE, "slideSrc", -1);

        //更新开关状态
        isOpen = toggleState;
        //设置背景图片
        if (slideBackground > 0) {
            mSlideBg = BitmapFactory.decodeResource(getResources(),slideBackground);
        } else {
            mSlideBg = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        }

        //设置滑块图片
        if (slideSrc > 0) {
            mSlideButton = BitmapFactory.decodeResource(getResources(),slideSrc);
        } else {
            mSlideButton = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        }

        //System.out.println("slideBackground:"+slideBackground+ "slideSrc:"+slideSrc);
        //如果设置了背景图片和滑块图片中一个或多个,且滑块的长度超过了背景图片的长度
        if ((slideBackground + slideSrc) > 0 && mSlideButton.getWidth() >= mSlideBg.getWidth()) {
            if (slideBackground > 0 && slideSrc > 0) {//说明都设置了
                ToastUtils.showDdfault(context,"滑块长度超过背景图片长度!");
            }
            if (slideBackground > 0 && slideSrc < 0) {
                ToastUtils.showDdfault(context,"背景图片过短!");
            }
            if (slideBackground < 0 && slideSrc > 0) {
                ToastUtils.showDdfault(context,"滑块长度超过默认的背景图片长度!");
            }

            if (slideBackground > 0) {//说明设置的背景图片
                mSlideBg = BitmapFactory.decodeResource(getResources(),R.drawable.switch_background);
                if (mSlideButton.getWidth() >= mSlideBg.getWidth()) {//设置的滑块宽度超过了默认的背景图片长度
                    mSlideButton = BitmapFactory.decodeResource(getResources(),R.drawable.slide_button);
                }
            } else {//说明没有设置背景图片,值设置的是滑块图片
                mSlideButton = BitmapFactory.decodeResource(getResources(),R.drawable.slide_button);
            }
        }
        //计算滑块最大左边距
        MAX_LEFT = mSlideBg.getWidth() - mSlideButton.getWidth();

        //设置点击事件
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    isOpen = !isOpen;
                    mSlideLeft = isOpen ? MAX_LEFT : 0;
//                    if (isOpen) {
//                        isOpen = false;
//                        mSlideLeft = 0;
//                    } else {
//                        isOpen = true;
//                        mSlideLeft = MAX_LEFT;
//                    }
                    //重绘控件, 让onDraw重新执行一次
                    invalidate();

                    //当开关状态发生变化时, 通过回调给前端页面通知当前的状态
                    if (mListener != null) {
                        mListener.onCheckChanged(CustomSwitch.this, isOpen);
                    }
                }
            }
        });

        //2.监听触摸状态,用来平滑的滑动滑块 快捷通道
        gotoTouchEvent();
        //3.onMeasure-->onLayout-->onDraw (测量控件宽高,设置控件位置,绘制控件内容) 快捷通道
        gotoMeasure();
        //4.开关状态的回调    快捷通道
        gotoOnCheckChangeListener();
    }

    //2.监听触摸状态,用来平滑的滑动滑块
    private void gotoTouchEvent() {
    }

    private float moveX;//记录移动总距离

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下的时候
                startX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:   //移动的时候
                endX = event.getX();
                float dX = endX - startX;
                moveX += Math.abs(dX);      //记录移动的总距离
                mSlideLeft += dX;           //获取滑动距离
                startX = endX;              //初始化开始位置

                if (mSlideLeft < 0) {
                    mSlideLeft = 0;
                }
                if (mSlideLeft > MAX_LEFT) {
                    mSlideLeft = MAX_LEFT;
                }
                invalidate();            //重绘滑块的位置

                /**
                 * 如果不像上面2个if这样写,而像下面这样写,会出现bug!
                 * bug:滑不到尽头,会留一点.因为稍微正常的滑动速度都可能造成mSlideLeft<0或>MAX_LEFT,
                 *      而这样就不会进方法里执行刷新的方法,现象:滑块貌似卡顿拖不动的现象
                 */
                //if (mSlideLeft > 0 && mSlideLeft < MAX_LEFT) {
                //    invalidate();
                //}
                break;
            case MotionEvent.ACTION_UP://抬起的时候

                //判断是点击还是手抖
                isClick = moveX > 5 ? false : true; //此处要容错5个像素, 避免手抖
                moveX = 0;                          //事件已经结束, movex归零

                if (!isClick) {                     //确定滑块最终状态
                    isOpen = mSlideLeft > MAX_LEFT / 2;
                    mSlideLeft = isOpen ? MAX_LEFT : 0;
//                    if (mSlideLeft > MAX_LEFT / 2) {
//                        isOpen = true;
//                        mSlideLeft = MAX_LEFT;
//                    } else {
//                        isOpen = false;
//                        mSlideLeft = 0;
//                    }
                    invalidate();

                    //当开关状态发生变化时, 通过回调给前端页面通知当前的状态
                    if (mListener != null) {
                        mListener.onCheckChanged(CustomSwitch.this, isOpen);
                    }
                }
                break;
        }
        /**
         * 事件返回值: 三种: true, false, super
         *
         * super.onTouchEvent底层代码对11133行的onClick事件进行了回调,让onTouch和onClick都响应到事件
         * return true:只响应滑动状态,不响应点击事件(会把点击当成滑动,不走setOnClickListener())
         * teturn false:滑动和点击都不响应
         */
        return super.onTouchEvent(event);
    }


    //1.初始化布局
    private void initView() {
        //初始化画笔,画笔只要一只就行,搞成全局变量
        //paint = new Paint();        //用于刚开始测试画红色实心矩形,本例其实无用
        //paint.setColor(Color.RED);  //用于刚开始测试画红色实心矩形,本例其实无用
    }

    //3.onMeasure-->onLayout-->onDraw (测量控件宽高,设置控件位置,绘制控件内容 快捷通道
    private void gotoMeasure() {
    }

    //测量控件的宽高,如果不重写,系统在xml中默认宽高=parent,因为它不确定,重写后可自定义宽,高
    //当然,也可在xml中写死宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//点进去,可查看系统怎么重写宽高的
        System.out.println("onMeasure");
        //根据图片大小来确定控件大小,要判断背景图片和滑块的高度的大小
        setMeasuredDimension(mSlideBg.getWidth(), mSlideBg.getHeight() >= mSlideButton.getHeight
                () ? mSlideBg.getHeight() : mSlideButton.getHeight());
    }

    //设置当前控件的位置,以后会用
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        System.out.println("onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    //onDraw:用于绘制控件的内容
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas); 这里面什么都没写,可删掉
        //canvas.drawRect(0,0,200,200,paint);   在左上角画一个200x200的红色实心矩形
        System.out.println("onDraw");

        //(@NonNull Bitmap bitmap, float left, float top, @Nullable Paint paint)画图片不需要画笔,null
        //绘制背景图片, 如果背景图片的高度小于滑块的高度,要另外设置,否则从左上角开始绘制
        if (mSlideBg.getHeight() < mSlideButton.getHeight()) {
            canvas.drawBitmap(mSlideBg, 0, (mSlideButton.getHeight() - mSlideBg.getHeight()) / 2,
                    null);
        } else {
            canvas.drawBitmap(mSlideBg, 0, 0, null);
        }
        //绘制滑块图片,如果背景图片的高度大于滑块的高度,要另外设置,否则从左上角开始绘制
        if (mSlideBg.getHeight() > mSlideButton.getHeight()) {
            canvas.drawBitmap(mSlideButton, mSlideLeft, (mSlideBg.getHeight() - mSlideButton
                    .getHeight()) / 2, null);
        } else {
            canvas.drawBitmap(mSlideButton, mSlideLeft, 0, null);
        }
    }

    //4.开关状态的回调    快捷通道
    private void gotoOnCheckChangeListener() {
    }

    /**
     * 回调的本质:对象引用的传递
     * <p>
     * 回调书写步骤:
     * 1.定义一个接口,里面写要回调的方法,参数
     * 2.暴露一个公共的方法,让调用者把接口的实现传递进来
     * 3.把传递进来的实现保存在本地,在适当的时候调用onCheckChanged(),把值回传回去
     */
    //把listener保存到本地的全局变量中
    private OnCheckChangeListener mListener;

    //设置回调监听
    public void setOnCheckChangeListener(OnCheckChangeListener listener) {
        mListener = listener;
    }

    //回调的接口
    public interface OnCheckChangeListener {
        void onCheckChanged(View view, boolean isOpen);
    }
}
