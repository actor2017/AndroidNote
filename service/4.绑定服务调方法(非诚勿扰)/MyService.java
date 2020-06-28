package com.itheima.call_method;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
private static final String TAG = "MyService";

	//服务被绑定成功后调用
	@Override
	public IBinder onBind(Intent intent) {
		return new ChengMiShu();
	}

	/**
	 * 服务里的方法
	 */
	public void methodInService(){
		Toast.makeText(getApplicationContext(), "我来了，你在哪儿？", 0).show();
	}
	
	/**
	 * 服务里的内部类
	 * 内部人员
	 * 小蜜
	 */
	public class ChengMiShu extends Binder implements IMyService {
		@Override
		public void qianShouMM(int money){
			if (money > 1000) {
				methodInService();
			}else {
				Toast.makeText(MyService.this, "没钱幻想找媳妇，回去写几年代码把", 0).show();	
			}
		}
		@Override
		public void 打麻将(){
			Toast.makeText(MyService.this, "要过年了，玩玩", 0).show();
		}
		
		//private,只能陪boss
		private void 洗桑拿(){
			Toast.makeText(MyService.this, "洗桑拿把噢耶。。。", 0).show();
		}
	}
	
}
