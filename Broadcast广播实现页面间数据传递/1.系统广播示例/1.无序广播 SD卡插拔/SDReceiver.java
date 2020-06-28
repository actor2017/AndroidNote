package com.itheima.sd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 1. 买一个收音机
 */
public class SDReceiver extends BroadcastReceiver {
	/**
	 * 接受到广播后调用
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
			System.out.println("SD卡被拔出了");
		}else if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
			System.out.println("SD插入了。。。。");
		}
	}
}
