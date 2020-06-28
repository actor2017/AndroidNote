底层用的MediaPlayer

VideoView:

public void setVideoURI(Uri uri);//设置视频地址, 本地/网络视频:Uri.parse(path)
public void setVideoURI(Uri uri, Map<String, String> headers);
public void setOnPreparedListener(MediaPlayer.OnPreparedListener l);//准备完成
public void setOnCompletionListener(OnCompletionListener l);//播放完成
public void setOnInfoListener(OnInfoListener l) {
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START://开始缓冲
                pbProgress.setVisibility(View.VISIBLE);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END://结束缓冲
                pbProgress.setVisibility(View.INVISIBLE);
                break;
        }
        return false;
    }
}

/**
 * 这是私有方法, 如果需要设置监听, 需自己重写VideoView
 * 设置网络的第二缓冲百分比
 */
private void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener) {
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		LogUtils.e(TAG, "percent:" + percent);
		float pre = percent / 100f;
		int pro = (int) (pre * mp.getDuration());
		sbPlayPosition.setSecondaryProgress(pro);
	}
}
public void setOnErrorListener(OnErrorListener l);
public boolean isPlaying();
public void resume();
public void pause();	//暂停
public void start();	//开始
public void stopPlayback();//应该是停止
public int getCurrentPosition();//现在播放位置,单位ms
public int getBufferPercentage();//获取缓存百分比
public void seekTo(int msec);//从哪儿开始播放,单位ms

//设置全屏(经测试好像无效)
videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
	@Override
	public void onPrepared(MediaPlayer mp) {
		//VIDEO_SCALING_MODE_SCALE_TO_FIT：适应屏幕显示
		//VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING：充满屏幕显示，保持比例，如果屏幕比例不对，则进行裁剪
		mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
	}
});

