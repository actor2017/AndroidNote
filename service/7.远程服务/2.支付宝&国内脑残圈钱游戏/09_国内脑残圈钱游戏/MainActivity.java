package com.itheima.game;

import com.itheima.alipay.IALiPayService;
import com.itheima.alipay.IALiPayService.Stub;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends Activity {

	private PayCon	payCon;
	private IALiPayService	mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//绑定支付宝的支付服务
		Intent intent = new Intent("com.itheima.alipay.PAY");
		 payCon = new PayCon();
		bindService(intent,payCon, BIND_AUTO_CREATE);
	}
	
	private class PayCon implements ServiceConnection {

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = Stub.asInterface(service);
		}
	}
	
	public void dian(View v){
		try {
			mService.pay("狗蛋", "123123", 10.01f, System.currentTimeMillis());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
