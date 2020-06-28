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

/**
 * Created by Kevin.
 */

public class SimpleWave extends View {

    private Paint paint;

    private int cx;
    private int cy;
    private int radius;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //1. 半径变大
            //2. 宽度变大
            //3. 颜色变浅
            radius += 5;                    //半径每次都增大5dp
            paint.setStrokeWidth(radius / 3);//圆环宽度
            int alpha = paint.getAlpha();//当前透明度
            alpha -= 5;                     //透明度每次减小5
            if (alpha < 0) {
                alpha = 0;
            }

            paint.setAlpha(alpha);
            //重绘
            invalidate();

            if (alpha > 0) {//圆环还没有完全消失
                //继续发送消息,形成内循环
                mHandler.sendEmptyMessageDelayed(0, 50);
            }
        }
    };

    public SimpleWave(Context context) {
        this(context, null);
    }

    public SimpleWave(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SimpleWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);//空心圆
        paint.setStrokeWidth(radius / 3);//设置圆环线条粗细
        paint.setAntiAlias(true);//光滑的线条, 去掉锯齿
        paint.setAlpha(255);//[0..255], 255代表完全不透明; 0代表完全透明
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重新初始化画笔和半径
                radius = 0;
                initView();

                cx = (int) event.getX();
                cy = (int) event.getY();

                mHandler.sendEmptyMessageDelayed(0, 50);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
    }
}
