package com.itheima.call_method;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	private MyConn	mConn;
	private IMyService	mService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void bind(View v) {
		Intent service = new Intent(this, MyService.class);
		mConn = new MyConn();
		bindService(service, mConn, BIND_AUTO_CREATE);
	}

	private class MyConn implements ServiceConnection {

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		
		//activity和Service连接成功后调用
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "4. activity和service的连接通道");
			Log.i(TAG, "5. 把IBinder强转成服务里的内部类");
//			mService = (ChengMiShu)service;
			 mService = (IMyService)service;
		}
	}
	/**
	 * 调用服务里的方法
	 */
	public void qianshou(View v){
		//四大组件不能被new对象
//		MyService mService = new  MyService();
//		mService.methodInService();
		Log.i(TAG, "6. 通过服务里的内部类对象间接调用服务里的方法");
//		mService.qianShouMM(100000);
		mService.打麻将();
//		mService.洗桑拿();//private,只能陪boss
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mConn);
	}
}
