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
		
		// 1. 获取手机电话管理器
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// 2. 设置监听
		mPhoneStateListener = new MyPhoneStateListener();
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	/**
	 * 3. 内部类实现监听
	 */
	private class MyPhoneStateListener extends PhoneStateListener {
		//手机的呼叫装填变化回到
		//incomingNumber:来电手机号码
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);

			System.out.println("来电号码："+incomingNumber);
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE://空闲
					System.out.println("空闲");
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK://通话
					System.out.println("通话");
					break;
				case TelephonyManager.CALL_STATE_RINGING://响铃
					System.out.println("来电了");
					break;

				default:
					break;
			}
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//4. 服务销毁前注销监听
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
}
