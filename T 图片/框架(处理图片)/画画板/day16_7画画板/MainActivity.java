package com.itheima.paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Paint		paint;
	private Bitmap		copyBmp;
	private Canvas		canvas;
	private ImageView	iv;

	/**
	 * 
	 * 1. 创建背景图片的副本 
	 * 2. 设置触摸监听 
	 * 3. 把开始点和结束点用先连接起来
	 * 4. 把修改后的图片显示在Imageview上
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//=================================================
		iv = (ImageView) findViewById(R.id.iv);
		// 1. 创建背景图片的副本
		Bitmap srcBmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg);
		//笔
		paint = new Paint();
		
		//获取手机屏幕的宽高 
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int sHeight = display.getHeight();
		int sWidth = display.getWidth();
		//纸
		copyBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(),
				srcBmp.getConfig());//getConfig()配置?
		//板
		canvas = new Canvas(copyBmp);
		//作画
		canvas.drawBitmap(srcBmp, new Matrix(), paint);

		// 2. 设置触摸监听
		iv.setOnTouchListener(new OnTouchListener() {
			private int	startX;
			private int	startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:// 手指按下
						startX = (int) event.getX();
						startY = (int) event.getY();
						break;
					case MotionEvent.ACTION_MOVE:// 手机移动
						int stopX = (int) event.getX();
						int stopY = (int) event.getY();
						// 3. 把开始点和结束点用先连接起来
						canvas.drawLine(startX, startY, stopX, stopY, paint);

						// 5. 更新起始点坐标
						startX = stopX;
						startY = stopY;
						break;
					default:
						break;
				}
				// 4. 把修改后的图片显示在Imageview上
				iv.setImageBitmap(copyBmp);
				return true;
			}
		});
	}

	/**
	 * 调整画笔的颜色
	 */
	public void red(View v) {
		paint.setColor(Color.RED);
	}

	public void green(View v) {
		paint.setColor(Color.GREEN);
	}

	public void blue(View v) {
		paint.setColor(Color.BLUE);
	}
	/**
	 * 随机调整画笔粗细(1-9)
	 */
	public void cx(View v){
		int x = new Random().nextInt(9)+1;
		paint.setStrokeWidth(x);
		Toast.makeText(MainActivity.this, "笔画粗细:"+x, 0).show();
	}
	/**
	 * 保存图片
	 */
	public void save(View v){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("/mnt/sdcard/jiezuo.jpg"));
			copyBmp.compress(CompressFormat.JPEG, 100, fos);
			
			Toast.makeText(this, "大师，您的杰作保存在 /mnt/sdcard/jiezuo.jpg", 0).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(this, "大师，您的杰作保存失败楼  :( ", 0).show();
		}
	}
}
