package cn.itcast.zhxa12.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import cn.itcast.zhxa12.R;
import cn.itcast.zhxa12.activity.MainActivity;
import cn.itcast.zhxa12.pager.BasePager;
import cn.itcast.zhxa12.pager.impl.GovAffairsPager;
import cn.itcast.zhxa12.pager.impl.HomePager;
import cn.itcast.zhxa12.pager.impl.NewsCenterPager;
import cn.itcast.zhxa12.pager.impl.SettingPager;
import cn.itcast.zhxa12.pager.impl.SmartServicePager;

/**
 * Created by zhengping on 2017/2/20,15:46.
 */

public class ContentFragment extends BaseFragment {

    private ArrayList<BasePager> mPagers;
    private ViewPager viewPager;

    @Override
    public View initView() {
        /*TextView tv = new TextView(activity);
        tv.setText("我是主界面的TextView");*/
        View view = View.inflate(activity, R.layout.fragment_content, null);
        //RelativeLayout relativeLayout = new RelativeLayout(activity);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //一般我们给一个控件设置adapter的时候，要确保，数据集合中的内容不为null
        //viewPager.setAdapter(new MyAdapter());
        //viewPager.set
        //如何让Viewpager不能左右滑动
        //1、给ViewPager设置一个触摸监听
        /*viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*/
        //viewPager为什么能够左右滑动，那是因为ViewPager重写了onTouchEvent，在onTouchEvent做的逻辑
        //让ViewPager不要调用到onTouchEvent就可以了
        //事件的分发  dispatchTouchEvent
        //事件的拦截 onInterceptTouchEvent
        //事件的响应 onTouchEvent
        //一个事件如果到达了一个view上，最先最先调用的会是这个View中的dispatchTouchEvent
       // View view;

        RadioGroup rgBottom = (RadioGroup) view.findViewById(R.id.rgBottom);
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //checkedId:当前选中的RadioButton的id
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbHome:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rbNewsCenter:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rbSmartService:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rbGovAffairs:
                        viewPager.setCurrentItem(3,false);
                        break;
                    case R.id.rbSetting:
                        viewPager.setCurrentItem(4,false);
                        break;
                }
            }
        });

        //让RadioGroup默认选择第0项
        rgBottom.check(R.id.rbHome);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0 || position == mPagers.size() - 1) {
                    //禁用侧边栏效果
                    setSlidingMenuEnable(false);
                } else {
                    //启用侧边栏
                    setSlidingMenuEnable(true);
                }

                mPagers.get(position).initData();//只有当用户点击到这个界面的时候，再去加载这一页的数据

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //默认将SLidingMenu的侧滑功能禁用
        setSlidingMenuEnable(false);



        return view;
    }

    private void setSlidingMenuEnable(boolean enable) {
        //1、拿到SlidingMenu的对象
        MainActivity mainActivity = (MainActivity) activity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if(enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

    //把主界面所需要的Pager对象事先创建出来，避免instantiateItem重复创建Pager对象
    //initData这个方法是在ContentFragment所依附的Activity创建成功之后会进行调用
    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(activity));//每new出一个Pager的对象，都会去加载一个布局文件：标题栏+空的帧布局
        mPagers.add(new NewsCenterPager(activity));
        mPagers.add(new SmartServicePager(activity));
        mPagers.add(new GovAffairsPager(activity));
        mPagers.add(new SettingPager(activity));

        viewPager.setAdapter(new MyAdapter());//有数据的时候方可设置adapter

        //默认加载第0个界面的数据
        mPagers.get(0).initData();
    }

    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagers.get(1);
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //return 5;//魔术数字
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //ViewPager带有预加载功能
        //ViewPager中instantiateItem可能会被重复调用
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*TextView tv = new TextView(activity);
            tv.setText("我是ViewPager的其中一个Item");
            container.addView(tv);*/
            /*View view = View.inflate(activity, R.layout.xxx, null);
            container.addView(view);
            view.findViewById()*/
           /* BasePager basePager = new BasePager(activity);
            basePager.initData();
            container.addView(basePager.view);*/
            /*BasePager basePager = null;
            switch (position) {
                case 0:
                    basePager = new HomePager(activity);
                    break;
                case 1:
                    basePager = new NewsCenterPager(activity);
                    break;
                case 2:
                    basePager = new SmartServicePager(activity);
                    break;
                case 3:
                    basePager = new GovAffairsPager(activity);
                    break;
                case 4:
                    basePager = new SettingPager(activity);
                    break;

            }*/
            //由于ViewPager有预加载下一页的功能，预加载下一页的View对象，对于下一页的网络数据，我们不应该在这里进行处理
            BasePager basePager = mPagers.get(position);
            //basePager.initData();//父类的引用指向子类的对象，多态
            //basePager.initData方法一旦执行完成之后，basePager中View对象=标题栏+帧布局(TextView)
            container.addView(basePager.view);
            return basePager.view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
