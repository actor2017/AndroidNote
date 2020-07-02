package com.kuchuanyun.test;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    private String[] pageTitles = {"Tab 1", "Tab 2", "Tab 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 0);

        viewpage.setAdapter(new MyViewPagerAdapter());
        tablayout.setupWithViewPager(viewpage);
    }

    private class MyViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pageTitles.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(TabLayoutActivity.this).inflate(R.layout
                    .item_viewpager, container, false);
            container.addView(view);
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles[position];
        }
    }
}
