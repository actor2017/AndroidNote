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
		//��ʾԭͼ
		bmp = BitmapFactory.decodeFile("/mnt/sdcard/mm.jpg");
		iv.setImageBitmap(bmp);
	}

	public void show(View v){
		//��ȡͼƬ�Ŀ��
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width*height];
		//��ȡͼƬ�е�����������Ϣ
		/*
		 * pixels	��int[]����������ͼƬ�������ص�
		 * offset	����ȡ��ɫ����ʼλ�ã�0ͼƬ�����Ͻ�
		 * stride	����ȡͼƬÿ�е�����
		 * x		��x�����ʼ������
		 * y		��y�����ʼ������
		 * width	��ͼƬ�Ŀ�
		 * height	��ͼƬ�ĸ߶�
		 */
		//�������ִ����ϣ�pixels��������ʹ�����ͼƬ��������ɫ��Ϣ
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		//���ñ��ط���������Ч
		JNI jni = new JNI();
//		jni.StyleLomoHDR(pixels, width, height);
		jni.StyleLomoB(pixels, width, height);
		//���޸ĵ���ɫ��������bmpλͼ
		Bitmap editBmp = Bitmap.createBitmap(pixels, width, height, bmp.getConfig());
		//��Imageview�����޸ĺ��λͼ
		iv.setImageBitmap(editBmp);
	}
	
	
}
