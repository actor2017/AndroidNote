package com.itheima.code_famer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HighCodeFarmerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//��ȡ����
		String result = getResultData();
		System.out.println("�߼���ũ��"+result);
		//��ֹ�㲥,�еͼ���ũûǮ��
//		abortBroadcast();
		//�޸Ĺ㲥�е�����
		setResultData("��ÿ����ũ������1000.00Ԫ");
	}

}
