package com.kuchuan.wisdompolice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.fragment.FragmentFactoryForMainActivity;
import com.kuchuan.wisdompolice.global.Global;
import com.kuchuan.wisdompolice.gson.VillageOrBuildingCardDetailGson;
import com.kuchuan.wisdompolice.utils.MyOkHttpUtils;
import com.kuchuan.wisdompolice.view.NoMoveFragmentPagerAdapter;

import org.androidpn.client.BatteryService;
import org.androidpn.client.NotificationService;
import org.androidpn.client.ServiceManager;
import org.androidpn.client.ServiceStateUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.kuchuan.wisdompolice.application.MyApplication.aCache;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_viewpager)
    ViewPager vpViewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.main_title)
    TextView mainTitle;

    private long flagClicked = 0;

    private int[] tabItemIcoms = new int[]{R.drawable.selector_tab_item_icon1,R.drawable.selector_tab_item_icon2,
        R.drawable.selector_tab_item_icon3,R.drawable.selector_tab_item_icon4};
    private String[] texts = new String[]{"首页", "申请审核", "报备审批", "我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), texts);
        vpViewPager.setAdapter(myFragmentPagerAdapter);
        tabLayout.setupWithViewPager(vpViewPager);

        //遍历获取TabItem,设置成自定义View
        for (int i = 0; i < texts.length; i++) {
            tabLayout.getTabAt(i).setCustomView(getTabItemView(i, tabLayout));
        }
    }

	//返回自定义View
	private View getTabItemView (int position, ViewGroup parent) {
		View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_tab_item, parent, false);
		ImageView iv = (ImageView) view.findViewById(R.id.iv);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		tv.setText(texts[position]);
		iv.setImageResource(tabItemIcoms[position]);
		return view;
	}

    //供Fragment调用
    public void switchToPosition(int position){
        tabLayout.getTabAt(position).select();
    }

    /**
     * 重写返回键,flagCount是全局变量
     * private int flagCount = 0;
     */
    @Override
    public void onBackPressed() {
		if (tabLayout.getSelectedTabPosition() != 0) {
            switchToPosition(0);
            return;
        }
        if (System.currentTimeMillis() - flagClicked <= 1000) {
            super.onBackPressed();
        } else {
            flagClicked = System.currentTimeMillis();
            toast("再点击一下退出");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        println("onDestroy");
    }
}
