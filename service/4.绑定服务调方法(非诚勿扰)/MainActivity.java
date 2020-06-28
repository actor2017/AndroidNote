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
		
		//activity��Service���ӳɹ������
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "4. activity��service������ͨ��");
			Log.i(TAG, "5. ��IBinderǿת�ɷ�������ڲ���");
//			mService = (ChengMiShu)service;
			 mService = (IMyService)service;
		}
	}
	/**
	 * ���÷�����ķ���
	 */
	public void qianshou(View v){
		//�Ĵ�������ܱ�new����
//		MyService mService = new  MyService();
//		mService.methodInService();
		Log.i(TAG, "6. ͨ����������ڲ�������ӵ��÷�����ķ���");
//		mService.qianShouMM(100000);
		mService.���齫();
//		mService.ϴɣ��();//private,ֻ����boss
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mConn);
	}
}
