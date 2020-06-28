package com.actor.chatlayout;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Description: 计算TextView的高度
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技 www.kuchuanyun.com
 * Author     : 李小松
 * Date       : 2017/6/29 on 18:32.
 */
public class TextViewUtils {

    /**
     * 打造一个TextView,这个TextView和我们自己的textview完全一致,然后给这个TextView设置maxLines
     * 然后返回的高度就 = 我们控件的高度
     * 获取高度,如果maxLine没设置,可以随意传,例100
     * @return TextView的高度
     */
    public static int getHeight(Context context,Integer maxLines, String text, int textSize, boolean boldAble, int paddingLeft, int paddingRight) {
        TextView tv = new TextView(UiUtils.getContext());
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        tv.getPaint().setFakeBoldText(boldAble);//设置粗体
        if (maxLines != null && maxLines > 0) tv.setMaxLines(maxLines);
        int size = UiUtils.getScreenWidth(context)  - UiUtils.dp2px(context,paddingLeft) - UiUtils.dp2px(context,paddingRight);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST);
        tv.measure(widthMeasureSpec,0);
        return tv.getMeasuredHeight();
    }
	
    //获取字体高度(比如用于根据字体宽高,确定表情Emoji的宽高)
    public static int getFontHeight(TextView textView) {
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.bottom - fm.top);//返回大于等于参数a的最小整数,即对浮点数向上取整
    }
	
	//未测试
	public static int getFontHeight2(TextView textView) {
		Paint mPaint = new Paint();
        mPaint.setTextSize(textView.getTextSize());
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
		//float width = mPaint.measureText(textView.getText(), 0, textView.length());
		return rect.height();
	}
}