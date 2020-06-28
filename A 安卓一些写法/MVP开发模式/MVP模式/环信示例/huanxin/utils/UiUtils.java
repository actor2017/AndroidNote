package com.shijing.huanxin.utils;

import android.content.Context;

/**
 * Created by zhengping on 2017/2/28,15:37.
 * 1.dp 和 像素 的互转    dp2px(用的多)  px2dp
 * 2.获取屏幕宽度/高度getScreenWidth
 */

public class UiUtils {

    /**
     * dp 转换为 像素,传入"dp",输出"像素",java代码中一般用这种
     */
    public static int dp2px(Context context,int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);//四舍五入
        //   3.1   --> 3
        //  3.7   --> 3
        //3.6-->3
        //4.2-->4
    }

    /**
     * 像素 转换为 dp,传入"像素",输出"dp"
     */
    public static int px2dp(Context context,int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px/density);
    }

    //字体的sp 转换为 sp
    public static int sp2px(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().scaledDensity;
        return (int)(var1 * var2 + 0.5F);
    }

    //px 转换为 sp
    public static int px2sp(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().scaledDensity;
        return (int)(var1 / var2 + 0.5F);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreemHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
