package com.ly.hihifriend.fragment;

import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.ly.hihifriend.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description: 首页
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/15 on 14:20
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout       appBarLayout;
    @BindView(R.id.tv_search)//搜索
    TextView           tvSearch;
    @BindView(R.id.tab_layout)
    TabLayout          tabLayout;
    @BindView(R.id.view_pager)
    ViewPager          viewPager;

    Unbinder unbinder;
    private List<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 20, tabLayout);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                swipeRefreshLayout.setEnabled(verticalOffset >= 0);//向上偏移是负数,0:刚好划出
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            //如果5秒内还没消失, 那就消失
            swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 1000);
        });

        fragments = FragmentFactory4Home.getFragments();
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view == null) tab.setCustomView(R.layout.custom_tab_layout_text);
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setTextSize(20);//ConvertUtils.sp2px()
                textView.setTypeface(Typeface.DEFAULT_BOLD);//加粗
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view == null) tab.setCustomView(R.layout.custom_tab_layout_text);
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setTextSize(17);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.tv_search, R.id.iv_k_song_king, R.id.iv_chat_room})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search://搜索
                toast("搜索");
                break;
            case R.id.iv_k_song_king://k歌之王
                toast("k歌之王");
                break;
            case R.id.iv_chat_room://聊天室
                toast("聊天室");
                break;
        }
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentFactory4Home.titles[position];
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
        }

        @Override
        public void registerDataSetObserver(@NonNull DataSetObserver observer) {
            if (observer != null) super.registerDataSetObserver(observer);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
