package com.itheima.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private static final String TAG = "MyService";
	/**
	 * 1.��������ʱ����
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate");
	}
	/**
	 * 2.���񱻰󶨺����
	 */
	@Override
	public IBinder onBind(Intent intent) {
		//���񱻰󶨳ɹ������
		Log.e(TAG, "2. ���񱻰󶨳ɹ�onBind");
		Log.e(TAG, "3. ���ط�������ڲ������");
		return new ChengMiShu();
		//return null;
	}

	/**
	 * ��������ڲ��� �ڲ���Ա С��
	 */
	public class ChengMiShu extends Binder implements IMyService {
		public void qianShouMM(int money){
			if (money > 1000) {
				methodInService();
			}else {
				Toast.makeText(MyService.this, "ûǮ������ϱ������ȥд��������", 0).show();	
			}
		}

		@Override
		public void ���齫() {
			Toast.makeText(MyService.this, "Ҫ�����ˣ�����", 0).show();
		}

		private void ϴɣ��() {
			Toast.makeText(MyService.this, "ϴɣ�ð���Ү������", 0).show();
		}
	}

	/**
	 * ������ķ���
	 */
	public void methodInService(){
		Toast.makeText(this, "�����ˣ������Ķ���", 0).show();
	}

	/**
	 * 3.���񱻽��ǰ����
	 */
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
	/**
	 * ��������ǰ����
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
	/**
	 * ��������ʱ����onCreate,onStartCommand����
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

}
