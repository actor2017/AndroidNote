package com.example.sec;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView	tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
	}
	
	//1. 在主线程中创建Handler对象
	Handler mHandler = new Handler(){
		//3. 重写HandleMessage方法
		public void handleMessage(Message msg) {
			tv.setText(String.valueOf(msg.obj));
		};
	};

	public void start(View v){
		new Thread(){
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//2.在子线程中发消息
					Message msg = new Message();
					msg.obj = i;
					mHandler.sendMessage(msg);
//					tv.setText(i+"");
				}
			};
		}.start();
	}
}
