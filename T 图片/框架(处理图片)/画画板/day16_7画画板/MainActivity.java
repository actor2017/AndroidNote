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
	 * 1. ��������ͼƬ�ĸ��� 
	 * 2. ���ô������� 
	 * 3. �ѿ�ʼ��ͽ�����������������
	 * 4. ���޸ĺ��ͼƬ��ʾ��Imageview��
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//=================================================
		iv = (ImageView) findViewById(R.id.iv);
		// 1. ��������ͼƬ�ĸ���
		Bitmap srcBmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg);
		//��
		paint = new Paint();
		
		//��ȡ�ֻ���Ļ�Ŀ�� 
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int sHeight = display.getHeight();
		int sWidth = display.getWidth();
		//ֽ
		copyBmp = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(),
				srcBmp.getConfig());//getConfig()����?
		//��
		canvas = new Canvas(copyBmp);
		//����
		canvas.drawBitmap(srcBmp, new Matrix(), paint);

		// 2. ���ô�������
		iv.setOnTouchListener(new OnTouchListener() {
			private int	startX;
			private int	startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:// ��ָ����
						startX = (int) event.getX();
						startY = (int) event.getY();
						break;
					case MotionEvent.ACTION_MOVE:// �ֻ��ƶ�
						int stopX = (int) event.getX();
						int stopY = (int) event.getY();
						// 3. �ѿ�ʼ��ͽ�����������������
						canvas.drawLine(startX, startY, stopX, stopY, paint);

						// 5. ������ʼ������
						startX = stopX;
						startY = stopY;
						break;
					default:
						break;
				}
				// 4. ���޸ĺ��ͼƬ��ʾ��Imageview��
				iv.setImageBitmap(copyBmp);
				return true;
			}
		});
	}

	/**
	 * �������ʵ���ɫ
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
	 * ����������ʴ�ϸ(1-9)
	 */
	public void cx(View v){
		int x = new Random().nextInt(9)+1;
		paint.setStrokeWidth(x);
		Toast.makeText(MainActivity.this, "�ʻ���ϸ:"+x, 0).show();
	}
	/**
	 * ����ͼƬ
	 */
	public void save(View v){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File("/mnt/sdcard/jiezuo.jpg"));
			copyBmp.compress(CompressFormat.JPEG, 100, fos);
			
			Toast.makeText(this, "��ʦ�����Ľ��������� /mnt/sdcard/jiezuo.jpg", 0).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(this, "��ʦ�����Ľ�������ʧ��¥  :( ", 0).show();
		}
	}
}
