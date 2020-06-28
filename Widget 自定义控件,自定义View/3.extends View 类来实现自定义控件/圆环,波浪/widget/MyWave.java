package cn.itcast.wave12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Kevin.
 */

public class MyWave extends View {

    //波浪集合
    private ArrayList<Wave> mWaveList = new ArrayList<>();

    //颜色集合
    private int[] mColors = new int[]{Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE};

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            flushData();

            invalidate();//重绘

            if (!mWaveList.isEmpty()) {
                //集合不会空, 继续发消息循环
                mHandler.sendEmptyMessageDelayed(0, 50);
            }
        }
    };

    public MyWave(Context context) {
        super(context);
    }

    public MyWave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //产生一个波浪
                addPoint((int) event.getX(), (int) event.getY());
                break;
        }
        return true;
    }

    //遍历集合,刷新每一个波浪对象
    private void flushData() {
        ArrayList<Wave> killedList = new ArrayList<>();

        for (Wave wave : mWaveList) {
            wave.radius += 8;//半径变大
            Paint paint = wave.paint;
            paint.setStrokeWidth(wave.radius / 3);//宽度变大

            //颜色变浅
            int alpha = paint.getAlpha();

            if (alpha <= 0) {
                //圆环已经消失, 需要从集合中移除
//                mWaveList.remove(wave);
                killedList.add(wave);
                continue;//循环继续
            }

            alpha -= 10;
            if (alpha < 0) {
                alpha = 0;
            }

            paint.setAlpha(alpha);
        }

        //移除待删除的集合
        mWaveList.removeAll(killedList);
    }

    //添加一个点
    private void addPoint(int cx, int cy) {
        if (mWaveList.isEmpty()) {
            addWave(cx, cy);
            //第一次滑动,启动动画循环
            mHandler.sendEmptyMessageDelayed(0, 50);
        } else {
            //让两个圆环有一定的距离, 避免离得太近
            Wave lastWave = mWaveList.get(mWaveList.size() - 1);//当前集合的最后一个圆

            if (Math.abs(lastWave.cx - cx) > 10 || Math.abs(lastWave.cy - cy) > 10) {
                addWave(cx, cy);
            }
        }
    }

    //添加一个圆环对象
    private void addWave(int cx, int cy) {
        Wave wave = new Wave();
        wave.cx = cx;
        wave.cy = cy;
        wave.radius = 0;

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);//空心圆
        paint.setStrokeWidth(wave.radius / 3);//设置圆环线条粗细
        paint.setAntiAlias(true);//光滑的线条, 去掉锯齿
        paint.setAlpha(255);//[0..255], 255代表完全不透明; 0代表完全透明

        //随机颜色
        //Random random =new Rando();
        //Math.random();//[0-1)的小数
        int i = (int) (Math.random() * mColors.length);//0.1.2.3
        paint.setColor(mColors[i]);

        wave.paint = paint;

        mWaveList.add(wave);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Wave wave : mWaveList) {
            canvas.drawCircle(wave.cx, wave.cy, wave.radius, wave.paint);
        }
    }

    //一个波浪对象
    class Wave {
        int cx;
        int cy;
        int radius;
        Paint paint;
    }
}
