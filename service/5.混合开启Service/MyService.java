package com.itheima.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	private static final String TAG = "MyService";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate");
	}
	/**
	 * 服务被绑定后调用
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.e(TAG, "onBind");
		return new MiShu();
	}
	
	public class MiShu extends Binder{
		public void callMethodInService(){
			menthodInService();
		}
	}
	
	public void menthodInService(){
		Toast.makeText(this, "我是服务里的方法", 0).show();
	}
	
	/**
	 * 服务被解绑前调用
	 */
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

}
