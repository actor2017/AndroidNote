package com.itheima.mobileplayer.ui.activity;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class VideoPlayerActivity extends BaseActivity {

    private View                     llTop;//系统控制栏
    private View                     llBottom;//底部栏

    private static final int         MSG_AUTO_HIDE_CTRL     = 2;
    private GestureDetector          mGestureDetectorsture;

    @Override
    public void initView() {
        llTop = findViewById(R.id.ll_top);//顶部栏
        llBottom = findViewById(R.id.ll_bottom);//底部栏
		mGestureDetectorsture = new GestureDetector(new SimpleOnGestureListener());
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_AUTO_HIDE_CTRL://隐藏上/下面的控制面板
                    hideCtrl();
                    break;
            }
        }
    };

    private float startY;//按下的坐标
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetectorsture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class SimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        //单击事件
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            switchCtrl();
            return super.onSingleTapConfirmed(e);
        }
    }

    private boolean isShowing = true;
    private void switchCtrl() {
        if (isShowing) {
            hideCtrl();//隐藏控制面板
        } else {
            showCtrl();//显示控制面板
        }
    }

    private void showCtrl() {
        ViewPropertyAnimator.animate(llTop).translationY(0);
        ViewPropertyAnimator.animate(llBottom).translationY(0);
        isShowing = true;
        mHandler.sendEmptyMessageDelayed(MSG_AUTO_HIDE_CTRL, 5000);//延迟5秒发送隐藏控制面板的消息
    }

    private void hideCtrl() {
        ViewPropertyAnimator.animate(llTop).translationY(-llTop.getHeight());//隐藏顶部栏
        ViewPropertyAnimator.animate(llBottom).translationY(llBottom.getHeight());//隐藏底部栏
        isShowing = false;
    }
}
