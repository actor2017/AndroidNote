package com.itheima.add_remove;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AddRemoveReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
			System.out.println("卸载了应用："+intent.getData().toString());
		}else if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
			System.out.println("安装了应用："+intent.getData().toString());
		}
	}

}
