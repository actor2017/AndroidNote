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
		// 1. ������ͼ����
		Intent intent = new Intent();
		// 2. ���ö���
		intent.setAction("com.itheima.dt.EAT");
		//�������ݣ���д�ɲ�д
		intent.setData(Uri.parse("itcast://��ƿ����"));
		// 3. �������
		sendBroadcast(intent);
	}
}
