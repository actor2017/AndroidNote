package com.itheima.lifecycle;

import com.itheima.lifecycle.MyService.MiShu;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends Activity {
	private MiShu	ms;

	private Intent	intent;
	private MyConn	mConn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 1.start开启服务
	 */
	public void start(View v){
		intent = new Intent(this,MyService.class);
		startService(intent);
	}
	
	/**
	 * 2.bind绑定服务
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
		//如果在service的onBind方法返回Null,则这个方法不会被调用
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("activity和service连接成功了");
			ms = (MiShu)service;
		}
	}
	
	/**
	 * 调用服务里的方法
	 */
	public void diao(View v){
		ms.callMethodInService();
	}
	
	/**
	 * 3.解绑服务
	 */
	public void unbind(View view){
		unbindService(mConn);
	}
	
	/**
	 * 4.停止服务
	 */
	public void stop(View v	){
		stopService(intent);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//activity销毁前解绑服务
		unbindService(mConn);
	}
	
}
