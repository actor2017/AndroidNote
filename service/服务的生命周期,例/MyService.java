package com.itheima.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private static final String TAG = "MyService";
	/**
	 * 1.创建服务时调用
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate");
	}
	/**
	 * 2.服务被绑定后调用
	 */
	@Override
	public IBinder onBind(Intent intent) {
		//服务被绑定成功后调用
		Log.e(TAG, "2. 服务被绑定成功onBind");
		Log.e(TAG, "3. 返回服务里的内部类对象");
		return new ChengMiShu();
		//return null;
	}

	/**
	 * 服务里的内部类 内部人员 小蜜
	 */
	public class ChengMiShu extends Binder implements IMyService {
		public void qianShouMM(int money){
			if (money > 1000) {
				methodInService();
			}else {
				Toast.makeText(MyService.this, "没钱幻想找媳妇，回去写几年代码把", 0).show();	
			}
		}

		@Override
		public void 打麻将() {
			Toast.makeText(MyService.this, "要过年了，玩玩", 0).show();
		}

		private void 洗桑拿() {
			Toast.makeText(MyService.this, "洗桑拿把噢耶。。。", 0).show();
		}
	}

	/**
	 * 服务里的方法
	 */
	public void methodInService(){
		Toast.makeText(this, "我来了，你在哪儿？", 0).show();
	}

	/**
	 * 3.服务被解绑前调用
	 */
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
	/**
	 * 服务被销毁前调用
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
	/**
	 * 开启服务时调用onCreate,onStartCommand方法
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

}
