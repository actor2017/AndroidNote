package com.itheima.ip_call;

import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IpCallReceiver extends BroadcastReceiver {

	/**
	 * 1.�����Ⲧ�绰�Ĺ㲥
		2.��ȡ�û�������ֻ�����
		3.���ֻ�����ǰ��17951
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction())) {
			//��ȡ�û�������ֻ�����
			String phonNum = getResultData();
			System.out.println("�û�������ֻ����룺"+phonNum);
			String newNum = phonNum;
			if (!phonNum.startsWith("17951")) {
				//���ֻ�����ǰ��17951
				newNum = "17951"+phonNum;
			}
			//���޸ĺ���ֻ��������õ��㲥��
			if (new Random().nextInt(2) == 1) {//�������1,���ܲ����ȥ,���򲦴򲻴�ȥ
				setResultData(newNum);
			}else {
				setResultData(null);		//����ʹ򲻳�ȥ
			}
			//��ֹ�㲥,����ʧ����,��Ϊ�ֻ������������յĹ㲥������
			abortBroadcast();
		}
	}

}
