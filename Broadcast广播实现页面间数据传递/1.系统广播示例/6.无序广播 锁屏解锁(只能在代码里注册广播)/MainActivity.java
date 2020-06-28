package com.itheima.lock_screen;

import android.os.Bundle;
import android.app.Activity;
import android.content.IntentFilter;
import android.view.Menu;

public class MainActivity extends Activity {

	private LockScreenReceiver	lockScreenReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//������ע��
		//ֻ��Ӧ�ó�����������ܽ��ܹ㲥����Լϵͳ����
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_OFF");
		filter.addAction("android.intent.action.SCREEN_ON");
		//ע��㲥
		lockScreenReceiver = new LockScreenReceiver();
		registerReceiver(lockScreenReceiver, filter);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//��activity����ǰע���㲥������
		unregisterReceiver(lockScreenReceiver);
	}
}
