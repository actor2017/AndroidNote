package com.ly.wechat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BannerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[]     resIds = new int[] {
            R.drawable.banner_image1,
            R.drawable.banner_image2,
            R.drawable.banner_image3,
            R.drawable.banner_image4,
            R.drawable.banner_image5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        viewPager = findViewById(R.id.vp_main);
        findViewById(R.id.btn_set_page_margin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setPageMargin(35);//设置page间隔
//                viewPager.setPageMarginDrawable();//设置drawableId | Drawable
            }
        });
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return resIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageResource(resIds[position]);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
    }
}
