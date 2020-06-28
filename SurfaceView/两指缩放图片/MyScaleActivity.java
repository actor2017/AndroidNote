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
        //��ť����
        mButton.setOnClickListener(new View.OnClickListener() 
        {
            
            @Override
            public void onClick(View v) 
            {
                // TODO Auto-generated method stub
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mmm);
                //��������SurfaceView
                Canvas mCanvas = mSurfaceHolder.lockCanvas();
                //��ͼ
                mCanvas.drawBitmap(mBitmap, 0f, 0f, null);
                //������ɣ��ύ�޸�
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                //������һ��
                mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        });
        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {            
        //���ظ�ScaleGestureDetector������
        return mScaleGestureDetector.onTouchEvent(event);
    }
    
    
    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
    {

        @Override
        public boolean onScale(ScaleGestureDetector detector) 
        {
            // TODO Auto-generated method stub
            
            Matrix mMatrix = new Matrix();        
            //���ű���
            float scale = detector.getScaleFactor()/3;
            mMatrix.setScale(scale, scale);
            
            //��������SurfaceView
            Canvas mCanvas = mSurfaceHolder.lockCanvas();
            //����
            mCanvas.drawColor(Color.BLACK);
            //�����ź��ͼ
            mCanvas.drawBitmap(mBitmap, mMatrix, null);
            //������ɣ��ύ�޸�
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            //������һ��
            mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) 
        {
            // TODO Auto-generated method stub    
            //һ��Ҫ����true�Ż����onScale()�������
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) 
        {
            // TODO Auto-generated method stub
            
        }
        
    }
     
}