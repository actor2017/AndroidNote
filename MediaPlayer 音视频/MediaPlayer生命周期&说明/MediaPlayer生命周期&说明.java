### 24 播放暂停状态切换	
	MediaPlayer 是Android系统里唯一用于播放音/视频的类
	VideoView 是SurfaceView的子类，封装了MediaPlayer，只是用于视频画面的显示

### 25 MediaPlayer生命周期
	1. 空闲: reset()  new MediaPlayer()   MediaPlayer.create();
	2. 初始化: setDataSource()
	3. 准备：
		* 同步: prepare()
		* 异步: prepareAsync();//播放网络音视频
	4. 播放状态: start()
	5. 播放完成
	6. 停止: stop()
	7. 暂停: pause()
	8. 结束: release()
	9. 错误: 出错了

设置音量大小: Audiomanager

### 方法说明
MediaPlayer player = MediaPlayer.create(this, R.raw.opendoormusic);
player.setVolume(1, 1);//左右声道音量 0-1, 最大, 使用的是多媒体的音量通道, 所以即使电话静音,也能够播放
player.setLooping(false);//单曲循环
int duration = player.getDuration();//获取音/视频总时间,单位ms
int position = player.getCurrentPosition();//当前音/视频播放进度,单位ms
player.seekTo(int msec);//设置音/视频播放进度,单位ms
player.start();//开始播放提示音;

public void setDataSource(Context context, Uri uri, Map<String, String> headers);//设置数据源
public void setDisplay(SurfaceHolder sh);
public void setAudioStreamType(int streamtype);//播放音视频:AudioManager.STREAM_MUSIC
public void setScreenOnWhilePlaying(boolean screenOn);//好像是保持屏幕常亮
public native void prepareAsync();//播放视频时异步准备
public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener);
public native boolean isPlaying();//是否在播放
public void reset();//将MediaPlayer重置为未初始化状态
public void start();//开始播放
public void pause();//暂停
public void stop();//停止

//监听
public void setOnPreparedListener(OnPreparedListener listener);//音/视频准备完成监听
public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener listener);//视频尺寸改变
public void setOnCompletionListener(OnCompletionListener listener);//播放完成
public void setOnErrorListener(OnErrorListener listener);//播放出错
public void setOnInfoListener(OnInfoListener listener);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            if (player.isPlaying()) {//正在播放
                player.stop();//停止.pause:暂停
            }
            player.release();//释放
            player = null;
        }
    }