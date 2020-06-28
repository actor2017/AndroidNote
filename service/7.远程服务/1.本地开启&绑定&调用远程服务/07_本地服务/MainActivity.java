package com.itheima.local;

import com.itheima.remote.IRemoteService;
import com.itheima.remote.IRemoteService.Stub;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends Activity {
	private IRemoteService	mService;
	private Intent	intent;
	private MyConn	mConn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		intent = new Intent();
		intent.setAction("dai.ma.nv.wo.qian.bai.bian");
	}

	/**
	 * 开启06工程里的服务组件
	 */
	public void start(View v){
		startService(intent);
	}
	/**
	 * 停止远程服务组件
	 */
	public void stop(View vc){
		stopService(intent);
	}
	/**
	 * 绑定06工程里的服务组件
	 */
	public void bind(View v){
		mConn = new MyConn();
		bindService(intent,mConn , BIND_AUTO_CREATE);
	}
	
	private class MyConn implements ServiceConnection {

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//06远程应用中的接口类型
			mService = Stub.asInterface(service);
		}
	}
	/**
	 * 调用远程服务里的方法
	 */
	public void diao(View v){
		try {
			mService.qianYangNiu();//牵洋妞
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解绑远程服务
	 */
	public void unbind(View v){
		unbindService(mConn);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mConn);
	}
}
