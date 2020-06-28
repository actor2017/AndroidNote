package cn.itcast.viewpager12;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Kevin.
 */

public class MyViewPager extends ViewGroup {

    private GestureDetector mDetector;
    private Scroller mScroller;
    private int startX;
    private int startY;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            //滑动监听
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                    distanceY) {
                //滑动当前页面
                //System.out.println("distanceX:" + distanceX);
                //基于当前位置,进行滑动, 参1:x偏移;参2:y偏移;相对位置
                scrollBy((int) distanceX, 0);

                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

        //滑动器, 可以实现平滑滑动效果
        mScroller = new Scroller(getContext());
    }

    //测量控件大小
    //重写此方法原因: 添加测试页面后, 由于测试页有很多子控件没有测量,导致无法绘制, 所以此方法要手动测量一遍
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //此方法用于测量当前ViewPager的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //遍历所有子控件,测量每个控件
        for (int i = 0; i < getChildCount(); i++) {
            //每个子控件的所有孩子都可以通过此方法进行测量
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        //不是纯粹的宽高信息, 而是同时带有宽高模式:1073742304-->1000000000000000000000111100000
        System.out.println("widthMeasureSpec:" + widthMeasureSpec);//1073742304
        System.out.println("heightMeasureSpec:" + heightMeasureSpec);//1073742511

        //宽高模式:
        //MeasureSpec.EXACTLY; 确定模式, 类似布局文件中把宽高写死, match_parent
        //MeasureSpec.AT_MOST: 至多模式, 类似wrap_content
        //MeasureSpec.UNSPECIFIED; 未指定模式, 宽高没有确定, ListView, ScrollView
        int width = MeasureSpec.getSize(widthMeasureSpec);//宽度
        int mode = MeasureSpec.getMode(widthMeasureSpec);//模式

        System.out.println("width:" + width);//480
        System.out.println("mode:" + mode);//1073741824-->1000000000000000000000000000000
    }

    //设置子控件的位置信息
    //必须重写此方法确定子控件位置, 否则无法展示子控件, 是个白板
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历所有子控件,设置位置, 一字排开
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());//l,t,r,b
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //手指抬起
                //确定下一页位置
                int scrollX = getScrollX();//获取当前滑动结束后的位置

//                 System.out.println("scrollX:" + scrollX);
                int position = scrollX / getWidth();

                int offset = scrollX % getWidth();
                if (offset > getWidth() / 2) {
                    //跳到下一个页面
                    position++;
                }

                //避免越界,否则会滑到白板
                if (position > getChildCount() - 1) {
                    position = getChildCount() - 1;
                }

                //避免越界
                if (position < 0) {
                    position = 0;
                }

                System.out.println("position:" + position);

                setCurrentItem(position);
                break;

            default:
                break;
        }
//        return super.onTouchEvent(event);//这样不能滑动
        return true;
    }

    //事件分发的方法
    //dispatchTouchEvent-->onInterceptTouchEvent-->onTouchEvent
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    //是否中断事件传递
    //按下---移动---抬起
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();

                //按下的事件被ScrollView消耗掉了, 导致如果左右滑动时, ViewPager丢掉了按下的事件, 导致滑动出现异常
                //解决办法: 给手势识别器把这个事件补上
                mDetector.onTouchEvent(ev);//补上按下的事件,否则滑动会跳动
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int dx = moveX - startX;
                int dy = moveY - startY;
                if (Math.abs(dx) > Math.abs(dy)) {//左右滑动
                    return true;//表示要中断事件传递, 从而viewpager的子控件(ScrollView)就拿不到事件
                }
                break;
        }
        return false;//不中断事件, 交由子控件(ScrollView)优先处理
    }

    //跳到特定页面
    public void setCurrentItem(int position) {
        //跳到下一个页面
        //scrollTo(getWidth() * position, 0);//跳到特定的位置, 绝对位置
        //滑动偏移量 = 目标位置 - 当前位置
        int dx = getWidth() * position - getScrollX();
        //开始滑动; 参1:起始x位置; 参2:起始y位置; 参3:x偏移; 参4:y偏移; 参5:滑动时间
        //为了保证匀速, 时间要和距离成正比
        //此方法不会立即启动动画, 而是会导致回调computeScroll方法, 需要在computeScroll中处理动画
        mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx));
        invalidate();//为了保证正常运行, 需要此处刷新

        if(mListener!=null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//判断滑动是否结束
            //没有结束
            int currX = mScroller.getCurrX();//获取当前滑动器应该滑动的位置
            System.out.println("currX:" + currX);

            scrollTo(currX, 0);//滑动到该位置
            invalidate();//为了保证正常运行, 需要此处刷新
        }
    }

    private OnPageChangeListener mListener;

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    public interface OnPageChangeListener {
        void onPageSelected(int position);
    }
}
