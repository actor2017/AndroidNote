package com.ly.changyi.utils;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/7/4.
 * @version 1.1 修改api28反射字段是: slidingTabIndicator
 */

public class TabLayoutUtil {
    /**
     * 设置页签的margin
     * @param tabs
     * @param leftDip marginLeft, 单位dp?
     * @param rightDip marginRight, 单位dp?
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            //通过反射得到tablayout的下划线的Field
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            try {
                //design包28.0.0 叫这个名字: slidingTabIndicator
                tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
            } catch (NoSuchFieldException ex) {
                ex.printStackTrace();
            }
        }
        if (tabStrip == null) return;
        try {
            tabStrip.setAccessible(true);
            //得到承载下划线的LinearLayout   //源码可以看到SlidingTabStrip继承得到承载下划线的LinearLayout
            LinearLayout llTab = (LinearLayout) tabStrip.get(tabs);

            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
            //循环设置下划线的左边距和右边距
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
