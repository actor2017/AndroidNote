package com.itheima.music_player;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends Activity {

	private MusicConn	musicConn;
	private IMusicService	mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//混合方式开启服务
		//startService保证服务长期运行在后台
		Intent intent = new Intent(this,MusicService.class);
		startService(intent);
		
		//bindService调用服务里的方法
		musicConn = new MusicConn();
		bindService(intent,musicConn , BIND_AUTO_CREATE);
	}
	
	/**
	 * activity和service的连接通道
	 */
	private class MusicConn implements ServiceConnection {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		//连接成功
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = (IMusicService)service;
		}
	}
	
	/**
	 * 播放音乐
	 */
	public void play(View v){
		mService.play();
	}

	/**
	 * 停止
	 */
	public void stop(View v){
		mService.stop();
	}
	
	/**
	 * 继续播放
	 */
	public void resume(View v){
		mService.resume();
	}
	/**
	 * 暂停
	 */
	public void pause(View view){
		mService.pause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//activity销毁前解绑服务
		unbindService(musicConn);
	}
}
