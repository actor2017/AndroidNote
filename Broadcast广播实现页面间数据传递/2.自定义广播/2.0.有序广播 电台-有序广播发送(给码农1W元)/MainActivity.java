package com.itheima.cctv;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void send(View v){
		Intent intent = new Intent();
		intent.setAction("com.itheima.cctv.MONEY");
		//��������㲥
//		sendBroadcast(intent);
		
		/*
		 * ��������㲥
		 * intent				����ͼ
		 * receiverPermission	��ָ�������ߵ�Ȩ��
		 * resultReceiver		�����յĹ㲥�����ߣ����һ�����յ��㲥��
		 * scheduler			��Handler
		 * initialCode			���㲥�ı���(1���ļ�,2���ļ�,����д)
		 * initialData			�����������ݣ������߱�����getResultData()��������
		 * initialExtras		��bundle���������������,������:intent.getExtras();,������������ʾ��
		 */
		sendOrderedBroadcast(intent, null, new FinalReceiver(), new Handler(), 1,
				"2017���ÿ����ũ������10000.00Ԫ", null);//��������
	}

	/**
	 * ���յĹ㲥������
	 * ����ǰ��Ľ�������ô������ֹ���޸Ĺ㲥�е�����
	 * �������һ�����ܵĹ㲥��
	 * ��Ҫ���嵥�ļ���ע��
	 */
	class FinalReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("���ս����ߣ�"+getResultData());
		}
		
	}
}
