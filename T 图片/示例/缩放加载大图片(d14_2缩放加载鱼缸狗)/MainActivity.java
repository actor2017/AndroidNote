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
	 * 1. ��ȡͼƬ�Ŀ��
	 *  2. ��ȡ�ֻ���Ļ�Ŀ��
	 *   3. ������ʵı���ֵ 
	 *   4. �������ű���ֵ��ʾͼƬ
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//=================================================
		ImageView iv = (ImageView) findViewById(R.id.iv);
		
		//opt:��װͼƬ��Ϣ
		Options opt = new Options();
		
		//=true:ֻ��ȡ���,������ͼƬIf set to true, the decoder will 
		//return null (no bitmap), but the out... fields will 
		//still be set, allowing the caller to query the bitmap
		//without having to allocate the memory for its pixels.
		opt.inJustDecodeBounds = true;
		
		// ���ò�����ֻ��ͼƬ�Ŀ�ߣ�������λͼ����(�ڶ�������opt)
		BitmapFactory.decodeFile("/mnt/sdcard/dog.jpg", opt);
		// 1. ��ȡͼƬ�Ŀ��
		int pHeight = opt.outHeight;
		int pWidth = opt.outWidth;
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		//2. ��ȡ�ֻ���Ļ�Ŀ�� 
		int sHeight = display.getHeight();
		int sWidth = display.getWidth();
		
		//3. ������ʵı���ֵ
		int width = pWidth / sWidth;
		int heith = pHeight /sHeight;
		int scale = 1;
		if (width >= heith && width>=1) {
			scale = width;
		}else if (heith > width && heith>=1) {
			scale = heith;
		}//else:���ͼƬ��߱�����<1,��ͼƬ����1:1�ı���ԭͼ��ʾ���ֻ���
		//4. �������ű���ֵ��ʾͼƬ
		opt.inSampleSize = scale;
		opt.inJustDecodeBounds = false;//����λͼ����
		Bitmap bmp = BitmapFactory.decodeFile("/mnt/sdcard/dog.jpg", opt);
		iv.setImageBitmap(bmp);
	}

}
