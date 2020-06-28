package com.ly.hihifriend.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.Animatable2Compat;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Description: 加载GIF图片, 播放循环次数后, 清空图片
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/24 on 21:05
 *
 * 示例使用:
 * private MyImageViewTarget simpleTarget;
 * if (simpleTarget == null) simpleTarget = new MyImageViewTarget(ivGiftShow, 2);
 * Glide.with(this).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).load(url).into(simpleTarget);
 *
 * @version 1.0
 */
public class MyImageViewTarget extends ImageViewTarget<GifDrawable> {

    private int loopCount;

    /**
     * @param imageView 图片
     * @param loopCount 循环次数
     */
    public MyImageViewTarget(ImageView imageView, @IntRange(from = 1) int loopCount) {
        super(imageView);
        this.loopCount = loopCount;
    }

    //当前方法表示图片资源加载完成
    @Override
    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
        ImageView imageView = getView();
        if (imageView != null) {
            imageView.setWillNotDraw(false);//设置图片
            resource.setLoopCount(loopCount);//循环播放次数
            imageView.setImageDrawable(resource);
            resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    super.onAnimationEnd(drawable);
                    ImageView imageView = getView();
                    if (imageView != null) imageView.setWillNotDraw(true);//清空图片
                }
            });
            resource.start();
        }
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {//开始加载图片
        super.onLoadStarted(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {//加载失败
        super.onLoadFailed(errorDrawable);
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {
        super.onLoadCleared(placeholder);
    }

    @Nullable
    @Override
    public Drawable getCurrentDrawable() {//获取当前显示的 Drawable
        return super.getCurrentDrawable();
    }

    @Override
    public void onStart() {//Activity/Fragment 中的onStart
        super.onStart();
    }

    @Override
    public void onStop() {//Activity/Fragment 中的onStop
        super.onStop();
    }

    @Override
    protected void setResource(@Nullable GifDrawable resource) {

    }

    @Override
    public void onDestroy() {//Activity/Fragment 中的onDestroy
        super.onDestroy();
    }
}
