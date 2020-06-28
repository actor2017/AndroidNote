package com.itheima.mobileplayer.lyric;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.itheima.mobileplayer.R;

import java.io.File;
import java.util.List;

/**
 * Description:歌词控件
 * Copyright  : Copyright (c) 2016
 * Company    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/18 9:55
 */
public class LyricView extends TextView {

    private Paint           mPaint;
    private float           mNormalSize;
    private int             mNormalColor;
    private int             mHighLightColor;
    private float           mHighLightSize;
    private int             mHalfViewW;
    private int             mHalfViewH;
    private float           mLineHeight;//每一行的行高
    private List<LyricBean> mLyricList;
    private int             mCurrentLine;//现在是第多少行

    public LyricView(Context context) {
        super(context);
        init();
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHalfViewH = h / 2;
        mHalfViewW = w / 2;
    }

    private void init() {
        //高亮居中行
        mHighLightColor = getResources().getColor(R.color.green);
        mHighLightSize = getResources().getDimension(R.dimen.high_light_text);

        //普通行字体
        mNormalColor = getResources().getColor(R.color.gray_6fff);
        mNormalSize = getResources().getDimension(R.dimen.normal_text);

        //初始化画笔
        mPaint = new Paint();
        mPaint.setTextSize(mHighLightSize);
        mPaint.setColor(mHighLightColor);
        mPaint.setAntiAlias(true);

        //行高
        mLineHeight = getResources().getDimension(R.dimen.text_height);
        //模拟歌词
       /* mLyricList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mLyricList.add(new LyricBean(i * 2000, "歌词内容：" + i));
        }*/
        //居中行的行号
//        mCurrentLine = 80;
    }

    public void parseLyrics(File file){
        mLyricList = LyricLoader.loadLyricsFormFile(file);
        mCurrentLine = 0;
    }
    public void parseLyrics(String title){
        mLyricList = LyricLoader.loadLyricsFormFile(title);
        mCurrentLine = 0;
    }

    /**
     * 滚动歌词
     * @param currentTime
     *         当前正在播放的歌词的时间
     * @param totalTime
     *         总时间
     */
    public void roll(int currentTime, int totalTime) {
        for (int i = 0; i < mLyricList.size() - 1; i++) {
            int startPoint = mLyricList.get(i).startPoint;
            int nextPoint;
            if (i == mLyricList.size() - 1) {
                nextPoint = totalTime;
            } else {
                nextPoint = mLyricList.get(i + 1).startPoint;
            }
            if (currentTime >= startPoint && currentTime < nextPoint) {
                mCurrentLine = i;
                break;
            }
        }
        //在主线程中修改UI
        invalidate();
        //在子线程中修改UI
        //        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mLyricList == null || mLyricList.size() == 0){
            drawSingleText(canvas);
        } else {
            drawMutliText(canvas);
        }
    }

    /**
     * 绘制多行歌词
     */
    private void drawMutliText(Canvas canvas) {
        String text = mLyricList.get(mCurrentLine).content;
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);

        //获取字体宽高的一半
        int mHalfTextH = rect.height() / 2;
        //居中行X、Y坐标
        int centerY = mHalfViewH + mHalfTextH;

        for (int i = 0; i < mLyricList.size() - 1; i++) {
            if (i == mCurrentLine) {
                mPaint.setTextSize(mHighLightSize);
                mPaint.setColor(mHighLightColor);
            } else {
                mPaint.setTextSize(mNormalSize);
                mPaint.setColor(mNormalColor);
            }
            int drawY = (int) (centerY + (i - mCurrentLine) * mLineHeight);

            drawHorizontalText(canvas, mLyricList.get(i).content, drawY);
        }
    }

    /**
     * 绘制单行歌词
     */
    private void drawSingleText(Canvas canvas) {
        String text = "玩命加载歌词...";
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);

        //获取字体宽高的一半
        int mHalfTextH = rect.height() / 2;//获取text高度方法2
        //居中行X、Y坐标
        int drawY = mHalfViewH + mHalfTextH;

        drawHorizontalText(canvas, text, drawY);
    }

    /**
     * 绘制一行歌词
     */
    private void drawHorizontalText(Canvas canvas, String text, int drawY) {
        int mHalfTextW = (int) (mPaint.measureText(text, 0, text.length())) / 2;//获取text宽度方法1
        int drawX = mHalfViewW - mHalfTextW;
        canvas.drawText(text, drawX, drawY, mPaint);
    }
}
