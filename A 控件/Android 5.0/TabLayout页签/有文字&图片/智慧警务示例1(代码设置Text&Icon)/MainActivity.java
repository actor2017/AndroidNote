package com.kuchuan.wisdompolice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.fragment.FragmentFactoryForMainActivity;
import com.kuchuan.wisdompolice.global.Global;
import com.kuchuan.wisdompolice.view.NoMoveFragmentPagerAdapter;

import org.androidpn.client.BatteryService;
import org.androidpn.client.NotificationService;
import org.androidpn.client.ServiceManager;
import org.androidpn.client.ServiceStateUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kuchuan.wisdompolice.application.MyApplication.aCache;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_viewpager)
    ViewPager vpViewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private int[] pressedIcons = new int[]{R.drawable.home_pressed, R.drawable.fuwu_pressed,
            R.drawable.baobei_pressed, R.drawable.me_pressed};
    private int[] normalIcons = new int[]{R.drawable.home_normal, R.drawable.fuwu_normal,
            R.drawable.baobei_normal, R.drawable.me_normal};
    private String[] texts = new String[]{"首页","服务","报备","我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vpViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpViewPager);//1.先删除所有的标签再添加
        tabLayout.removeAllTabs();//2.如果不removeAll,tabLayout里会有8个tabItem.
        //参考资料:http://m.blog.csdn.net/xxkalychen/article/details/72790691

        tabLayout.addTab(tabLayout.newTab().setText(texts[0]).setIcon(pressedIcons[0]));
        tabLayout.addTab(tabLayout.newTab().setText(texts[1]).setIcon(normalIcons[1]));
        tabLayout.addTab(tabLayout.newTab().setText(texts[2]).setIcon(normalIcons[2]));
        tabLayout.addTab(tabLayout.newTab().setText(texts[3]).setIcon(normalIcons[3]));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //改变Tab 状态
                for(int i=0;i< tabLayout.getTabCount();i++){
                    if(i == tab.getPosition()){
//                        tabLayout.getTabAt(i).setText()
                        tabLayout.getTabAt(i).setIcon(getResources().getDrawable(pressedIcons[i]));
                    }else{
                        tabLayout.getTabAt(i).setIcon(getResources().getDrawable(normalIcons[i]));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

		//        switchToPosition(0); //默认选中某项,已经默认0,不用调用
    }

	public void switchToPosition(int position){
        tabLayout.getTabAt(position).select();
    }
}
