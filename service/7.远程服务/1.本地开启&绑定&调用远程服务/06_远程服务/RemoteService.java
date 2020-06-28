package com.itheima.remote;

import com.itheima.remote.IRemoteService.Stub;//gen目录下

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class RemoteService extends Service {

	private static final String	TAG	= "RemoteSevice";

	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "onBind");
		return new YangMiShu();
	}

	/**
	 * 远程服务中的内部类
	 */
	class YangMiShu extends Stub{
		public void qianYangNiu(){
			methodInRemoteService();
		}
	}
	
	/**
	 * 远程服务里的方法
	 */
	public void methodInRemoteService(){
		System.out.println("我来了，你在哪儿？American，我们中间隔着一个太平洋");
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
}
