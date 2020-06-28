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
		//代码中注册
		//只有应用程序启动后才能接受广播，节约系统开销
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_OFF");
		filter.addAction("android.intent.action.SCREEN_ON");
		//注册广播
		lockScreenReceiver = new LockScreenReceiver();
		registerReceiver(lockScreenReceiver, filter);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//在activity销毁前注销广播接收者
		unregisterReceiver(lockScreenReceiver);
	}
}
