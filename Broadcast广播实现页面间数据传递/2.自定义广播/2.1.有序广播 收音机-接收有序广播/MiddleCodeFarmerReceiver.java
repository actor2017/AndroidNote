package com.itheima.code_famer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MiddleCodeFarmerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//��ȡ����
		String result = getResultData();
		System.out.println("�м���ũ��"+result);
		
		//�޸Ĺ㲥�е�����
		setResultData("��ÿ����ũ����ǳ�");
	}

}
