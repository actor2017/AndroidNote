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
	 * 1.start��������
	 */
	public void start(View v){
		intent = new Intent(this,MyService.class);
		startService(intent);
	}
	
	/**
	 * 2.bind�󶨷���
	 */
	public void bind(View view){
		Intent service = new Intent(this,MyService.class);
		/*
		 * service	����ͼ����
		 * conn		��activity�ͷ��������ͨ��
		 * flags	���󶨷���ʱ��������񲻴��ڣ��ʹ���
		 * 	BIND_AUTO_CREATE			������ھ͸���
		 */
		mConn = new MyConn();
		bindService(service, mConn, BIND_AUTO_CREATE);
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
		//�����service��onBind��������Null,������������ᱻ����
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("activity��service���ӳɹ���");
			ms = (MiShu)service;
		}
	}
	
	/**
	 * ���÷�����ķ���
	 */
	public void diao(View v){
		ms.callMethodInService();
	}
	
	/**
	 * 3.������
	 */
	public void unbind(View view){
		unbindService(mConn);
	}
	
	/**
	 * 4.ֹͣ����
	 */
	public void stop(View v	){
		stopService(intent);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//activity����ǰ������
		unbindService(mConn);
	}
	
}
