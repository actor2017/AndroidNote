package com.itheima.jjj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class JjjReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("com.itheima.dt.EAT".equals(intent.getAction())) {
			//麻麻，我回来了。。。itcast://打瓶酱油
			System.out.println("麻麻，我回来。。。"+intent.getData().toString());
		}
	}

}
