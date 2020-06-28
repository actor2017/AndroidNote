package com.ly.wechat.widget.transformer;

import android.support.annotation.FloatRange;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Description: Google官方提供的两种动画效果的代码, 其中的一种, 和{@link ScaleAlphaTransformer}一样效果
 * 示例: viewPager.setPageTransformer(true, new ZoomOutPageTransformer(0.75F, 0.5F));
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/15 on 17:28
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static float MIN_SCALE;
    private static float MIN_ALPHA;

    public ZoomOutPageTransformer(@FloatRange(from = 0, to = 1) float minScale, @FloatRange(from = 0, to = 1) float minAlpha) {
        MIN_SCALE = minScale;
        MIN_ALPHA = minAlpha;
    }

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to
            // shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
