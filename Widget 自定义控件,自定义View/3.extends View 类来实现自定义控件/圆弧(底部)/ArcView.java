package com.cmcc.yyn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cmcc.yyn.R;

/**
 * 弧形的view
 */
public class ArcView extends View {
    private int mWidth;
    private int mHeight;

    private int            mArcHeight; //圆弧的高度
    private int            mBgColor;   //背景颜色
    private int            lgColor;    //变化的最终颜色
    private Paint          mPaint;  //画笔
    private LinearGradient linearGradient;
    private Rect           rect = new Rect(0, 0, 0, 0);//普通的矩形
    private Path           path = new Path();//用来绘制曲面

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0);
        mBgColor = typedArray.getColor(R.styleable.ArcView_bgColor, context.getResources().getColor(R.color.colorPrimary));
//        lgColor = typedArray.getColor(R.styleable.ArcView_lgColor, mBgColor);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        linearGradient = new LinearGradient(0,0,getMeasuredWidth(),0,
//                mBgColor,lgColor, Shader.TileMode.CLAMP
//        );
//        mPaint.setShader(linearGradient);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置成填充
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBgColor);

        //绘制矩形
        rect.set(0, 0, mWidth, mHeight - mArcHeight);
        canvas.drawRect(rect, mPaint);

        //绘制路径
        path.moveTo(0, mHeight - mArcHeight);
        /**
         * 当我们不仅仅是画一条线甚至是画弧线时会形成平滑的曲线, 该曲线又称为"贝塞尔曲线"(Bezier curve)
         * x1，y1为控制点的坐标值，x2，y2为终点的坐标值
         */
        path.quadTo(mWidth >> 1, mHeight, mWidth, mHeight - mArcHeight);
        canvas.drawPath(path, mPaint);
    }
}
