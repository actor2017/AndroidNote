package com.itheima.jjj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class JjjReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("com.itheima.dt.EAT".equals(intent.getAction())) {
			//���飬�һ����ˡ�����itcast://��ƿ����
			System.out.println("���飬�һ���������"+intent.getData().toString());
		}
	}

}
