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
		
		//注册短信数据库变化的观察者
		getContentResolver().registerContentObserver(
				Uri.parse("content://sms/"), 
				true, //草稿箱,发送箱,已发送,会监听3次
				new SmsObserver(new Handler()));
	}
	
	/**
	 * 短信观察者
	 */
	private class SmsObserver extends ContentObserver {
		private SmsObserver(Handler handler) {
			super(handler);
		}
		/**
		 * 低版本中使用
		 */
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
		}
		 /**
		 * 高版本中使用
		 * 当短信数据库变化后回调
		 */
		
		@Override
		public void onChange(boolean selfChange, Uri uri) {
			super.onChange(selfChange, uri);
			System.out.println("你操作了短信数据库！");//高版本会打印4次
		}
	}

}
