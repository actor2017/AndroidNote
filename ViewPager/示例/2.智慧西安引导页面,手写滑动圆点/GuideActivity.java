package com.heima.wisdomxian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.heima.wisdomxian.R;
import com.heima.wisdomxian.global.GlobalConstants;
import com.heima.wisdomxian.utils.SharedPreferencesUtils;

/**
 * 引导界面
 */
public class GuideActivity extends Activity {

    private ViewPager vp_viewpager;
    private int[] mImgs = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    int COUNT = mImgs.length;       //获取总的个数
    private LinearLayout ll_dots;
    private ImageView iv_dot_red;
    private int distance;           //两个小灰点之间的距离
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除标题栏（ActionBar=TitleBar）
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gride);

        //1.设置适配器
        vp_viewpager = (ViewPager) findViewById(R.id.vp_viewpager);
        vp_viewpager.setAdapter(new mAdapter());

        //2.初始化小灰点
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        initDots();

        //3.对视图树进行监听,当视图树走完的时候才去获取2个小灰点之间的举例
        /**
         * 计算两点之间的距离
         * View viewOne = ll_dots.getChildAt(1);    //结果:0
         * View viewZero = ll_dots.getChildAt(0);   //结果:0
         * int distance = viewOne.getLeft() - viewZero.getLeft();
         * System.out.println("distance=" + distance);
         * left值在此时都为0
         *
         * view的绘制流程:
         * 测量onMeasure-->确定位置onLayout-->绘制onDraw
         * 只有经过了绘制流程之后，才会得到一个控件真实的位置和大小信息
         * 只走完onCreate不代表我们就能看到控件,onCreate结束的时候，这个Activity中的控件其实并没有走完绘制流程
         * 千万不要在Activity的onCreate方法中直接获取一个控件的大小信息和位置信息
         *
         * 视图树：Android中就是使用视图树来管理view对象
         */
        ll_dots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {

            //当视图树中所有的view都走完了onLayout方法之后会进行的方法回调
            @Override
            public void onGlobalLayout() {
                distance = ll_dots.getChildAt(1).getLeft() - ll_dots.getChildAt(0).getLeft();
                System.out.println("distance" + distance);
                //使用原则：由于onGlobalLayout这个方法会被调用多次，当我们获取到我们需要的信息之后，
                // 我们需要将监听移除，节约内存消耗
                //ll_dots.getViewTreeObserver().removeOnGlobalLayoutListener(this);//需要API16
                //下面这个是作者发现写错了才弄成过时的
                ll_dots.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        //4.监听ViewPager页面变化的事件,改变小红点左边距,改变button的visiable
        iv_dot_red = (ImageView) findViewById(R.id.iv_dot_red);
        btn_start = (Button) findViewById(R.id.btn_start);
        vp_viewpager.addOnPageChangeListener(new mPageChangeLis());

        //ViewPager具备预加载  预加载下一页的view对象
        //ViewPager默认情况下能够同时保存的view对象有3个   左中右 1+1+1

        //viewPager.setOffscreenPageLimit(0);   //传0无效
        // 取消ViewPager的预加载功能,自己实现ViewPager,LazyViewPager
    }

    //1.下面是viewpager的适配器
    class mAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return COUNT;
        }

        //这个方法的写法非常固定
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //返回ViewPager在某一个位置上的view对象,比较类似于getView
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);//要删掉,点进去会看见抛异常
            //1、创建View对象
            ImageView imageView = new ImageView(GuideActivity.this);
            //imageView.setImageResource(mImgs[position]);      //图片不缩放,会有留白
            imageView.setBackgroundResource(mImgs[position]);
            //2、将iv添加到container
            container.addView(imageView);
            //3、将iv返回
            return imageView;       //注意:这儿不能反悔container★★★★★★★★★★★★
        }

        //这个方法的写法非常固定
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);//要删掉,点进去会看见抛异常
            container.removeView((View) object);
        }
    }

    //4.viewpager的监听
    class mPageChangeLis implements ViewPager.OnPageChangeListener {
        //指的是当界面在整个滚动的时候会进行方法回调
        //positionOffset:界面移动的比例,0-1,到下一个页面后变成0
        //positionOffsetPixels:界面移动的真实像素,最大值是手机X轴像素(屏幕宽,例:320),到下一个页面后归0
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            System.out.println("position" + position + "positionOffset" + positionOffset +
                    "positionOffsetPixels" + positionOffsetPixels);

            //改变ivRed的位置信息，其实就是改变ivRed的marginLeft的值
            //ivRed.setleft
            //此时不new出LayoutParams,如果控件是new出来的话，那么布局参数对象也new★★★★★★★★★★★★★★★★
            //如果控件是在布局文件中配置的话，那么就使用getLayoutParams★★★★★★★★★★★★★★★★★★★★★★
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_dot_red
                    .getLayoutParams();
            layoutParams.leftMargin = (int) ((position + positionOffset) * distance);
            System.out.println(distance + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            iv_dot_red.setLayoutParams(layoutParams);
        }

        //在position这个界面被选中的瞬间，会进行的方法回调
        @Override
        public void onPageSelected(int position) {
            if (position == COUNT - 1) {
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setClickable(true);
                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesUtils.putBoolean(GuideActivity.this, GlobalConstants
                                .GRIDE_FINISHED, true);
                        startActivity(new Intent(GuideActivity.this,MainActivity.class));
                    }
                });
            } else {
                btn_start.setVisibility(View.GONE);
                btn_start.setClickable(false);
            }
        }

        //当界面滚动状态改变的时候，会进行的方法回调
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //2.初始化小灰点,并设置左边距
    private void initDots() {
        for (int i = 0; i < COUNT; i++) {
            ImageView imageView = new ImageView(this);

            //布局参数对象LayoutParams
            //LayoutParams是针对布局文件中以android:layout_xxx这种类型属性的封装
            //LayoutParams的类型有很多种，究竟选哪一种要看父控件
            //控件究竟有哪些 layout_开头的属性，很大程度上取决于父控件
            if (i > 0) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams
                                .WRAP_CONTENT);

                //从第2个小圆点开始设置成灰色,并且设置边距
                layoutParams.leftMargin = 10;
                //给ImageView设置布局参数
                imageView.setLayoutParams(layoutParams);
            }
            imageView.setImageResource(R.drawable.shape_dot_grey);
            ll_dots.addView(imageView);
        }
    }

}
