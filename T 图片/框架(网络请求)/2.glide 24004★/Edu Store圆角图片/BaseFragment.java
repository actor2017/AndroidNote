package com.kuchuan.education.view.impl.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public abstract class BaseFragment extends Fragment {

    //加载图片
    public void loadImage(Context context,String url,ImageView imageView,@Nullable Integer radius,@Nullable Integer emptyPic,@Nullable Integer errPic){
        if (emptyPic == null) {
            emptyPic = 0;
        }
        if (errPic == null) {
            errPic = 0;
        }
        if (radius == null) {
            radius = 0;
        }
        Glide
                .with(getContext()) // 指定Context
                .load(url)// 指定图片的URL
                .placeholder(emptyPic)// 指定图片未成功加载前显示的图片或颜色,可以不设置(什么都不显示)
				.dontTransform()//加载的图片不变形(在RecyclerView中一定要设置)
                .error(errPic)// 指定图片加载失败显示的图片,可以不设置(什么都不显示)
                //.override(300, 300)//指定图片的尺寸(不设置可注销)
                .skipMemoryCache(false)// 跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//跳过磁盘缓存(ALL:不跳过,NONE:跳过)
                .transform(new GlideRoundTransform(context,radius))//自定义圆角图片
                .into(imageView);//指定显示图片的ImageView
    }
}
