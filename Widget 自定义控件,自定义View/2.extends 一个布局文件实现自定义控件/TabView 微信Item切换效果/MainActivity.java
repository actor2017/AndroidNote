package com.ly.wechat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.ly.wechat.fragment.TabFragment;
import com.ly.wechat.widget.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabView btnWx;
    private TabView btnTxl;
    private TabView btnFx;
    private TabView btnw;

    private List<String>          titles    = new ArrayList<>(Arrays.asList("微信", "通讯录", "发现", "我"));
    private SparseArray<Fragment> fragments = new SparseArray<>(titles.size());
    private List<TabView>         buttons   = new ArrayList<>(titles.size());

    private String SELECTED_POSITION = "SELECTED_POSITION";//选中的Item, 用于 屏幕旋转/系统 恢复
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //恢复 屏幕旋转/系统 后的值
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(SELECTED_POSITION, 0);
        }

        viewPager = findViewById(R.id.vp_main);
        btnWx = findViewById(R.id.btn_wx);
        btnTxl = findViewById(R.id.btn_txl);
        btnFx = findViewById(R.id.btn_fx);
        btnw = findViewById(R.id.btn_w);
        btnWx.setIconAndText(R.drawable.aio, R.drawable.ain, "微信");
        btnTxl.setIconAndText(R.drawable.aim, R.drawable.ail, "通讯录");
        btnFx.setIconAndText(R.drawable.aiq, R.drawable.aip, "发现");
        btnw.setIconAndText(R.drawable.ais, R.drawable.air, "我");
        buttons.add(btnWx);
        buttons.add(btnTxl);
        buttons.add(btnFx);
        buttons.add(btnw);
        setCurrentTab(currentPosition);//设置选中项
        //设置点击事件
        for (int i = 0; i < buttons.size(); i++) {
            TabView tabView = buttons.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentTab(finalI);
                    viewPager.setCurrentItem(finalI, false);//不要切换动画
                }
            });
        }


        viewPager.setOffscreenPageLimit(titles.size());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return TabFragment.newInstance(titles.get(i));//这儿一定是new
            }

            @Override
            public int getCount() {
                return titles.size();
            }

            //实例化
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                fragments.put(position, fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                fragments.remove(position);
                super.destroyItem(container, position, object);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                buttons.get(position).setProgress(1 - positionOffset);
                if (position != buttons.size() - 1) buttons.get(position + 1).setProgress(positionOffset);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_POSITION, viewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    //设置选中项
    private void setCurrentTab(int position) {
        for (int i = 0; i < buttons.size(); i++) {
            TabView tabView = buttons.get(i);
            if (i == position) {
                tabView.setProgress(1);
            } else tabView.setProgress(0);
        }
    }
}
