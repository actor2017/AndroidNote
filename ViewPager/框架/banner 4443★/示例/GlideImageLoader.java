package com.ly.bridgeemergency.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ly.bridgeemergency.info.BannerFindallInfo;
import com.youth.banner.loader.ImageLoader;

/**
 * Description: 加载首页轮播图
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-8-7 on 16:46
 *
 * @version 1.0
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerFindallInfo.DataBean data = (BannerFindallInfo.DataBean) path;
        if (data != null) {
            Glide.with(context).load(Global.HOST_PIC.concat(data.fileUrl)).into(imageView);
        }
    }
}
