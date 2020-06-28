package com.itheima.qt;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class QtService extends Service {
	private TelephonyManager		tm;
	private MyPhoneStateListener	mPhoneStateListener;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 1. 获取手机电话管理器
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// 2. 设置监听
		mPhoneStateListener = new MyPhoneStateListener();
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	/**
	 * 3. 内部类实现监听
	 */
	private class MyPhoneStateListener extends PhoneStateListener {
		private MediaRecorder	recorder;

		// 手机的呼叫状态变化回调
		//state:状态(响铃,通话,空闲)
		// incomingNumber:来电手机号码
		@Override
		//重写电话状态改变的方法,还可重写一些其它方法(信号强度,飞行模式....)
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			System.out.println("来电号码：" + incomingNumber);
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:// 空闲
					System.out.println("空闲");
					if (recorder != null) {
						//8. 停止录音
						recorder.stop();
						 //9.重置
						 recorder.reset();   // You can reuse the object by going back to setAudioSource() step
						 //10. 释放资源
						 recorder.release(); // Now the object cannot be reused
						 recorder = null;
					}
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:// 通话
					System.out.println("通话");
					//开始录音
					//1. 创建录音机
					recorder = new MediaRecorder();
					//2. 设置音频源
					 recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					 //3. 设置音频的格式(其它格式要收费?)
					 recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					 //4. 设置编译音频
					 recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					 //5. 设置音频存储的位置
					 recorder.setOutputFile("/mnt/sdcard/luyin.3gp");
					 try {
						 //6.准备录音
						recorder.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					 //7. 开始录音
					 recorder.start();   // Recording is now started
					
					break;
				case TelephonyManager.CALL_STATE_RINGING:// 响铃
					System.out.println("来电了");
					break;

				default:
					break;
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 4. 服务销毁前注销监听
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
}
