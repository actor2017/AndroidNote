package cn.itcast.ad12;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TextView tvTitle;
    private LinearLayout llContainer;
    private List<Item> itemList = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //切换到下一个页面
            int currentItem = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++currentItem);

            //继续发送延时两秒的消息, 形成循环, 类似递归
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        itemList.add(new Item(R.drawable.a, "巩俐不低俗, 我就不能低俗"));
        itemList.add(new Item(R.drawable.b, "朴树又回来啦, 再唱经典老歌,引万人大合唱"));
        itemList.add(new Item(R.drawable.c, "解密北京电影如何升级"));
        itemList.add(new Item(R.drawable.d, "乐视网TV版大派送"));
        itemList.add(new Item(R.drawable.e, "热血屌丝的反杀"));

        //初始化标题
        tvTitle.setText(itemList.get(0).title);
        //初始化小圆点
        for (int i = 0; i < itemList.size(); i++) {
            ImageView redPoint = new ImageView(this);
            if (i == 0) {
                redPoint.setImageResource(R.drawable.shape_point_red);//设置小红点
            }else redPoint.setImageResource(R.drawable.shape_point_gray);

            //给圆点设置边距
            //布局参数: 父控件是谁,就用谁的LayoutParams, 宽高包裹内容
            //作用: 设置布局宽高, 外边距等信息,  android:layout_xxxx
            //凡是布局文件中带有layout_前缀的属性,都可以使用布局参数来在代码中设置
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = i == 0 ? 0 : 5;//从第二个点开始设置左边距
            redPoint.setLayoutParams(params);//给ImageView设置布局参数
            llContainer.addView(redPoint);//将小圆点添加给线性布局
        }

        mViewPager.setAdapter(new MyAdapter());

        //设置ViewPager初始页面位置
        //mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        mViewPager.setCurrentItem(itemList.size() * 1000000);

        //监听ViewPager页面滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {//页面滑动监听
            }

            @Override
            public void onPageSelected(int position) {
                position = position % itemList.size();//某个页面被选中的监听
                tvTitle.setText(itemList.get(position).title);
                //更新小红点的位置
                for (int i = 0; i < llContainer.getChildCount(); i++) {
                    ImageView view = (ImageView) llContainer.getChildAt(i);//返回当前位置的子控件

                    //当前位置改为红丝
                    if (i == position) {
                        view.setImageResource(R.drawable.shape_point_red);
                    } else view.setImageResource(R.drawable.shape_point_gray);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {//页面滑动状态监听
            }
        });

        //启动自动轮播效果
        mHandler.sendEmptyMessageDelayed(0, 2000);//延时两秒发送消息

        //监听ViewPager触摸事件, 在适当时机启动或停止轮播
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);//停止轮播
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeMessages(0);
                        mHandler.sendEmptyMessageDelayed(0, 2000);//继续轮播
                        break;
                }
                return false;//返回true表示全权消费掉此事件,会导致viewpager无法响应触摸; 所以此处要返回false
            }
        });
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;//实现循环滑动, 返回整数最大值
        }

        //判断当前要显示的view是否就是返回的object
        //是的话, 才正常显示这个view,固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化布局, 类似getView方法
        //ViewPager优化方法: 默认只保留当前页, 上一页和下一页的对象, 其他对象都会销毁, 从而节省了内存,提高了性能
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //0,1,2,3,4   5,6,7,8,9
            //            10,11,12,13,14
            //            0,1,2,3,4
            //除以5取余, 模于5
            position = position % itemList.size();

            //1. 初始化布局对象
            ImageView imageView = new ImageView(MainActivity.this);
            //imageView.setImageResource(mImageIds[position]);//src:为了保持图片最佳比例, 在某些时候会有适当留白
            imageView.setBackgroundResource(itemList.get(position).drawableRes);//以背景方式设置图片, 图片会填充整个ImageView

            //2. 将布局对象添加给当前容器
            container.addView(imageView);

            System.out.println("初始化布局了....." + position);

            //3. 返回布局对象
            return imageView;
        }

        //销毁布局
        //固定写法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);删除此代码, 避免出异常
            container.removeView((View) object);//从容器中移除布局对象

            System.out.println("销毁布局了...." + position);
        }
    }

    private class Item {
        private int drawableRes;
        private String title;
        public Item(@DrawableRes int drawableRes, String title) {
            this.drawableRes = drawableRes;
            this.title = title;
        }
    }

    //页面销毁的时候,停止轮播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
