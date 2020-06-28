package com.example.sms;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.database.ContentObserver;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//ע��������ݿ�仯�Ĺ۲���
		getContentResolver().registerContentObserver(
				Uri.parse("content://sms/"), 
				true, //�ݸ���,������,�ѷ���,�����3��
				new SmsObserver(new Handler()));
	}
	
	/**
	 * ���Ź۲���
	 */
	private class SmsObserver extends ContentObserver {
		private SmsObserver(Handler handler) {
			super(handler);
		}
		/**
		 * �Ͱ汾��ʹ��
		 */
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
		}
		 /**
		 * �߰汾��ʹ��
		 * ���������ݿ�仯��ص�
		 */
		
		@Override
		public void onChange(boolean selfChange, Uri uri) {
			super.onChange(selfChange, uri);
			System.out.println("������˶������ݿ⣡");//�߰汾���ӡ4��
		}
	}

}
