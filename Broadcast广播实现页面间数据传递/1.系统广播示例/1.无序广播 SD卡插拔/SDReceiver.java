package com.itheima.sd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 1. ��һ��������
 */
public class SDReceiver extends BroadcastReceiver {
	/**
	 * ���ܵ��㲥�����
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
			System.out.println("SD�����γ���");
		}else if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
			System.out.println("SD�����ˡ�������");
		}
	}
}
