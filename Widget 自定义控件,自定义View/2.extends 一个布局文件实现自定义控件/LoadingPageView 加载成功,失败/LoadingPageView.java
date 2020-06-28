package cn.googleplay12.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import cn.googleplay12.R;
import cn.googleplay12.utils.UiUtils;

/**
 * Created by zhengping on 2017/4/2,14:39.
 * <p>
 * LoadingPageView 就是一个帧布局，只不过这个帧布局有一些状态
 * <p>
 * 1、加载中的状态   --> mLoadingView
 * 2、加载失败的状态---> mErrorView
 * 3、加载成功的状态--> mSuccessView
 * 4、加载成功，但是数据集合中的数量为0  -->mEmptyView
 */

public abstract class LoadingPageView extends FrameLayout {

    public static final int STATE_NONE = 0;     //用作初始化
    public static final int STATE_LOADING = 1;  //加载中
    public static final int STATE_ERROR = 2;    //加载失败
    public static final int STATE_SUCCESS = 3;  //加载成功
    public static final int STATE_EMPTY = 4;    //加载成功，但是数据集合中的数量为0

    private int mCurrentState = STATE_NONE;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public LoadingPageView(Context context) {
        this(context,null);
    }

    public LoadingPageView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public LoadingPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mLoadingView == null) {
            mLoadingView = View.inflate(getContext(), R.layout.layout_loading, null);
            addView(mLoadingView);
        }
        if (mErrorView == null) {
            mErrorView = View.inflate(getContext(), R.layout.layout_error, null);
            addView(mErrorView);

        }
        if (mEmptyView == null) {
            mEmptyView = View.inflate(getContext(), R.layout.layout_empty, null);
            addView(mEmptyView);
        }

        showRightView();
    }

    // 根据mCurrentState的值动态显示某一个View，其他的View对象隐藏起来
    //showRightView这个方法要在mCurrentState的值改变之后进行调用  初始化的时候也需要调用一下
    private void showRightView() {
        mLoadingView.setVisibility((mCurrentState == STATE_LOADING || mCurrentState == STATE_NONE) ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(mCurrentState == STATE_ERROR ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? View.VISIBLE : View.GONE);

        //成功布局的初始化
        if (mCurrentState == STATE_SUCCESS) {
            mSuccessView = initSuccessView();
            if (mSuccessView != null) {
                addView(mSuccessView);
                // mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? View.VISIBLE:View
                // .GONE);
            }

        }
        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? View.VISIBLE : View.GONE);
        }

    }

    /**
     * 显示加载中的状态(圆形进度条)
     */
    public void loadData() {

        if (mCurrentState == STATE_LOADING) {
            return;
        }

        mCurrentState = STATE_LOADING;
        showRightView();//mCurrentState的值一旦改变之后，咱们要让ui有最新状态的显示

        new Thread(new Runnable() {
            @Override
            public void run() {
                //真正的开始加载服务器的数据
                ResultState resultState = resultState();
                if(resultState != null) {
                    mCurrentState = resultState.state;
                }

                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showRightView();
                    }
                });

            }
        }).start();
    }

    /**
     * 返回一个枚举类型:请求后的状态,例:LoadingPageView.ResultState.SUCESS
     */
    public abstract ResultState resultState();

    //由于 LoadingPageView 无法确定每一个模块成功之后长什么样子，所以 LoadingPageView 无法完成这项工作，应该把这项工作转交出去

    /**
     * 网络数据加载成功后的布局
     */
    public abstract View initSuccessView();


    /**
     * 枚举--》class
     * 1、关键字变了class-->enum
     * 2、创建对象的写法简化
     * 3、枚举的构造方法必须私有化
     * 通过构造方法的私有化，达到对象的数量得到有效控制的效果
     * 通常我们会有枚举来代表一些返回值，这样显得更专业一些。
     */
    public enum ResultState {

        LOADING(STATE_LOADING), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY), SUCESS(STATE_SUCCESS);
        public int state;

        private ResultState(int state) {
            this.state = state;
        }
    }
}
