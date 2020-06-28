package com.itheima.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneService extends Service {
	private TelephonyManager	tm;
	private MyPhoneStateListener	mPhoneStateListener;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		
		// 1. ��ȡ�ֻ��绰������
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// 2. ���ü���
		mPhoneStateListener = new MyPhoneStateListener();
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	/**
	 * 3. �ڲ���ʵ�ּ���
	 */
	private class MyPhoneStateListener extends PhoneStateListener {
		//�ֻ��ĺ���װ��仯�ص�
		//incomingNumber:�����ֻ�����
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);

			System.out.println("������룺"+incomingNumber);
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE://����
					System.out.println("����");
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK://ͨ��
					System.out.println("ͨ��");
					break;
				case TelephonyManager.CALL_STATE_RINGING://����
					System.out.println("������");
					break;

				default:
					break;
			}
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//4. ��������ǰע������
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
}
