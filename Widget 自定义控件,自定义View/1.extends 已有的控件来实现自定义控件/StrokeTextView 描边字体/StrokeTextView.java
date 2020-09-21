package com.rm.lpsj.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;

import com.runman.baselibrary.R;

/**
 * 描边字体
 * Created by dj on 2018/2/5.
 */
public class StrokeTextView extends AppCompatTextView {

    //用于描边的TextView
    private AppCompatTextView borderText;

    public StrokeTextView(Context context) {
        super(context);
        borderText = new AppCompatTextView(context);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        borderText = new AppCompatTextView(context, attrs);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        borderText = new AppCompatTextView(context, attrs, defStyle);
        init();
    }

    public void init() {
        TextPaint textPaint = borderText.getPaint();
        //设置描边宽度
        textPaint.setStrokeWidth(4);
        //对文字只描边
        textPaint.setStyle(Paint.Style.STROKE);
        //设置描边颜色
        borderText.setTextColor(getResources().getColor(R.color.gray_333));
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        borderText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //先进构造方法, 再初始化属性
        borderText.setText(getText());
        borderText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        borderText.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        borderText.draw(canvas);
    }
}
