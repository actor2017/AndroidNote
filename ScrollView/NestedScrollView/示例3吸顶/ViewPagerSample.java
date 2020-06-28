package com.example.wenlaisu.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenlaisu on 2018/4/12.
 * https://blog.csdn.net/suwenlai/article/details/79960708
 * https://github.com/wenlai521/viewpagerDemo/tree/master
 */
public class ViewPagerSample extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView        iv;
    @BindView(R.id.tab_layout)
    TabLayout        tabLayout;
    @BindView(R.id.ll)
    LinearLayout     ll;
    @BindView(R.id.view_pager)
    CustomViewPager  viewPager;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.rl)
    RelativeLayout   rl;

    private List<Fragment>  mFragments = new ArrayList<>();;
    private String[] mTitles = new String[]{"主页", "微博", "相册"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);

        setupViewPager();
    }


    private void setupViewPager() {
        mFragments.add(new ListFragment(viewPager, 0));
        mFragments.add(new ListFragment2(viewPager, 1));
        mFragments.add(new ListFragment3(viewPager, 2));

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int
                    oldScrollX, int oldScrollY) {

                //下面几行是关键代码
                if (scrollY > iv.getHeight() && tabLayout.getParent() == ll) {
                    ll.removeView(tabLayout);
                    rl.addView(tabLayout);
                } else if (scrollY < iv.getHeight() && tabLayout.getParent() == rl) {
                    rl.removeView(tabLayout);
                    ll.addView(tabLayout);
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {//每次页面改变都重写测量, 非常不科学
                //自定义方法,重写设置高度
                viewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
