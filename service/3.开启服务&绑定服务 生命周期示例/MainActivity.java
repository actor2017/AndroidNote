package com.itheima.lifecycle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends Activity {

	private Intent	intent;
	private MyConn	mConn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * start开启服务
	 */
	public void start(View v){
		intent = new Intent(this,MyService.class);
		startService(intent);
	}
	/**
	 * 停止服务
	 */
	public void stop(View v	){
		stopService(intent);
	}
	/**
	 * bind绑定服务
	 */
	public void bind(View view){
		Intent service = new Intent(this,MyService.class);
		/*
		 * service	：意图对象
		 * conn		：activity和服务的连接通道
		 * flags	：绑定服务时，如果服务不存在，就创建
		 * 	BIND_AUTO_CREATE			如果存在就复用
		 */
		mConn = new MyConn();
		bindService(service, mConn, BIND_AUTO_CREATE);
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
		}
	}
	
	/**
	 * 解绑服务,这是一个点击事件
	 */
	@OnClick
	public void unbind(View view){
		unbindService(mConn);//不能多次解绑
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//activity销毁前解绑服务
		unbindService(mConn);//不能多次解绑
	}
	
}
