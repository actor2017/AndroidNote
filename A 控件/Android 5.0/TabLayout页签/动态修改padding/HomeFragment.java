package com.ly.hihifriend.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.jaeger.library.StatusBarUtil;
import com.ly.hihifriend.R;
import com.ly.hihifriend.activity.AddFriendActivity;
import com.ly.hihifriend.activity.ChatRoomListActivity;
import com.ly.hihifriend.activity.ChatingServiceActivity;
import com.ly.hihifriend.utils.Global;
import com.ly.hihifriend.widget.SwipeRefreshLayoutCompatViewPager;

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
    SwipeRefreshLayoutCompatViewPager swipeRefreshLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout                      appBarLayout;
    @BindView(R.id.tv_search)//搜索
    TextView                          tvSearch;
    @BindView(R.id.tab_layout)
    TabLayout                         tabLayout;
    @BindView(R.id.view_pager)
    ViewPager                         viewPager;

    Unbinder unbinder;
    private List<Fragment> fragments;
    private NearbyFragment nearbyFragment;
    private OnLineFragment onLineFragment;
    private String[] titles;
    private int tabLayoutPaddingtop;//tabLayou的paddingTop
    private int appBarLayoutHeight;//appBarLayout高度
    private int statusBarHeight;//状态栏高度
    private int currentAppBarLayoutHeight;//滑动过程中appBarLayout的高度

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 20, null);//, tabLayout

        tabLayoutPaddingtop = tabLayout.getPaddingTop();
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            swipeRefreshLayout.setEnabled(verticalOffset >= 0);//向上偏移是负数,0:刚好划出
            if (appBarLayoutHeight <= 0) appBarLayoutHeight = appBarLayout.getMeasuredHeight();
            if (statusBarHeight <= 0) statusBarHeight = BarUtils.getStatusBarHeight();
            if (appBarLayoutHeight > 0) {
                //频繁调用很卡顿, 所以每隔50px设置一次
                if (Math.abs(verticalOffset - currentAppBarLayoutHeight) > 50 || -verticalOffset == appBarLayoutHeight || verticalOffset == 0) {
                    currentAppBarLayoutHeight = verticalOffset;
                    double percent = -verticalOffset * 1.0 / appBarLayoutHeight;//向上滑动百分比
                    int offset = (int) (statusBarHeight * percent);//偏移量
                    setOffset(offset);
                }
            }
        });
        fragments = FragmentFactory4HomeFrag.getFragments();
        nearbyFragment = (NearbyFragment) fragments.get(0);
        onLineFragment = (OnLineFragment) fragments.get(1);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            //如果3秒内还没消失, 那就消失
            swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000);
            int position = tabLayout.getSelectedTabPosition();
            if (position == 0) {
                nearbyFragment.getNearbyList(1);
            } else if (position == 1) onLineFragment.getOnlineList(1);
        });
        titles = FragmentFactory4HomeFrag.titles;
        viewPager.setAdapter(new MyFragmentStatePagerAdapter(getChildFragmentManager(), fragments, titles));
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

    public void setRefresh(boolean refresh) {
        if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(refresh);
    }

    //R.id.iv_k_song_king, R.id.iv_chat_room
    @OnClick({R.id.tv_search, R.id.iv_voice_party, R.id.iv_chat, R.id.iv_video_party, R.id.iv_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search://搜索
                startActivity(new Intent(activity, AddFriendActivity.class), tvSearch);
                break;
            case R.id.iv_voice_party://语音派对(k歌之王iv_k_song_king)
                Intent intent1 = new Intent(activity, ChatRoomListActivity.class)
                        .putExtra(Global.isChatRoom, false);
                startActivity(intent1);
                break;
            case R.id.iv_chat://聊天
                startActivity(new Intent(activity, ChatingServiceActivity.class));
                break;
            case R.id.iv_video_party://视频派对(聊天室iv_chat_room)
                Intent intent = new Intent(activity, ChatRoomListActivity.class)
                        .putExtra(Global.isChatRoom, true);
                startActivity(intent);
                break;
            case R.id.iv_date://约会
                toast("约会");
                break;
        }
    }

//    private ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabLayout.getLayoutParams();
    //设置偏移
    private void setOffset(int offSet) {
        //这种方式会有bug, 当点击第二个TabItem的时候, 下方的indicator不会划到第2个TabItem下方去
//        layoutParams.setMargins(layoutParams.leftMargin, offSet, layoutParams.rightMargin, layoutParams.bottomMargin);
//        tabLayout.requestLayout();

        tabLayout.setPaddingRelative(0, offset, 0, 0);//start = top = end = bottom = 0
        //tabLayout.setPaddingRelative(tabLayout.getPaddingStart(), tabLayoutPaddingtop + offSet, tabLayout.getPaddingEnd(), tabLayout.getPaddingBottom());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentFactory4HomeFrag.clear();
        unbinder.unbind();
    }
}
