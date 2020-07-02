package com.ly.meeting.frag;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.futils.annotation.view.ContentView;
import com.futils.annotation.view.ViewID;
import com.ly.meeting.R;
import com.ly.meeting.base.BaseFrag;

@ContentView(R.layout.frag_home)
public class HomeFrag extends BaseFrag implements View.OnClickListener {

    @ViewID(R.id.appbar_layout)
    AppBarLayout       appBarLayout;
    @ViewID(R.id.swipeRefreshLayout)//下拉刷新
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewID(R.id.tablayout)
    TabLayout tablayout;
    @ViewID(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onViewCreated(Bundle bundle) {
        viewPager.setOffscreenPageLimit(3);
        //getActivity().getSupportFragmentManager()也可以...
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(), fragments));
        tablayout.setupWithViewPager(viewPager);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {//下拉刷新冲突的关键代码
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                swipeRefreshLayout.setEnabled(verticalOffset >= 0);//向上偏移是负数,0:刚好划出
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            getBanner();
            //如果5秒内还没消失, 那就消失
            swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 5000);
        });
    }
}