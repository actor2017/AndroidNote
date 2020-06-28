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
	 * start��������
	 */
	public void kaiShi(View v){		//kaiShi:��ť
		intent = new Intent(this,MyService.class);
		startService(intent);
	}
	/**
	 * ֹͣ����
	 */
	public void tingZhi(View v	){
		stopService(intent);
	}
	/**
	 * bind�󶨷���
	 */
	public void bangDing(View view){
		Intent service = new Intent(this,MyService.class);
		/*
		 * service	����ͼ����
		 * conn		��activity�ͷ��������ͨ��
		 * flags	���󶨷���ʱ��������񲻴��ڣ��ʹ���
		 * 	BIND_AUTO_CREATE			������ھ͸���
		 */
		mConn = new MyConn();
		Log.i(TAG, "1. �󶨷���");
		bindService(service, mConn, BIND_AUTO_CREATE);//�󶨷���
	}
	
	/**
	 * activity��service������ͨ��
	 */
	private class MyConn implements ServiceConnection {
		//ʧȥ���Ӻ����
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		//���ӳɹ������
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "4. activity��service������ͨ��");
			Log.i(TAG, "5. ��IBinderǿת�ɷ�������ڲ���");

			//�ѷ���ǿת�ɳ���������
			mService = (ChengMiShu)service;

			/**
			 * ���÷�����ķ���
			 */
			diaoYong();
		}

		public void diaoYong(){
			//�Ĵ�������ܱ�new����
			//MyService mService = new  MyService();
			//mService.methodInService();
			Log.i(TAG, "6. ͨ����������ڲ�������ӵ��÷�����ķ���");
			/**
			 * ֻ�ܵ��ýӿ��б�¶�ķ���
			 */
			mService.qianShouMM(100000);
			mService.���齫();
		}
	}
	
	/**
	 * ������
	 */
	public void jieBang(View view){
		unbindService(mConn);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//activity����ǰ������
		unbindService(mConn);
	}
	
}
