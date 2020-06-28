package com.itheima.photo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {

	/**
	 * 1. 获取图片的宽高
	 *  2. 获取手机屏幕的宽高
	 *   3. 计算合适的比例值 
	 *   4. 设置缩放比例值显示图片
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//=================================================
		ImageView iv = (ImageView) findViewById(R.id.iv);
		
		//opt:封装图片信息
		Options opt = new Options();
		
		//=true:只获取宽高,不加载图片If set to true, the decoder will 
		//return null (no bitmap), but the out... fields will 
		//still be set, allowing the caller to query the bitmap
		//without having to allocate the memory for its pixels.
		opt.inJustDecodeBounds = true;
		
		// 设置参数，只拿图片的宽高，不加载位图对象(第二个参数opt)
		BitmapFactory.decodeFile("/mnt/sdcard/dog.jpg", opt);
		// 1. 获取图片的宽高
		int pHeight = opt.outHeight;
		int pWidth = opt.outWidth;
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		//2. 获取手机屏幕的宽高 
		int sHeight = display.getHeight();
		int sWidth = display.getWidth();
		
		//3. 计算合适的比例值
		int width = pWidth / sWidth;
		int heith = pHeight /sHeight;
		int scale = 1;
		if (width >= heith && width>=1) {
			scale = width;
		}else if (heith > width && heith>=1) {
			scale = heith;
		}//else:如果图片宽高比例都<1,则图片按照1:1的比例原图显示在手机上
		//4. 设置缩放比例值显示图片
		opt.inSampleSize = scale;
		opt.inJustDecodeBounds = false;//加载位图对象
		Bitmap bmp = BitmapFactory.decodeFile("/mnt/sdcard/dog.jpg", opt);
		iv.setImageBitmap(bmp);
	}

}
