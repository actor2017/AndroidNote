package com.itheima.cctv;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void send(View v){
		Intent intent = new Intent();
		intent.setAction("com.itheima.cctv.MONEY");
		//发送无序广播
//		sendBroadcast(intent);
		
		/*
		 * 发送有序广播
		 * intent				：意图
		 * receiverPermission	：指定接收者的权限
		 * resultReceiver		：最终的广播接收者，最后一个接收到广播的
		 * scheduler			：Handler
		 * initialCode			：广播的编码(1号文件,2号文件,随意写)
		 * initialData			：少量的数据，接收者必须用getResultData()接受数据
		 * initialExtras		：bundle，传输大量的数据,接收者:intent.getExtras();,见短信拦截器示例
		 */
		sendOrderedBroadcast(intent, null, new FinalReceiver(), new Handler(), 1,
				"2017年给每个码农补贴￥10000.00元", null);//传递数据
	}

	/**
	 * 最终的广播接收者
	 * 无论前面的接收者怎么拦截终止、修改广播中的数据
	 * 都是最后一个接受的广播的
	 * 不要在清单文件中注册
	 */
	class FinalReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("最终接收者："+getResultData());
		}
		
	}
}
