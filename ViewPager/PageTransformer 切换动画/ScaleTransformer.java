package com.ly.wechat.widget.transformer;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Description: ViewPager中可以缩放的Transformer, 示例:
 * viewPager.setPageTransformer(true, new ScaleTransformer(0.75F));
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/15 on 03:18
 */
public class ScaleTransformer implements ViewPager.PageTransformer {

    private static float MIN_SCALE;

    /**
     * 最小缩放比例, 比如 minScale=0.75, ==> 第一页从1变成0.75, 第二页从0.75变成1
     * @param minScale
     */
    public ScaleTransformer(@FloatRange(from = 0, to = 1) float minScale) {
        MIN_SCALE = minScale;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        //需在page中设置tag, 用于这儿打印日志
        //当从 a 滑到 b 页时, position的值:
        //a: 0 -> -1
        //b: 1 -> 0

        //当从 b 滑到 a 页时, position的值:
        //a: -1 -> 0
        //b: 0 -> 1
//        Log.e("ScaleTransformer", String.format("page.getTag()=%d, position=%f", page.getTag(), position));

        if (position < -1) {//中间页面(, -1)
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position < 0) {//左边页面[-1 , 0)
            //当从 a 滑到 b 页时, position的值:
            //a: 0 -> -1
            //把 0 到 -1  ==> 变成 1 到 0.75
            float scaleA2B = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
            page.setScaleX(scaleA2B);
            page.setScaleY(scaleA2B);

            //当从 b 滑到 a 页时, position的值:
            //a: -1 -> 0
            //把 -1 到 0 ==> 变成 0.75 到 1
//            float scaleB2A = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);//和上面代码一致
        } else if (position <= 1) {//右边页面[0 , 1]
            //当从 a 滑到 b 页时, position的值:
            //b: 1 -> 0
            //把 1 到 0 ==> 变成 0.75 到 1
            float scaleA2B = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
            page.setScaleX(scaleA2B);
            page.setScaleY(scaleA2B);

            //当从 b 滑到 a 页时, position的值:
            //b: 0 -> 1
            //把 0 到 1 ==> 变成 1 到 0.75
//            float scaleB2A = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);//和上面代码一致
        } else {//(1, )
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
