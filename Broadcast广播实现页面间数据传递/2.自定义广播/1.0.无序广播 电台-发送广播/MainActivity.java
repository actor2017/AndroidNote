package com.itheima.dt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void send(View v){
		// 1. 创建意图对象
		Intent intent = new Intent();
		// 2. 设置动作
		intent.setAction("com.itheima.dt.EAT");
		//设置数据，可写可不写
		intent.setData(Uri.parse("itcast://打瓶酱油"));
		// 3. 激活组件
		sendBroadcast(intent);
	}
}
