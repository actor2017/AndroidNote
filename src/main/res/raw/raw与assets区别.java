1.两者目录下的文件在打包后会原封不动的保存在apk包中，不会被编译成二进制。


*res/raw和assets的不同点：
1.res/raw中的文件会被映射到R.java文件中，访问的时候直接使用资源ID即R.raw.filename；
assets文件夹下的文件不会被映射到R.java中，访问的时候需要AssetManager类。

2.res/raw不可以有目录结构，而assets则可以有目录结构，也就是assets目录下可以再建立文件夹


*读取文件资源：
1.读取res/raw下的文件资源，通过以下方式获取输入流来进行写操作
InputStream is = getResources().openRawResource(R.raw.filename);  
AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);

private MediaPlayer mediaPlayer;
//初始化MediaPlayer
private void initMediaPlayer() {
	if(mediaPlayer == null) {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(mBeepListener);			//播放监听
		AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
		try {
		    mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
			file.close();
			mediaPlayer.setVolume(1, 1);
			mediaPlayer.prepare();
		} catch (IOException e) {
			mediaPlayer = null;
		}
	}
}
private void playBeepSound() {//播放扫码提示音
	if (mediaPlayer != null) {
		mediaPlayer.start();
	}
}
@Override
protected void onDestroy() {
	if (mediaPlayer != null) {
		if (mediaPlayer.isPlaying()) {//正在播放
			mediaPlayer.stop();//停止.pause:暂停
		}
		mediaPlayer.release();//释放
		mediaPlayer = null;
	}
	super.onDestroy();
}


如果用Videoview来播放：
videoView.setVideoUri(Uri.parse("android.resource://" + getpackageName() + "/" + R.raw.movie));




2.读取assets下的文件资源，通过以下方式获取输入流来进行写操作
AssetManager am = null;  
am = getAssets();  
InputStream is = am.open("filename");
