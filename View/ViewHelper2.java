package com.itheima.mobileplayer.utils;

import android.view.View;

/**
 * Description: View操作帮助类,参考 nineoldandroids 的ViewHelper
 * TODO 还有一些可以封装,见View.java
 * <ol>
 *     <li>ViewHelper.setAlpha(View view, float alpha);//设置透明度[0, 1]</li><br/>
 *     <li>
 *         下面2个属性只是设置点,需要配合rotation,scale等才有效果(侧滑菜单 ——仿QQ实现动画效果)<br/>
 *         ViewHelper.setPivotX(View view, float pivotX);//view以pivotX处X轴为中心旋转/缩放<br/>
 *         ViewHelper.setPivotY(View view, float pivotY);//view以pivotY处Y轴为中心旋转/缩放
 *     </li><br/>
 *     <li>ViewHelper.setRotation(View view, float rotation);//以view中心,按Z轴旋转rotation°</li>
 *     <li>ViewHelper.setRotationX(View view, float rotationX);//以view高度/2的X轴为中心,旋转rotationX°</li>
 *     <li>ViewHelper.setRotationY(View view, float rotationY);//以view宽度/2的Y轴为中心,旋转rotationY°</li>
 *     <li>ViewHelper.setScaleX(View view, float scaleX);//以view宽度/2的Y轴为中心,缩放scaleX倍</li>
 *     <li>ViewHelper.setScaleY(View view, float scaleY);//以view高度/2的X轴为中心,缩放scaleY倍</li>
 *     <li>ViewHelper.setScrollX(View view, int scrollX);//view以X轴←滑动scrollX距离(背景不变)</li>
 *     <li>ViewHelper.setScrollY(View view, int scrollY);//view以Y轴↑滑动scrollY距离</li>
 *     <li>ViewHelper.setTranslationX(View view, float translationX);//view在X轴平移xx距离(view.setTranslationX(value);)</li>
 *     <li>ViewHelper.setTranslationY(View view, float translationY);//view在Y轴平移xx距离</li>
 *     <li>ViewHelper.setX(View view, float x);//设置view↖的X轴坐标</li>
 *     <li>ViewHelper.setY(View view, float y);//设置view↖的Y轴坐标</li>
 * </ol>
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2018/9/4 on 16:03
 */

public class ViewHelper2 {
    private ViewHelper2() {
        throw new RuntimeException("U can't new me!");
    }

    public static float getAlpha(View view) {
        return Honeycomb.getAlpha(view);
    }

    public static void setAlpha(View view, float alpha) {
        Honeycomb.setAlpha(view, alpha);
    }

    public static float getPivotX(View view) {
        return Honeycomb.getPivotX(view);
    }

    public static void setPivotX(View view, float pivotX) {
        Honeycomb.setPivotX(view, pivotX);
    }

    public static float getPivotY(View view) {
        return Honeycomb.getPivotY(view);
    }

    public static void setPivotY(View view, float pivotY) {
        Honeycomb.setPivotY(view, pivotY);
    }

    public static float getRotation(View view) {
        return Honeycomb.getRotation(view);
    }

    public static void setRotation(View view, float rotation) {
        Honeycomb.setRotation(view, rotation);
    }

    public static float getRotationX(View view) {
        return Honeycomb.getRotationX(view);
    }

    public static void setRotationX(View view, float rotationX) {
        Honeycomb.setRotationX(view, rotationX);
    }

    public static float getRotationY(View view) {
        return Honeycomb.getRotationY(view);
    }

    public static void setRotationY(View view, float rotationY) {
        Honeycomb.setRotationY(view, rotationY);
    }

    public static float getScaleX(View view) {
        return Honeycomb.getScaleX(view);
    }

    public static void setScaleX(View view, float scaleX) {
        Honeycomb.setScaleX(view, scaleX);
    }

    public static float getScaleY(View view) {
        return Honeycomb.getScaleY(view);
    }

    public static void setScaleY(View view, float scaleY) {
        Honeycomb.setScaleY(view, scaleY);
    }

    public static float getScrollX(View view) {
        return Honeycomb.getScrollX(view);
    }

    public static void setScrollX(View view, int scrollX) {
        Honeycomb.setScrollX(view, scrollX);
    }

    public static float getScrollY(View view) {
        return Honeycomb.getScrollY(view);
    }

    public static void setScrollY(View view, int scrollY) {
        Honeycomb.setScrollY(view, scrollY);
    }

    public static float getTranslationX(View view) {
        return Honeycomb.getTranslationX(view);
    }

    public static void setTranslationX(View view, float translationX) {
        Honeycomb.setTranslationX(view, translationX);
    }

    public static float getTranslationY(View view) {
        return Honeycomb.getTranslationY(view);
    }

    public static void setTranslationY(View view, float translationY) {
        Honeycomb.setTranslationY(view, translationY);
    }

    public static float getX(View view) {
        return Honeycomb.getX(view);
    }

    public static void setX(View view, float x) {
        Honeycomb.setX(view, x);
    }

    public static float getY(View view) {
        return Honeycomb.getY(view);
    }

    public static void setY(View view, float y) {
        Honeycomb.setY(view, y);
    }

    private static final class Honeycomb {//Honeycomb: 蜂巢，蜂巢状之物.API 11
        static float getAlpha(View view) {
            return view.getAlpha();
        }

        static void setAlpha(View view, float alpha) {
            view.setAlpha(alpha);
        }

        static float getPivotX(View view) {
            return view.getPivotX();
        }

        static void setPivotX(View view, float pivotX) {
            view.setPivotX(pivotX);
        }

        static float getPivotY(View view) {
            return view.getPivotY();
        }

        static void setPivotY(View view, float pivotY) {
            view.setPivotY(pivotY);
        }

        static float getRotation(View view) {
            return view.getRotation();
        }

        static void setRotation(View view, float rotation) {
            view.setRotation(rotation);
        }

        static float getRotationX(View view) {
            return view.getRotationX();
        }

        static void setRotationX(View view, float rotationX) {
            view.setRotationX(rotationX);
        }

        static float getRotationY(View view) {
            return view.getRotationY();
        }

        static void setRotationY(View view, float rotationY) {
            view.setRotationY(rotationY);
        }

        static float getScaleX(View view) {
            return view.getScaleX();
        }

        static void setScaleX(View view, float scaleX) {
            view.setScaleX(scaleX);
        }

        static float getScaleY(View view) {
            return view.getScaleY();
        }

        static void setScaleY(View view, float scaleY) {
            view.setScaleY(scaleY);
        }

        static float getScrollX(View view) {
            return view.getScrollX();
        }

        static void setScrollX(View view, int scrollX) {
            view.setScrollX(scrollX);
        }

        static float getScrollY(View view) {
            return view.getScrollY();
        }

        static void setScrollY(View view, int scrollY) {
            view.setScrollY(scrollY);
        }

        static float getTranslationX(View view) {
            return view.getTranslationX();
        }

        static void setTranslationX(View view, float translationX) {
            view.setTranslationX(translationX);
        }

        static float getTranslationY(View view) {
            return view.getTranslationY();
        }

        static void setTranslationY(View view, float translationY) {
            view.setTranslationY(translationY);
        }

        static float getX(View view) {
            return view.getX();
        }

        static void setX(View view, float x) {
            view.setX(x);
        }

        static float getY(View view) {
            return view.getY();
        }

        static void setY(View view, float y) {
            view.setY(y);
        }
    }
}
