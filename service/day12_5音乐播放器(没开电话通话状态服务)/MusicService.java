package com.itheima.music_player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

	private MediaPlayer	mediaPlayer;

	@Override
	public IBinder onBind(Intent intent) {
		return new Music();
	}

	/**
	 * 音乐服务的内部类
	 * 调用服务里操作音乐的方法
	 */
	private class Music extends Binder implements IMusicService{

		@Override
		public void play() {
			playMusic();
		}

		@Override
		public void pause() {
			pauseMusic();
		}

		@Override
		public void resume() {
			resumeMusic();
		}

		@Override
		public void stop() {
			stopMusic();
		}
	}
	
	/**
	 * 播放
	 */
	public void playMusic(){
		//避免二重唱
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			//释放
			mediaPlayer.release();
			mediaPlayer = null;
		}
		
		mediaPlayer = MediaPlayer.create(this, R.raw.ib);
		mediaPlayer.start();
	}

	public void stopMusic() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
	}

	public void resumeMusic() {
		if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	public void pauseMusic() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}
}
