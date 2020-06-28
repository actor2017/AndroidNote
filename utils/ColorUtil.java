package com.shijing.huanxin.utils;

/**
 * Description: 本工具还没实现,没时间
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/21 on 19:25.
 */

import android.content.Context;
import android.graphics.Color;

import java.util.regex.Pattern;


public class ColorUtil {

    /**
     * 获取资源中的颜色
     *
     * @param color
     * @return
     */
    public static int getResourcesColor(Context context, int color) {

        int ret = 0x00ffffff;
        try {
            ret = context.getResources().getColor(color);
        } catch (Exception e) {
        }

        return ret;
    }

    /**
     * 将十六进制 颜色代码 转换为 int
     *
     * @return
     */
    public static int HextoColor(String color) {

        // #ff00CCFF
        String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#00ffffff";
        }

        return Color.parseColor(color);
    }

    /**
     * 修改颜色透明度
     *
     * @param color
     * @param alpha
     * @return
     */
    public static int changeAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }

    public void setAlpha(float toAlpha) {
//提示：注释的那一行也可以实现一样的效果。
        //setBackgroundColor(ColorUtil.changeAlpha(ColorUtil.HextoColor("#ff0066ff"), (int)
        // (toAlpha * 0xff)));
        //setBackgroundColor(ColorUtil.changeAlpha(ColorUtil.getResourcesColor(R.color.topbar_bg)
        // , (int)(toAlpha * 0xff)));
    }

}