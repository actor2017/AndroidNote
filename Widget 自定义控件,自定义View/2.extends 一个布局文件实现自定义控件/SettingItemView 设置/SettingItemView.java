package com.heima.mobilesafe_work.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heima.mobilesafe_work.R;

/**
 * Description: SettingActivity中元素的:自定义组合控件,在xml中写这个相对布局
 * 在layout中写一个例:setting_item.xml,填充到本RelativeLayout中
 * 在values写一个arrts.xml定义本RelativeLayout的自定义属性
 * 暴露setting_item.xml中setting_title,Background,toogleVisiable方法,供布局文件中设置自定义
 *
 * 在xml文件中用法:
 * 1.声明命名空间    xmlns:heima="http://schemas.android.com/apk/res-auto"
 * 2.像这样声明本RelativeLayout
 * <com.heima.mobilesafe_work.view.SettingItemView
 * android:id="@+id/iv_Setting_AutoUpdateToogle"
 * android:layout_width="match_parent"
 * android:layout_height="40dp"
 * android:layout_marginTop="10dp"      可写可不写,看效果
 * heima:Setting_Background="first"     "middle"    "last"
 * heima:ToogleVisiable="true"          默认显示开关的图片
 * heima:Setting_Title="骚扰拦截设置"/>
 *
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/1/13 on 19:34.
 */

public class SettingItemView extends RelativeLayout {

    private static final String NAME_SPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tv_settingTitle;
    private ImageView iv_toggleSwitch;

    public SettingItemView(Context context) {
        /**
         * 如果不传this,如果走这个方法,不能读取xml里面自定义属性的值★★★★★★★★★★★★★
         */
        this(context,null);
        //初始化布局 注意:要注销,否则图片无法切换!!!★★★★★★★★★★★★★★★
        //initView();
    }
    public SettingItemView(Context context, AttributeSet attrs) {
        /**
         * 如果不传this,如果走这个方法,不能读取xml里面自定义属性的值★★★★★★★★★★★★★
         * -1:随便传的值
         */
       this(context, attrs,-1);
        //初始化布局 注意:要注销,否则图片无法切换!!!★★★★★★★★★★★★★★★
        //initView();
    }
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 1.初始化布局
         */
        initView();

        /**
         * 2.★★★★★★初始化完布局后,读取xml里面自定义属性的值,然后设置到布局中★★★★★★
         *
         * AttributeSet:属性集合, xml布局文件中当前控件的所有属性都会加载在这个属性集合中
         * 读取自定义属性值
         * 读取标题属性
         */
        //读取自定义属性值
        String setting_title = attrs.getAttributeValue(NAME_SPACE, "Setting_Title");
        int Setting_Background = attrs.getAttributeIntValue(NAME_SPACE, "Setting_Background", 0);//0:属性里默认第一张图片
        boolean toogleVisiable = attrs.getAttributeBooleanValue(NAME_SPACE, "ToogleVisiable", true);//默认显示开关

        //把读取到的属性值配置到布局中
        tv_settingTitle.setText(setting_title);

        //把读取到的 Setting_Background 图片配置到布局中
        switch (Setting_Background) {
            case 0:
                setBackgroundResource(R.drawable.first_selector);
            break;
            case 1:
                setBackgroundResource(R.drawable.middle_selector);
            break;
            case 2:
                setBackgroundResource(R.drawable.last_selector);
            break;
        }

        //获取是否显示开关的boolean值:toogleVisiable
        iv_toggleSwitch.setVisibility(toogleVisiable? View.VISIBLE:View.INVISIBLE);//设置开关可见
    }
//=============================================================================
    private void initView() {
        /**
         * 给当前空的布局填充内容
         * 参3:A view group that will be the parent.
         * 传null表示当前布局没有父控件,大部分都传null
         * 传this表示已当前相对布局为这个布局的父控件,这样做了以后,当前空的布局就有内容了
         */
        //1.给SettingItemView(RelatuveLayout)填充布局
        View inflate = View.inflate(getContext(), R.layout.setting_item, this);
        //2.获取布局里面的控件,好暴露出来,让xml里面设置
        tv_settingTitle = (TextView) inflate.findViewById(R.id.tv_settingTitle);
        iv_toggleSwitch = (ImageView) inflate.findViewById(R.id.iv_toggle);
    }

    //供外界设置开关状态
    public void setToggleState(boolean toggleState){
        System.out.println("toggleState:" + toggleState);
        iv_toggleSwitch.setImageResource(toggleState? R.drawable.on : R.drawable.off);
    }
}
