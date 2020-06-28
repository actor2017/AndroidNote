package com.wisdomxian.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wisdomxian.R;
import com.wisdomxian.global.GlobalConstants;
import com.wisdomxian.utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本例是一个能"下拉刷新" 和 "上拉加载" 的listView
 * 里面添加了一个能刷新的[头布局]item 和一个 脚布局
 * <p>
 * 可继续间接头/脚布局,和设置adapter加载更多item
 **/

 /** 用法:
 * 1.在布局文件中示例写法:
 * <!--是一个能刷新的ListView,已经加载了一个刷新的头,在TabDetailPager中又陆续的加了一个viewpager的头和下面的item(通过设置适配器)-->
 * <com.heima.wisdomxian.widget.RefreshListView
 * android:id="@+id/lvListNews"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:layout_alignParentTop="true"
 * android:layout_alignParentLeft="true"
 * ndroid:layout_alignParentStart="true">
 * </com.heima.wisdomxian.widget.RefreshListView>
 *
 *
 * 2.在加载布局的时候设置监听,refreshListView.setOnRefreshListener()
 * @see #setOnRefreshListener(OnRefreshListener)
 * <p>
 * 3.加载完成后调用隐藏头布局的方法refreshListView.hideHeaderView(boolean)
 * @see #hideHeaderView(boolean)
 * <p>
 * 4.上拉加载完成后调用隐藏脚布局的方法refreshListView.hideFooterView()
 *   ,并且自己写加载成功,失败,网络访问失败 的toast
 * @see #hideFooterView()
 *
  * 5.上拉加载更多后,注意更新上拉后的"更多(more)"的网址
  *
  *
  *
 * //获取一个控件的大小信息的话，2种方案   1、监听视图树  2、手动测量
 * <p>
 * 1、写了RefreshListView继承ListView
 * 解释构造方法调用时机
 * AttributeSet
 * 2、添加头布局
 * ListView可以添加多个头布局，头布局添加的越早，显示越靠上
 * 3、默认让头布局隐藏
 * 设置可见度的方案--不可行
 * 设置高度的方案-->布局参数对象
 * 设置负的paddingTop的值
 * 获取头布局的高度
 * 1、监听视图树  2、手动测量
 * 4、慢慢的把头布局拖出来（慢慢的把头布局的高度变大   负的paddingTop值慢慢往0趋近）
 * onTouchEvent
 * 获取按下的坐标
 * 获取移动的坐标
 * 获取滑动的偏移量
 * 条件判断 dy > 0 && firstVisiblePosition == 0
 * 5、给ListView增加状态
 * mCurrentState
 * STATE_PULL_TO_REFRESH
 * STATE_RELEASE_TO_REFRESH
 * STATE_REFRESHING
 * 6、在move事件中，根据paddingTop的值改变mCurrentState的值
 * 7、根据mCurrentState的值，刷新UI  refreshUi
 * 8、给箭头增加了动画效果
 * 应该在状态发生改变的时候再refreshUi
 * 9、处理up事件
 * mCurrentState == STATE_PULL_TO_REFRESH
 * mCurrentState == STATE_RELEASE_TO_REFRESH
 * 10、使用了观察者设计模式，在事件发生的时候，由ListView通知TabDetailPager
 * 11、在加载数据成功之后，由TabDetailPager通知ListView
 * 12、设置时间的显示
 * Date       : 2017/2/27 on 18:58.
 */

public class RefreshListView extends ListView {

    public static final int STATE_PULL_TO_REFRESH = 0;//下拉刷新
    public static final int STATE_RELEASE_TO_REFRESH = 1;//松开刷新
    public static final int STATE_REFRESHING = 2;//正在刷新

    private static int mCurrentState = STATE_PULL_TO_REFRESH;//记录现在的状态

    private ImageView iv_arrow;
    private TextView tv_status;
    private TextView tv_date;
    private ProgressBar pb_progress;
    private int headerHeight;
    private RotateAnimation updateAnimation;    //下拉刷新的旋转动画
    private RotateAnimation downAnimation;      //松开刷新的旋转动画
    private float startY;
    private View headerView;                    //头布局(刷新)
    private boolean isRefresh;                 //正在加载更多
     private int footerViewHeight;
     private View footerView;
     private boolean isLoadingMore;

     public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化头布局
        addHeaderView(context);
        //初始化脚布局
        addFooterView(context);
        //初始化下拉刷新,松开刷新的动画
        initAnimation();
    }

     //初始化头布局
    private void addHeaderView(Context context) {
        headerView = View.inflate(context, R.layout.layout_refresh_header, null);

        //给listview添加头布局,可添加多个,头布局添加的越早，显示的位置越靠上
        this.addHeaderView(headerView);

        iv_arrow = (ImageView) headerView.findViewById(R.id.ivArrow);
        pb_progress = (ProgressBar) headerView.findViewById(R.id.pb);
        tv_status = (TextView) headerView.findViewById(R.id.tvStatus);
        tv_date = (TextView) headerView.findViewById(R.id.tvDate);

        String data = SharedPreferencesUtils.getString(context, GlobalConstants.LAST_UPDATE_TIME, null);
        if (data != null) {
            tv_date.setText(data);
        }

        //headerView.setVisibility(View.GONE);
        //不能设置可见度，因为可见度的设置是一瞬间完成的事件，没有慢慢拖出来的效果
        //方案：通过改变头布局的高度，再拖拽的过程中就会有效果了

        //通过改变一个View的paddingTop的值，可以改变一个控件的高度
        //如果设置的是负的padding的值，布局文件起不到效果，在代码中才能起到效果
        //headerView.setPadding(0,-40,0,0);
        //得到headerView的高度
        //View的绘制流程  measure-layout-draw
        //既然此时系统没有走完这个View的绘制流程，那么我们手动的测量一下控件的大小

        //手动测量  传0 和0 代表将测量的工作交给系统来完成，开发者不参与其他的限制意见
        headerView.measure(0, 0);
        //获取已经测量的高度
        headerHeight = headerView.getMeasuredHeight();
        //默认隐藏头布局
        headerView.setPadding(0, -headerHeight, 0, 0);
        //获取一个控件的大小信息的话，2种方案   1、监听视图树  2、手动测量
    }

    //添加脚布局
     private void addFooterView(Context context) {
         footerView = View.inflate(context, R.layout.layout_refresh_footer, null);
         //添加脚布局
         this.addFooterView(footerView);
         //测量脚布局
         footerView.measure(0,0);
         //获取已经测量后的高度
         footerViewHeight = footerView.getMeasuredHeight();
         //初始化隐藏
         footerView.setPadding(0,-footerViewHeight,0,0);
     }

    //初始化刷新的动画
    private void initAnimation() {
        //松开刷新的动画
        updateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        updateAnimation.setDuration(200);
        updateAnimation.setFillAfter(true);

        //下拉刷新的动画
        downAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(200);
        downAnimation.setFillAfter(true);
    }

    //在此时获取down事件的坐标最准确，如果在onTouchEvent中获取起点坐标很可能会受制于子控件的onTouchEvent的返回值
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startY = ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }

    //dispatchTouchEvent--onInterceptTouchEvent--onTouchEvent
    //onTouchEvent的来源：
    //1、自身决定拦截之后，会调用到自身的onTouchEvent
    //2、子控件回传  ，在子控件的onTouchEvent中return false
    //重写触摸事件监听
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //注意这几个的区别,第一个3,4   第2,3个:都是13
        System.out.println(getChildCount()+" "+getCount()+" "+getAdapter().getCount());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取Y轴的起点坐标(无效)
                //startY = ev.getY();     //这儿获取的值无效
                //System.out.println("RefreshListView.ACTION_DOWN.startY=" + startY);//0
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();//获取Y轴的移动坐标
                float dY = moveY - startY;//获取滑动的偏移量
                //获取ListView第一个可见的位置
                if (getFirstVisiblePosition() == 0 && dY > 0) {
                    //改变HeaderView的高度
                    int paddingTop = (int) (dY - headerHeight);//设置后item0的高度
                    headerView.setPadding(0, paddingTop, 0, 0);

                    int oldState = mCurrentState;
                    //判断现在的状态
                    mCurrentState = paddingTop < 0 ? STATE_PULL_TO_REFRESH :
                            STATE_RELEASE_TO_REFRESH;
                    if (oldState != mCurrentState) {
                        //在滑动的过程中根据mCurrentState的值进行ui的刷新
                        refreshUI();
                    }
                    return true;//代表消费了这个事件
                    //如果是最后一条
                }
                //上拉加载更多
                if (getLastVisiblePosition() == this.getCount()-1 && dY < 0) {
                    //int paddingFooter = (int) (dY + footerViewHeight);
                    //懒得写其他逻辑,直接就出来
                    isLoadingMore = true;
                    footerView.setPadding(0,0,0,0);
                } else {
                    isLoadingMore = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                //处于下拉刷新的状态,将头布局完全隐藏起来
                if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    headerView.setPadding(0, -headerHeight, 0, 0);
                } else if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    headerView.setPadding(0, 0, 0, 0);
                    mCurrentState = STATE_REFRESHING;
                    refreshUI();
                    //通知父控件LiseView刷新数据
                    notifyRefresh();
                }
                if (isLoadingMore) {
                    //调用上拉加载更多的方法
                    notifyLoadMore();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //接口,父类必须重写这里面的方法


    //###############################################################

    public interface OnRefreshListener {
        public void onRefresh();    //刷新数据

        public void onLoadMore();   //正在加载更多
    }

    //观察者设计模式的本质其实就是对象的引用传递
    private OnRefreshListener onRefreshListener;

    /**
     * onRefresh:下拉刷新的时候,这个给ListView刷新数据
     * onLoadMore:上拉加载的时候,如果有就加载更多
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    /**
     * 写下拉刷新数据的方法
     */
    private void notifyRefresh() {
        if (onRefreshListener != null && !isRefresh) {
            onRefreshListener.onRefresh();
            isRefresh = true;
        }
    }

    /**
     * 写上拉加载更多的方法,如果没有数据了,需要自己写toast提示没数据.
     * 然后调用隐藏脚布局的方法:
     */
    private void notifyLoadMore() {
        if (onRefreshListener != null) {
            onRefreshListener.onLoadMore();
        }
    }

    //#####################################################################

    /**
     * 隐藏头布局
     *
     * @param refreshSuccess 是否刷新成功,用来判断是否更新时间
     */
    public void hideHeaderView(boolean refreshSuccess) {
        headerView.setPadding(0, -headerHeight, 0, 0);
        //必须要在加载下一页数据结束的时候，改变isLoadingMore的值
        isRefresh = false;
        //刷新tv_date显示时间
        setCurrentTime(refreshSuccess);
    }

    /**
     * 隐藏脚布局,并且自己写加载成功,失败,网络访问失败 的toast
     */
    public void hideFooterView(){
        isLoadingMore = false;
        setSelection(getCount() - 1);//手动的让ListView滑动到最后一个条目
        footerView.setPadding(0,-footerViewHeight,0,0);
    }

    //如果更新成功就刷新时间
    private void setCurrentTime(boolean refreshSuccess) {
        if (refreshSuccess) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String nowTime = simpleDateFormat.format(new Date());
            tv_date.setText(nowTime);
            //持久化到本地
            SharedPreferencesUtils.putString(getContext(),GlobalConstants.LAST_UPDATE_TIME,nowTime);

        }
    }

    //根据状态的改变而改变头item的动画
    private void refreshUI() {
        switch (mCurrentState) {
            //下拉刷新
            case STATE_PULL_TO_REFRESH:
                iv_arrow.setVisibility(VISIBLE);
                pb_progress.setVisibility(INVISIBLE);
                iv_arrow.startAnimation(downAnimation);
                tv_status.setText("下拉刷新");
                break;
            //松开刷新
            case STATE_RELEASE_TO_REFRESH:
                iv_arrow.setVisibility(VISIBLE);
                pb_progress.setVisibility(INVISIBLE);
                iv_arrow.startAnimation(updateAnimation);
                tv_status.setText("松开刷新");
                break;
            //正在刷新
            case STATE_REFRESHING:
                iv_arrow.clearAnimation();
                // TODO: 2017/2/27 //注意，如果将一个控件的可见度设置不可见的情况下，要确保，这个控件中已经没有了动画对象
                iv_arrow.setVisibility(INVISIBLE);
                pb_progress.setVisibility(VISIBLE);
                tv_status.setText("正在刷新");
                break;
        }
    }
}