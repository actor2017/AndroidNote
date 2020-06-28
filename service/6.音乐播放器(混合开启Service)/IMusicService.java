package com.itheima.music_player;

/**
 * 音乐服务类的接口
 */
public interface IMusicService {

	/**
	 * 播放音乐
	 */
	public void play();

	/**
	 * 暂停音乐
	 */
	public void pause();

	/**
	 * 继续播放
	 */
	public void resume();

	/**
	 * 停止播放音乐
	 */
	public void stop();
}
