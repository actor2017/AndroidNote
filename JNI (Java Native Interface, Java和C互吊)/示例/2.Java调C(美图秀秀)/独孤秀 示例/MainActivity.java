package com.itheima.dugu_show;

import com.mt.mtxx.image.JNI;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView	iv;
	private Bitmap	bmp;

	static{
		System.loadLibrary("mtimage-jni");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.iv);
		//显示原图
		bmp = BitmapFactory.decodeFile("/mnt/sdcard/mm.jpg");
		iv.setImageBitmap(bmp);
	}

	public void show(View v){
		//获取图片的宽高
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width*height];
		//获取图片中的所有像素信息
		/*
		 * pixels	：int[]数组用来放图片所有像素的
		 * offset	：获取颜色的起始位置，0图片的左上角
		 * stride	：获取图片每行的像素
		 * x		：x轴的起始点坐标
		 * y		：y轴的起始点坐标
		 * width	：图片的宽
		 * height	：图片的高度
		 */
		//这个方法执行完毕，pixels数组里面就存满了图片的所有颜色信息
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		//调用本地方法设置特效
		JNI jni = new JNI();
//		jni.StyleLomoHDR(pixels, width, height);
		jni.StyleLomoB(pixels, width, height);
		//把修改的颜色数组生成bmp位图
		Bitmap editBmp = Bitmap.createBitmap(pixels, width, height, bmp.getConfig());
		//给Imageview设置修改后的位图
		iv.setImageBitmap(editBmp);
	}
	
	
}
