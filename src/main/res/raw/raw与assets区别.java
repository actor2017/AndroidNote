1.����Ŀ¼�µ��ļ��ڴ�����ԭ�ⲻ���ı�����apk���У����ᱻ����ɶ����ơ�


*res/raw��assets�Ĳ�ͬ�㣺
1.res/raw�е��ļ��ᱻӳ�䵽R.java�ļ��У����ʵ�ʱ��ֱ��ʹ����ԴID��R.raw.filename��
assets�ļ����µ��ļ����ᱻӳ�䵽R.java�У����ʵ�ʱ����ҪAssetManager�ࡣ

2.res/raw��������Ŀ¼�ṹ����assets�������Ŀ¼�ṹ��Ҳ����assetsĿ¼�¿����ٽ����ļ���


*��ȡ�ļ���Դ��
1.��ȡres/raw�µ��ļ���Դ��ͨ�����·�ʽ��ȡ������������д����
InputStream is = getResources().openRawResource(R.raw.filename);  
AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);

private MediaPlayer mediaPlayer;
//��ʼ��MediaPlayer
private void initMediaPlayer() {
	if(mediaPlayer == null) {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(mBeepListener);			//���ż���
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
private void playBeepSound() {//����ɨ����ʾ��
	if (mediaPlayer != null) {
		mediaPlayer.start();
	}
}
@Override
protected void onDestroy() {
	if (mediaPlayer != null) {
		if (mediaPlayer.isPlaying()) {//���ڲ���
			mediaPlayer.stop();//ֹͣ.pause:��ͣ
		}
		mediaPlayer.release();//�ͷ�
		mediaPlayer = null;
	}
	super.onDestroy();
}


�����Videoview�����ţ�
videoView.setVideoUri(Uri.parse("android.resource://" + getpackageName() + "/" + R.raw.movie));




2.��ȡassets�µ��ļ���Դ��ͨ�����·�ʽ��ȡ������������д����
AssetManager am = null;  
am = getAssets();  
InputStream is = am.open("filename");
