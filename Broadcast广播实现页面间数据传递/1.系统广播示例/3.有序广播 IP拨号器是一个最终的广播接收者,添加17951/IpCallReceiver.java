package com.itheima.ip_call;

import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IpCallReceiver extends BroadcastReceiver {

	/**
	 * 1.接受外拨电话的广播
		2.获取用户拨打的手机号码
		3.在手机号码前加17951
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction())) {
			//获取用户拨打的手机号码
			String phonNum = getResultData();
			System.out.println("用户拨打的手机号码："+phonNum);
			String newNum = phonNum;
			if (!phonNum.startsWith("17951")) {
				//在手机号码前加17951
				newNum = "17951"+phonNum;
			}
			//把修改后的手机号码设置到广播中
			if (new Random().nextInt(2) == 1) {//如果等于1,就能拨打出去,否则拨打不粗去
				setResultData(newNum);
			}else {
				setResultData(null);		//否则就打不出去
			}
			//终止广播,但是失败了,因为手机拨号器是最终的广播接收者
			abortBroadcast();
		}
	}

}
