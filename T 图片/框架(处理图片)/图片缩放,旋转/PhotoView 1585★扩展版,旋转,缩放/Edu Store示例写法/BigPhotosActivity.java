package com.kuchuan.education.view.impl.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.jaeger.library.StatusBarUtil;
import com.kuchuan.education.R;
import com.kuchuan.education.gson.CommentPicsListGson;

import java.util.ArrayList;

import CirclePageIndicator.CirclePageIndicator;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 大图片
 */
public class BigPhotosActivity extends BaseActivity {

    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;
    @BindView(R.id.cpi_circlepageindicator)
    CirclePageIndicator cpiCirclepageindicator;
    private ArrayList<CommentPicsListGson.DataBean> pics;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photos);
        ButterKnife.bind(this);

        StatusBarUtil.setTranslucentForImageView(this,30,null);
        pics = (ArrayList<CommentPicsListGson.DataBean>) getIntent().getSerializableExtra("pics");
        position = getIntent().getIntExtra("position", 0);

        vpViewpager.setAdapter(new MyViewPagerAdapter());
        cpiCirclepageindicator.setViewPager(vpViewpager);
        cpiCirclepageindicator.setCurrentItem(position);
    }

    //viewpager的adapter
    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //View view = View.inflate(BigPhotosActivity.this,R.layout.item_bigphoto_pic,null);
            View view = LayoutInflater.from(BigPhotosActivity.this).inflate(R.layout
                    .item_bigphoto_pic, container, false);

            //1、创建View对象
//            ImageView ivPic = (ImageView) view.findViewById(R.id.iv_pic);
            PhotoView pvPic = (PhotoView) view.findViewById(R.id.pv_pic);//........................①
            pvPic.enable();//启用图片缩放功能,默认为禁用，会跟普通的ImageView一样......................②
            loadImage(BigPhotosActivity.this, pics.get(position).imgUrl, pvPic, null, null, null);//③
            //2、将iv添加到container
            container.addView(view);
            //3、将iv返回
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
