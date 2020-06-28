package cn.itcast.wave12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kevin.
 */

public class MyRing extends View {

    private Paint paint;

    public MyRing(Context context) {
        this(context, null);
    }

    public MyRing(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);//实心
        paint.setStyle(Paint.Style.STROKE);//空心
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);//??
        paint.setStrokeWidth(20);//设置圆环线条粗细
        paint.setAntiAlias(true);//光滑的线条, 去掉锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制一个圆; 参1,2:圆环的圆心坐标; 参3:半径
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 100, paint);
    }
}
