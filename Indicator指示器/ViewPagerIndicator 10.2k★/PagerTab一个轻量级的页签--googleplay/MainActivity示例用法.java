package cn.itcast.googleplay12.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;



import cn.itcast.googleplay12.R;
import cn.itcast.googleplay12.application.MyApplication;
import cn.itcast.googleplay12.fragment.AppFragment;
import cn.itcast.googleplay12.fragment.BaseFragment;
import cn.itcast.googleplay12.fragment.CategoryFragment;
import cn.itcast.googleplay12.fragment.FragmentFactory;
import cn.itcast.googleplay12.fragment.GameFragment;
import cn.itcast.googleplay12.fragment.HomeFragment;
import cn.itcast.googleplay12.fragment.HotFragment;
import cn.itcast.googleplay12.fragment.RecommendFragment;
import cn.itcast.googleplay12.fragment.SubjectFragment;
import cn.itcast.googleplay12.utils.UiUtils;
import cn.itcast.googleplay12.widget.PagerTab;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* MyApplication.applicationContext;
        UiUtils.getContext();*/
        // getString(R.string.app_name);

        PagerTab pagerTab = (PagerTab) findViewById(R.id.pagerTab);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        //设置完viewPager的adapter之后才能将页签控件关联
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        pagerTab.setViewPager(viewPager);

        //runOnUiThread();
       /// viewPager.setOffscreenPageLimit(0);  LazyViewPager

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.getFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //此时loadData偏早了一些，因为Activity  的onCreate走完，不代表我们的界面就显示出来
        //FragmentFactory.getFragment(0).loadData();
       // mHandler.sendEmptyMessageDelayed(0, 1000);
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FragmentFactory.getFragment(0).loadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //viewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });



    }

    /*private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentFactory.getFragment(0).loadData();
        }
    };*/

    //如果ViewPager显示的普通的View对象  ，那么使用PagerAdapter
    //如果ViewPager显示的Fragment所包装的View对象  ，那么使用FragmentPagerAdapter
    class MyAdapter extends FragmentPagerAdapter {

        private final String[] mTabNames;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UiUtils.getStringArray(R.array.tab_names);
        }

        //此方法是给页签控件调用，返回在某一个位置上，页签要显示什么样的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment baseFragment = FragmentFactory.getFragment(position);
            return baseFragment;
        }

        @Override
        public int getCount() {
            // return 7;//魔术数字
            return mTabNames.length;
        }
    }

}
