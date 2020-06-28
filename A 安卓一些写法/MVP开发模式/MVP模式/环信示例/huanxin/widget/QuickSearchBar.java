package com.shijing.huanxin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shijing.huanxin.R;
import com.shijing.huanxin.utils.UiUtils;

/**
 * Description: 快速查找条
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/22 on 20:55.
 *
 * 用法:
 * 1. 清单文件中示例定义
 * <com.shijing.huanxin.widget.QuickSearchBar
 * android:id="@+id/qsb_quicksearchbar"
 * android:layout_width="30dp"
 * android:layout_height="match_parent"
 * android:layout_alignParentRight="true"
 * android:layout_below="@id/layout_titlebar"/>
 *
 * 2. 2秒时间到了之后,letter = ""
 * 所以设置监听要判断:
 * if (letter != "") {
 *      tv_tips.setVisibility(View.VISIBLE);
 *      tv_tips.setText(letter);
 *          }else {
 *      tv_tips.setVisibility(View.GONE);
 * }
 *
 * 3.可以设置时间......等
 * qsb_quicksearchbar = (QuickSearchBar) view.findViewById(R.id.qsb_quicksearchbar);
 * qsb_quicksearchbar.setLetterShowTime(float ms);
 * qsb_quicksearchbar.setTextSize(float textSize);
 * qsb_quicksearchbar.setTextColo(String color);
 */

public class QuickSearchBar extends View {

    private static final String[] SECTIONS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Rect rect;
    private Paint paint;
    private float mCellWidth;   //控件宽度
    private float mCellHeight;  //控件高度/26
    private int mCurrentIndex = -1;  //现在所触摸的索引

    public QuickSearchBar(Context context) {
        this(context,null);
    }

    public QuickSearchBar(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public QuickSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        paint.setColor(Color.parseColor("#8c8c8c"));
        paint.setAntiAlias(true);       //抗锯齿
        paint.setTextSize(UiUtils.sp2px(getContext(),10));
        rect = new Rect();              //矩形
    }

    //注意这种写法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCellWidth = getMeasuredWidth();    //控件宽度
        mCellHeight = getMeasuredHeight()*1.0f/SECTIONS.length;//控件高度/26
    }

    @Override
    protected void onDraw(Canvas canvas) {//canvas帆布
        super.onDraw(canvas);
//        canvas.drawBitmap();
        for (int i = 0; i < SECTIONS.length; i++) {
            //绘制图片:起点在左上角
            //绘制文字:起点在左下角
            paint.getTextBounds(SECTIONS[i],0,1,rect);
            int textWidth = rect.width();   //字体宽度
            int textHeight = rect.height(); //字体高度
            float x = mCellWidth/2 - textWidth/2;//字体从小rect的左下角开始画
            float y = mCellHeight / 2 + textHeight / 2 + mCellHeight * i;//字体从小rext的做小脚开始画
            if (i == mCurrentIndex) {
                paint.setColor(Color.BLUE);
            }else {
                paint.setColor(Color.parseColor("#8c8c8c"));
            }
            canvas.drawText(SECTIONS[i],x,y,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_MOVE:
            float y = event.getY();     //相对与这个控件左上角的y坐标
            int oldIndex = mCurrentIndex;
            mCurrentIndex = (int) (y / mCellHeight);
            if (mCurrentIndex > SECTIONS.length - 1) {
                mCurrentIndex = SECTIONS.length - 1;
            }
            if (mCurrentIndex < 0) {
                mCurrentIndex = 0;
            }
            if (oldIndex != mCurrentIndex) {
                notifyLetterChanged(SECTIONS[mCurrentIndex]);
                invalidate();//请求重新绘制，此方法会促发系统再次调用onDraw的方法
            }
            setBackgroundResource(R.drawable.shape_quicksearchbar_bg);//当选中右侧条目时,背景色为#50000000的圆角背景
            break;

        case MotionEvent.ACTION_UP:
        default:
            mCurrentIndex = -1;
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0,2000);
            setBackgroundColor(Color.TRANSPARENT);//设置透明的颜色背景
            invalidate();
            //setBackgroundDrawable(new ColorDrawable());//这个也可以设置透明
            break;
        }
        return true;
    }

    public interface OnLetterChangedListener {
        void onLetterChangedthrow(String letter);
    }

    private OnLetterChangedListener letterChangedListener;
    public void setOnLetterChangedListener(OnLetterChangedListener letterChangedListener){
        this.letterChangedListener = letterChangedListener;
    }

    /**
     * 2秒时间到了之后,letter = ""
     * 所以设置监听要判断:
     * if (letter != "") {
     *      tv_tips.setVisibility(View.VISIBLE);
     *      tv_tips.setText(letter);
     *          }else {
     *      tv_tips.setVisibility(View.GONE);
     * }
     */
    private void notifyLetterChanged(String letter){
        if (letterChangedListener != null) {
            letterChangedListener.onLetterChangedthrow(letter);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        getMeasuredWidth();
//        getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        getMeasuredWidth();
//        getMeasuredHeight();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandler.removeMessages(0);
            notifyLetterChanged("");
        }
    };

    //measure--layout--deaw
    public void setTextColo(String color){

    }

    public void setTextSize(float textSize){

    }

    /**
     * 设置字母显示时间
     */
    public void setLetterShowTime(float ms){

    }
}
