https://blog.csdn.net/xiangzaixiansheng/article/details/88654917
https://blog.csdn.net/weixin_37730482/article/details/80567141

<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />//好像不要权限也可以?

private AudioManager audioManager;
private int currentVoice;//当前的系统音量

audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

currentVoice = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//当前的系统音量

/**
 * AudioManager.NUM_STREAMS:
 * AudioManager.STREAM_ALARM: 闹钟的音频流
 * AudioManager.STREAM_DTMF: DTMF音调的音频流
 * AudioManager.STREAM_MUSIC: 播放音乐的音频流
 * AudioManager.STREAM_NOTIFICATION: 通知的音频流
 * AudioManager.STREAM_RING: 电话铃响时的音频流
 * AudioManager.STREAM_SYSTEM: 系统声音的音频流
 * AudioManager.STREAM_VOICE_CALL: 电话的音频流
 * AudioManager.USE_DEFAULT_STREAM_TYPE: 系统默认
 */
int maxVoice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//播放音乐的最大音量: 15
seekBar.setMax(maxVoice);//把系统音量和SeekBar关联起来
seekBar.setProgress(currentVoice);

//参数2: 音量.   参数3: 是否显示系统的音量控制面板，1显示，0不显示
audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVoice, 0);//AudioManager.FLAG_SHOW_UI = 1


if (audioManager != null) {
	audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVoice, 0);
	//好像没有释放的方法
}