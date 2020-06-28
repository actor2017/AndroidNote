package com.nan.scale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;



public class MyScaleActivity extends Activity 
{
    private Button mButton = null;
    private SurfaceView mSurfaceView = null;
    private SurfaceHolder mSurfaceHolder = null;
    private ScaleGestureDetector mScaleGestureDetector = null;
    private Bitmap mBitmap = null;    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSurfaceView = (SurfaceView)this.findViewById(R.id.surfaceview);
        mSurfaceHolder = mSurfaceView.getHolder();  
        mScaleGestureDetector = new ScaleGestureDetector(this,new ScaleGestureListener());
        mButton = (Button)this.findViewById(R.id.button);
        //按钮监听
        mButton.setOnClickListener(new View.OnClickListener() 
        {
            
            @Override
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mmm);
                //锁定整个SurfaceView
                Canvas mCanvas = mSurfaceHolder.lockCanvas();
                //画图
                mCanvas.drawBitmap(mBitmap, 0f, 0f, null);
                //绘制完成，提交修改
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                //重新锁一次
                mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        });
        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {            
        //返回给ScaleGestureDetector来处理
        return mScaleGestureDetector.onTouchEvent(event);
    }
    
    
    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
    {

        @Override
        public boolean onScale(ScaleGestureDetector detector) 
        {
            // TODO Auto-generated method stub
            
            Matrix mMatrix = new Matrix();        
            //缩放比例
            float scale = detector.getScaleFactor()/3;
            mMatrix.setScale(scale, scale);
            
            //锁定整个SurfaceView
            Canvas mCanvas = mSurfaceHolder.lockCanvas();
            //清屏
            mCanvas.drawColor(Color.BLACK);
            //画缩放后的图
            mCanvas.drawBitmap(mBitmap, mMatrix, null);
            //绘制完成，提交修改
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            //重新锁一次
            mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) 
        {
            // TODO Auto-generated method stub    
            //一定要返回true才会进入onScale()这个函数
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) 
        {
            // TODO Auto-generated method stub
            
        }
        
    }
     
}