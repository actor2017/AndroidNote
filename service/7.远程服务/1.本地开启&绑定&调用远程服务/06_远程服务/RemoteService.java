package com.itheima.remote;

import com.itheima.remote.IRemoteService.Stub;//genĿ¼��

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
	 * Զ�̷����е��ڲ���
	 */
	class YangMiShu extends Stub{
		public void qianYangNiu(){
			methodInRemoteService();
		}
	}
	
	/**
	 * Զ�̷�����ķ���
	 */
	public void methodInRemoteService(){
		System.out.println("�����ˣ������Ķ���American�������м����һ��̫ƽ��");
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
