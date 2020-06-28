package com.example.plasma;  
import android.app.Activity;  
import android.content.Context;  
import android.content.Intent;  
import android.os.Bundle;  
import android.util.AttributeSet;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.Window;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Canvas;  
import android.graphics.Color;  
/**   
* http://bbs.mobiletrain.org/thread-61947-1-1.html
* @Title: AnimActivity.java  
* @Package com.example.plasma  
* @Description: 安卓水波纹效果制作学习  
* @author XiaoMa  
*   
*   
*/
public class AnimActivity extends Activity {  
    @Override
    public void onCreate(Bundle savedInstanceState){  
        super.onCreate(savedInstanceState);  
        setContentView(new PlasmaView(this));  
//      this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    }  
      
      
}  
class PlasmaView extends View implements View.OnTouchListener{   
    private Bitmap mBitmap;  
    long time;  
    long fps;  
    public PlasmaView(Context context) {  
        super(context);         
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(),R.drawable.lvzi);  
        mBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);  
        AnimRender.setBitmap(bmp);  
        this.setOnTouchListener(this);  
    }  
    @Override   
    protected void onDraw(Canvas canvas) {  
        long ct = System.currentTimeMillis();  
        if(ct - time > 1000){  
            time = ct;  
            fps = 0;  
        }  
        fps++;        
         
        AnimRender.render(mBitmap);      
        canvas.drawBitmap(mBitmap, 0, 0, null);  
        postInvalidate();  
    }  
    @Override
    public boolean onTouch(View v, MotionEvent event) {  
        AnimRender.drop((int)event.getX(), (int)event.getY(), 500);  
        return false;  
    }  
}  
class AnimRender{  
    public static native void setBitmap(Bitmap src);  
    public static native void render(Bitmap dst);  
    public static native void drop(int x, int y, int height);  
     
    static {  
        System.loadLibrary("plasma");  
    }  
}  