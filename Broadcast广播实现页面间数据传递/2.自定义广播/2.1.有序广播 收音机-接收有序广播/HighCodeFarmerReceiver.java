package com.itheima.code_famer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HighCodeFarmerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//获取数据
		String result = getResultData();
		System.out.println("高级码农："+result);
		//终止广播,中低级码农没钱了
//		abortBroadcast();
		//修改广播中的数据
		setResultData("给每个码农补贴￥1000.00元");
	}

}
