package com.heima.ad;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.heima.ad.R.drawable.c;

public class MainActivity extends AppCompatActivity {

    @butterknife.BindView(R.id.view_pager)
    ViewPager viewPager;                    //viewPager
    @butterknife.BindView(R.id.tv_title)
    TextView tvTitle;                       //标题
    @butterknife.BindView(R.id.ll_container)
    LinearLayout llContainer;               //放小圆点的线性布局

    private int[] mImageIds = new int[]{R.drawable.a, R.drawable.b, c, R.drawable.d, R
            .drawable.e};

    private String[] mTitles = new String[]{"巩俐不低俗, 我就不能低俗", "朴树又回来啦, 再唱经典老歌," +
            "引万人大合唱", "解密北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀"};

    //注意:不能写在onCreat()里面
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = viewPager.getCurrentItem();//获取现在的页面
            viewPager.setCurrentItem(++currentItem);//切换到下一个页面
            //super.handleMessage(msg);

            //继续发送延时两秒的消息, 形成循环, 类似递归
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        //1.初始化viewPager
        viewPager.setAdapter(new MyAdapter());//class MyAdapter extends PagerAdapter {

        //2.设置ViewPager初始页面位置
        //mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        viewPager.setCurrentItem(mImageIds.length * 10000);

        //3.手动更新第一页标题
        tvTitle.setText(mTitles[0]);

        //4.初始化红点和灰点
        initPoints();               //for (int i = 0; i < mImageIds.length; i++) {

        //5.启动自动轮播
        mHandler.sendEmptyMessageDelayed(0, 2000);

        //6.监听ViewPager页面滑动,并更新小红点
        initPageChange();           //viewPager.addOnPageChangeListener(

        //7.监听ViewPager触摸事件, 在适当时机启动或停止轮播
        initOnTouch();              //viewPager.setOnTouchListener();

        //8.页面销毁的时候,停止轮播    mHandler.removeCallbacksAndMessages(null);

        //9.    双击事件    点击水波纹效果★★★★★★★★★未实现★★★★★★★★★

        //10.    长按事件    保存图片
    }

    //监听ViewPager触摸事件, 在适当时机启动或停止轮播
    private void initOnTouch() {
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    //当按下的时候
                    case MotionEvent.ACTION_DOWN:
                        //停止轮播
                        mHandler.removeCallbacksAndMessages(null);
                        Toast.makeText(MainActivity.this, "未来记得添加双击事件", Toast.LENGTH_SHORT).show();
                        break;

                    case MotionEvent.ACTION_UP:
                        //继续轮播
                        mHandler.sendEmptyMessageDelayed(0,2000);
                    default:
                        break;
                }
                return false;//返回true表示全权消费掉此事件,会导致viewpager无法响应触摸; 所以此处要返回false
            }
        });
    }

    //初始化红点和灰点
    private void initPoints() {
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView point = new ImageView(this);

            /**
             * 获取布局参数:
             * 父控件是谁,就用谁的LayoutParams, 宽高包裹内容
             * 作用: 设置布局宽高, 外边距等信息,  android:layout_xxxx
             * 凡是布局文件中带有layout_前缀的属性,都可以使用布局参数来在代码中设置
             */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i == 0) {
                //第一个小圆点设置成红色
                point.setImageResource(R.drawable.shape_point_red);
            } else {
                //从第2个小圆点开始设置成灰色,并且设置边距
                params.leftMargin = 5;
                point.setImageResource(R.drawable.shape_point_gray);
            }
            //给ImageView设置布局参数
            point.setLayoutParams(params);

            //将小圆点添加给线性布局
            llContainer.addView(point);
        }
    }

    //监听ViewPager页面滑动
    private void initPageChange() {
        //监听ViewPager页面滑动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //页面滑动监听
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            //某个页面被选中的监听,获取当前页面的5个子布局对象(小圆点),遍历并重新设置颜色
            @Override
            public void onPageSelected(int position) {
                position %= mImageIds.length;
                //更新标题
                tvTitle.setText(mTitles[position]);
                for (int i = 0; i < llContainer.getChildCount(); i++) {//写成:i < mImageIds.length一样的
                    System.out.println("llContainer.getChildCount():" + llContainer.getChildCount
                            ());
                    ImageView childView = (ImageView) llContainer.getChildAt(i);//返回当前位置的子控件
                    if (i == position) {
                        childView.setImageResource(R.drawable.shape_point_red);
                    } else {
                        childView.setImageResource(R.drawable.shape_point_gray);
                    }
                }
            }

            //页面滑动状态监听
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //适配器
    class MyAdapter extends PagerAdapter {
        /**
         * 固定写法,返回条目数量
         */
        @Override
        public int getCount() {
            //实现循环滑动, 返回整数最大值
            return Integer.MAX_VALUE;
        }

        /**
         * 固定写法
         * 判断当前要显示的view是否就是返回的object,是的话, 才正常显示这个view
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * ★★★★★★★★只有这一个不是固定写法★★★★★★★★★
         * <p>
         * 初始化布局, 类似getView方法
         * ViewPager优化方法: 默认只保留当前页, 上一页和下一页的对象, 其他对象都会销毁,
         * 从而节省了内存,提高了性能
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //除以5取余, 模于5
            System.out.println("现在页面的position值:" + position);
            position %= mImageIds.length;
            System.out.println("position %= mImageIds.length后的值:" + position);
            //1. 初始化布局对象
            ImageView imageView = new ImageView(MainActivity.this);

            //src: 为了保持图片最佳比例, 在某些时候会有适当留白
            //imageView.setImageResource(mImageIds[position]);

            //以背景方式设置图片, 图片会填充整个ImageView
            imageView.setBackgroundResource(mImageIds[position]);//这儿写成了setBackgroundColor();调试了半天

            //2. 将布局对象添加给当前容器
            container.addView(imageView);

            System.out.println("初始化布局了....." + position);
            //3. 返回布局对象★★★★★★★★注意,这热不能写成container,调试了半天★★★★★★★★★★
            return imageView;
            //return super.instantiateItem(container, position);
        }

        /**
         * 固定写法,销毁布局
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);删除此代码, 避免出异常
            container.removeView((View) object);

            System.out.println("销毁布局了...." + position);
        }
    }

    //页面销毁的时候,停止轮播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
