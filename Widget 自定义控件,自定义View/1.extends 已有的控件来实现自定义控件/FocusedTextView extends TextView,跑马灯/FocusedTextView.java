package com.huntersun.vky.etcopencard.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Description: 让TextView获取焦点,能实现多行同时 跑马灯,滚动,自定义永远有焦点的TextView
 * 示例写法:
 * <com.huntersun.vky.etcopencard.widget.FocusedTextView
 *    android:layout_width="wrap_content"
 *    android:layout_height="wrap_content"
 *    android:textColor="@color/white"
 *    tools:text="测试测试测试测试测试测试测试测试测试测试测试测试测试测试" />
 *
 * Author     : 李大发
 * Date       : 2017/1/12 on 20:10
 * @version 1.0
 */
public class FocusedTextView extends AppCompatTextView {

    /**
     * 当开发者创建一个对象时, 走此构造方法
     */
    public FocusedTextView(Context context) {
        super(context);
        initTextView();
    }

    //
    /**
     * AttributeSet: 属性集合
     * 当布局文件中配有属性时, 走此构造方法, 由系统底层调用
     */
    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTextView();
    }

    //
    /**
     * defStyleAttr: 样式 style
     * 当布局文件中配有样式时, 走此构造方法, 由系统底层调用
     */
    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTextView();
    }

    /**
     * 强制返回true, 让此TextView永远有焦点, 解决了多个跑马灯,只有一个跑起来的bug
     * @return
     */
    @Override
    public boolean isFocused() {
        //return super.isFocused();
        return true;
    }

    /**
     * 从activity_home.xml的TextView中复制过来的4个固定属性
     * 宽,高,text,textColor,textSize等没有复制过来,需自定义
     *
     * android:ellipsize="marquee"          :跑马灯
     * android:focusable="true"             :有焦点的
     * android:focusableInTouchMode="true"  :可以触摸
     * android:singleLine="true" :设置只有一行.xml中lines="1"和maxLines="1"写了都不跑马灯
     *
     * 依照上面复制过来的4条属性,写初始化TextView的方法,
     * 让TextView永远获取焦点,可以跑马灯
     */
    private void initTextView(){
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setSingleLine(true);
        setMarqueeRepeatLimit(-1);
    }

    /**
     * @param marqueeLimit 设置跑马灯重复次数, -1无限
     */
    @Override
    public void setMarqueeRepeatLimit(int marqueeLimit) {
        super.setMarqueeRepeatLimit(marqueeLimit);
    }
}
