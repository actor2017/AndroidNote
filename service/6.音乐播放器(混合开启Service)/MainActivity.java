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
		
		//��Ϸ�ʽ��������
		//startService��֤�����������ں�̨
		Intent intent = new Intent(this,MusicService.class);
		startService(intent);
		
		//bindService���÷�����ķ���
		musicConn = new MusicConn();
		bindService(intent,musicConn , BIND_AUTO_CREATE);
	}
	
	/**
	 * activity��service������ͨ��
	 */
	private class MusicConn implements ServiceConnection {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		//���ӳɹ�
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = (IMusicService)service;
		}
	}
	
	/**
	 * ��������
	 */
	public void play(View v){
		mService.play();
	}

	/**
	 * ֹͣ
	 */
	public void stop(View v){
		mService.stop();
	}
	
	/**
	 * ��������
	 */
	public void resume(View v){
		mService.resume();
	}
	/**
	 * ��ͣ
	 */
	public void pause(View view){
		mService.pause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//activity����ǰ������
		unbindService(musicConn);
	}
}
