package com.itheima.code_famer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MiddleCodeFarmerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//获取数据
		String result = getResultData();
		System.out.println("中级码农："+result);
		
		//修改广播中的数据
		setResultData("给每个码农买个糖吃");
	}

}
