package com.ly.hihifriend.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ly.hihifriend.R;
import com.ly.hihifriend.adapter.ChatRoomLinkMicrophoneAdapter;
import com.ly.hihifriend.fragment.ChatRoomAlreadyLinkFragment;
import com.ly.hihifriend.fragment.ChatRoomApplyListFragment;
import com.ly.hihifriend.fragment.FragmentFactory4ChatRoomActi;

import java.util.List;

/**
 * Description: 视频主播-->麦序
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/4/29 on 9:40
 */
public class BottomSheetVideoActivity extends AppCompatActivity {

    private List<Fragment>              fragments;
    private ChatRoomAlreadyLinkFragment alreadyLinkFragment;//已连麦
    private ChatRoomApplyListFragment   applyListFragment;//申请队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_bottom_sheet_video);

//        setTheme(R.style.BottomSheet);//清单文件添加
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        fragments = FragmentFactory4ChatRoomActi.getFragments();
        alreadyLinkFragment = (ChatRoomAlreadyLinkFragment) fragments.get(0);
        applyListFragment = (ChatRoomApplyListFragment) fragments.get(1);//申请队列
        viewPager.setAdapter(new ChatRoomLinkMicrophoneAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.alpha = 1f;//1.０全透明．０不透明．
        window.setAttributes(windowParams);
    }
}
