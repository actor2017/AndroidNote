package com.itheima.lifecycle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private Intent	intent;
	private MyConn	mConn;
	//private ChengMiShu	mService;
	private IMyService	mService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * start开启服务
	 */
	public void kaiShi(View v){		//kaiShi:按钮
		intent = new Intent(this,MyService.class);
		startService(intent);
	}
	/**
	 * 停止服务
	 */
	public void tingZhi(View v	){
		stopService(intent);
	}
	/**
	 * bind绑定服务
	 */
	public void bangDing(View view){
		Intent service = new Intent(this,MyService.class);
		/*
		 * service	：意图对象
		 * conn		：activity和服务的连接通道
		 * flags	：绑定服务时，如果服务不存在，就创建
		 * 	BIND_AUTO_CREATE			如果存在就复用
		 */
		mConn = new MyConn();
		Log.i(TAG, "1. 绑定服务");
		bindService(service, mConn, BIND_AUTO_CREATE);//绑定服务
	}
	
	/**
	 * activity和service的连接通道
	 */
	private class MyConn implements ServiceConnection {
		//失去连接后调用
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		//连接成功后调用
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "4. activity和service的连接通道");
			Log.i(TAG, "5. 把IBinder强转成服务里的内部类");

			//把服务强转成成秘书类型
			mService = (ChengMiShu)service;

			/**
			 * 调用服务里的方法
			 */
			diaoYong();
		}

		public void diaoYong(){
			//四大组件不能被new对象
			//MyService mService = new  MyService();
			//mService.methodInService();
			Log.i(TAG, "6. 通过服务里的内部类对象间接调用服务里的方法");
			/**
			 * 只能调用接空中暴露的方法
			 */
			mService.qianShouMM(100000);
			mService.打麻将();
		}
	}
	
	/**
	 * 解绑服务
	 */
	public void jieBang(View view){
		unbindService(mConn);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//activity销毁前解绑服务
		unbindService(mConn);
	}
	
}
