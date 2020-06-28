https://github.com/CarGuo/GSYVideoPlayer
1.添加依赖
compile 'com.github.CarGuo:GSYVideoPlayer:v1.6.3'//直播,点播,列表播放,同时播放多个视频

2.https://github.com/CarGuo/GSYVideoPlayer/blob/master/doc/GSYVIDEO_PLAYER_PROJECT_INFO.md
项目经过多版本调整之后，目前大致结构分为如下 ：
Player 播放内核层：IjkMediaPlayer、ExoPlayr2、MediaPlayer（IPlayerManager）。
Cache 缓存层：ProxyCacheManager、ExoPlayerCacheManager（ICacheManager）。
Manager 内核管理层：GSYVideoManager（GSYVideoBaseManager <- GSYVideoViewBridge）。
Video 播放器控件层：GSYTextureRenderVIew 到 GSYVideoPlayer 五层。
Render 渲染控件层：TextureView、SurfaceView、GLSurfaceView（GSYRenderView <- IGSYRenderView）。

2.接口文档入口	--- 使用说明、接口文档 - 入口 https://github.com/CarGuo/GSYVideoPlayer/wiki

2.以下设置全局生效
  2.1.PlayerFactory
PlayerFactory.setPlayManager(new Exo2PlayerManager());//EXO模式
PlayerFactory.setPlayManager(new SystemPlayerManager());//系统模式
PlayerFactory.setPlayManager(new IjkPlayerManager());//ijk模式

GSYVideoManager.instance().clearAllDefaultCache(MainActivity.this);//清理缓存

  2.2.调整代码结构，CacheFactory 更方便自定义，默认 ProxyCacheManager。
CacheFactory.setCacheManager(new ExoPlayerCacheManager());//exo缓存模式，支持m3u8，只支持exo
CacheFactory.setCacheManager(new ProxyCacheManager());//代理缓存模式，支持所有模式，不支持m3u8等

  2.3.增加 ExoMediaSourceInterceptListener，方便 Exo 模式下使用自定义的 MediaSource。
  ExoSourceManager.setSkipSSLChain(true);//7.0.1 ExoPlayer 增加 SSL 证书忽略支持
ExoSourceManager.setExoMediaSourceInterceptListener(new ExoMediaSourceInterceptListener() {
           /**
            * @param dataSource  链接
            * @param preview     是否带上header，默认有header自动设置为true
            * @param cacheEnable 是否需要缓存
            * @param isLooping   是否循环
            * @param cacheDir    自定义缓存目录
            * @return 返回不为空时，使用返回的自定义mediaSource
            */
            @Override
            public MediaSource getMediaSource(String dataSource, boolean preview, boolean cacheEnable, boolean isLooping, File cacheDir) {
                return null;
            }
});

  2.4.切换比例
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT);//默认显示比例
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);//16:9
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);//全屏裁减显示，为了显示正常 CoverImageView 建议使用FrameLayout作为父布局
GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);//全屏拉伸显示，使用这个属性时，surface_container建议使用FrameLayout
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3);//4:3

2.5.切换渲染
GSYVideoType.setRenderType(GSYVideoType.TEXTURE);//默认TextureView
GSYVideoType.setRenderType(GSYVideoType.SUFRACE);//SurfaceView，动画切换等时候效果比较差
GSYVideoType.setRenderType(GSYVideoType.GLSURFACE);//GLSurfaceView、支持滤镜


2.继承关系
FrameLayout
--  GSYTextureRenderView//1.绘制View
----  GSYVideoView//2.视频回调与状态处理等相关层
------  GSYVideoControlView//3.播放UI的显示、控制层、手势处理等
--------  GSYBaseVideoPlayer//4.处理全屏和小屏幕逻辑
----------  GSYVideoPlayer//5.兼容的空View，目前用于 GSYVideoManager的设置
------------  StandardGSYVideoPlayer//6.标准播放器，继承之后实现一些ui显示效果，如显示／隐藏ui，播放按键等
--------------   SwitchVideo(自定义)//列表跳转详情, 共享元素跳过去, 无感知(目前有bug)
--------------   DanmakuVideoPlayer(自定义)//配置弹幕使用的播放器,b站的弹幕so只有v5 v7 x86、没有64，所以记得配置上ndk过滤
--------------   ListGSYVideoPlayer//7.列表播放支持
----------------   GSYSampleADVideoPlayer//8.只支持每个片头广告播放的类,其实就是根据实体，判断播放列表中哪个是广告，哪个不是，从而处理不同的UI显示效果
--------------   GSYADVideoPlayer//9.
--------------   NormalGSYVideoPlayer//10.使用正常播放按键和loading的播放器
--------------   EmptyControlVideo extends StandardGSYVideoPlayer(自定义)//无任何控制ui的播放
--------------   MultiSampleVideo(自定义)//多个同时播放的播放控件
--------------   SampleVideo extends StandardGSYVideoPlayer(自定义)//这个播放器的demo配置切换到全屏播放器
--------------   SampleControlVideo extends StandardGSYVideoPlayer(自定义)//这个播放器的demo配置切换到全屏播放器
----------------   CustomRenderVideoPlayer(自定义)//自定义渲染控件
----------------   PreViewGSYVideoPlayer extends NormalGSYVideoPlayer(自定义)//进度图小图预览的另类实现
--------------   LandLayoutVideo(自定义)//CustomGSYVideoPlayer 是试验中，建议使用的时候使用 StandardGSYVideoPlayer
--------------   FloatingVideo(自定义)//多窗体下的悬浮窗页面支持Video
--------------   GSYExo2PlayerView(自定义)//自定义View支持exo的list数据，实现无缝切换效果
--------------   ListADVideoPlayer(自定义)
----------------   RequestListADVideoPlayer(自定义)
--------------   SampleCoverVideo extends StandardGSYVideoPlayer(自定义)//带封面
--------------   SmartPickVideo(自定义)//无缝切换视频的DEMO.这里是切换清晰度，稍微修改下也可以作为切换下一集等


3.Activity
GSYBaseActivityDetail<T extends GSYBaseVideoPlayer>//详情模式播放页面基础类
public void initVideo();//选择普通模式
public void initVideoBuilderMode();//选择builder模式
public void showFull();//全屏
public void onBackPressed();
public void onConfigurationChanged(Configuration newConfig);
public void onStartPrepared(String url, Object... objects);//开始加载，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
public void onPrepared(String url, Object... objects);//加载成功
public void onClickStartIcon(String url, Object... objects);
public void onClickStartError(String url, Object... objects);
public void onClickStop(String url, Object... objects);
public void onClickStopFullscreen(String url, Object... objects);
public void onClickResume(String url, Object... objects);
public void onClickResumeFullscreen(String url, Object... objects);
public void onClickSeekbar(String url, Object... objects);
public void onClickSeekbarFullscreen(String url, Object... objects);
public void onAutoComplete(String url, Object... objects);
public void onEnterFullscreen(String url, Object... objects);
public void onQuitFullscreen(String url, Object... objects);
public void onQuitSmallWidget(String url, Object... objects);
public void onEnterSmallWidget(String url, Object... objects);
public void onTouchScreenSeekVolume(String url, Object... objects);
public void onTouchScreenSeekPosition(String url, Object... objects);
public void onTouchScreenSeekLight(String url, Object... objects);
public void onPlayError(String url, Object... objects);
public void onClickStartThumb(String url, Object... objects);
public void onClickBlank(String url, Object... objects);
public void onClickBlankFullscreen(String url, Object... objects);
public boolean hideActionBarWhenFull();
public boolean hideStatusBarWhenFull();
public abstract T getGSYVideoPlayer();//抽象方法, 播放控件
public abstract GSYVideoOptionBuilder getGSYVideoOptionBuilder();//配置播放器
public abstract void clickForFullScreen();//点击了全屏
public abstract boolean getDetailOrientationRotateAuto();//是否启动旋转横屏，true表示启动
public boolean isAutoFullWithSize();//是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，注意，这时候默认旋转无效

GSYBaseADActivityDetail<T extends GSYBaseVideoPlayer, R extends GSYADVideoPlayer> extends GSYBaseActivityDetail<T>//详情AD模式播放页面基础类
public void startAdPlay();//显示播放广告
public void showADFull();//广告视频的全屏显示
public abstract R getGSYADVideoPlayer();
public abstract GSYVideoOptionBuilder getGSYADVideoOptionBuilder();//配置AD播放器
public abstract boolean isNeedAdOnStart();//是否播放开始广告


4.播放器方法 https://github.com/CarGuo/GSYVideoPlayer/wiki/%E5%9F%BA%E7%A1%80Player-API
1.GSYTextureRenderView//1.绘制View
public GSYVideoGLView.ShaderInterface getEffectFilter();//GL的滤镜
public GSYRenderView getRenderProxy();//获取渲染的代理层
public void setEffectFilter(GSYVideoGLView.ShaderInterface effectFilter);//设置滤镜效果
public void setMatrixGL(float[] matrixGL);//GL模式下的画面matrix效果, matrixGL 16位长度
public void setCustomGLRenderer(GSYVideoGLViewBaseRender renderer);//自定义GL的渲染render
public void setGLRenderMode(int mode);//GL布局的绘制模式，利用布局计算大小还是使用render计算大小, MODE_LAYOUT_SIZE = 0,  MODE_RENDER_SIZE = 1


2.GSYVideoView//2.视频回调与状态处理等相关层
public int getCurrentVideoWidth();
public int getCurrentVideoHeight();
public int getVideoSarNum();
public int getVideoSarDen();
public boolean setUp(String url, boolean cacheWithPlay, String title);//设置播放URL, cacheWithPlay:是否边播边缓存
//cachePath:缓存路径，如果是M3U8或者HLS，请设置为false                  mapHeadData:头部信息
public boolean setUp(String url, boolean cacheWithPlay, File cachePath, Map<String, String> mapHeadData, String title);
public boolean setUp(String url, boolean cacheWithPlay, File cachePath, String title);
public void onVideoReset();//重置
public void onVideoPause();//暂停状态
public void onVideoResume();//恢复暂停状态
public void onVideoResume(boolean seek);//恢复暂停状态, seek 是否产生seek动作
public void onPrepared();
public void onAutoCompletion();
public void onCompletion();
public void onSeekComplete();
public void onError(int what, int extra);
public void onInfo(int what, int extra);
public void onVideoSizeChanged();
public void clearCurrentCache();//清除当前缓存
public int getCurrentPositionWhenPlaying();//获取当前播放进度
public int getDuration();//获取当前总时长
public void release();//释放吧
public void startAfterPrepared();//prepared成功之后会开始播放
public boolean isInPlayingState();//根据状态判断是否播放中
public String getPlayTag();//播放tag防止错误，因为普通的url也可能重复
public void setPlayTag(String playTag);//播放tag防止错误，因为普通的url也可能重复(可传入position)
public int getPlayPosition();
public void setPlayPosition(int playPosition);//设置播放位置防止错位
public long getNetSpeed();//网络速度.注意，这里如果是开启了缓存，因为读取本地代理，缓存成功后还是存在速度的. 再打开已经缓存的本地文件，网络速度才会回0.因为是播放本地文件了
public String getNetSpeedText();//网络速度, 同上
public long getSeekOnStart();
public void setSeekOnStart(long seekOnStart);//从哪里开始播放. 目前有时候前几秒有跳动问题，毫秒. 需要在startPlayLogic之前，即播放开始之前
public int getBuffterPoint();//缓冲进度/缓存进度
public boolean isIfCurrentIsFullscreen();//是否全屏
public void setIfCurrentIsFullscreen(boolean ifCurrentIsFullscreen);//
public boolean isLooping();
public void setLooping(boolean looping);//设置循环
public void setVideoAllCallBack(VideoAllCallBack mVideoAllCallBack);//设置播放过程中的回调
public float getSpeed();
public void setSpeed(float speed);//播放速度
public void setSpeed(float speed, boolean soundTouch);//soundTouch 是否对6.0下开启变速不变调
public void setSpeedPlaying(float speed, boolean soundTouch);//播放中生效的播放数据
public boolean isShowPauseCover();
public void setShowPauseCover(boolean showPauseCover);//是否需要加载显示暂停的cover图片
public void seekTo(long position);
public boolean isStartAfterPrepared();
public void setStartAfterPrepared(boolean startAfterPrepared);//准备成功之后立即播放. 默认true，false的时候需要在prepared后调用startAfterPrepared()
public boolean isReleaseWhenLossAudio();
public void setReleaseWhenLossAudio(boolean releaseWhenLossAudio);//长时间失去音频焦点，暂停播放器. 默认true，false的时候只会暂停
public Map<String, String> getMapHeadData();
public void setMapHeadData(Map<String, String> headData);//单独设置mapHeader
public String getOverrideExtension();
public void setOverrideExtension(String overrideExtension);//是否需要覆盖拓展类型，目前只针对exoPlayer内核模式有效. overrideExtension 比如传入 m3u8,mp4,avi 等类型


3.GSYVideoControlView//3.播放UI的显示、控制层、手势处理等
public void onStopTrackingTouch(SeekBar seekBar);//拖动进度条
public boolean setUpLazy(String url, boolean cacheWithPlay, File cachePath, Map<String, String> mapHeadData, String title);//在点击播放的时候才进行真正setup(startPlayLogic())
public void initUIState();//初始化为正常状态
public RelativeLayout getThumbImageViewLayout();//封面布局
public View getThumbImageView();
public void setThumbImageView(View view);//设置封面
public void clearThumbImageView();//清除封面
public TextView getTitleTextView();//title
public View getStartButton();//获取播放按键
public ImageView getFullscreenButton();//获取全屏按键
public ImageView getBackButton();//获取返回按键
public int getEnlargeImageRes();
public void setEnlargeImageRes(int mEnlargeImageRes);//设置右下角"显示切换到全屏"的按键资源,必须在setUp之前设置,不设置使用默认
public int getShrinkImageRes();
public void setShrinkImageRes(int mShrinkImageRes);//设置右下角"显示退出全屏"的按键资源,必须在setUp之前设置,不设置使用默认
public void setIsTouchWigetFull(boolean isTouchWigetFull);//是否可以全屏滑动界面改变进度，声音等. 默认 true
public void setThumbPlay(boolean thumbPlay);//是否点击封面可以播放
public boolean isHideKey();
public void setHideKey(boolean hideKey);//全屏隐藏虚拟按键，默认打开
public boolean isNeedShowWifiTip();
public boolean isTouchWiget();
public void setIsTouchWiget(boolean isTouchWiget);//是否可以滑动界面改变进度，声音等 默认true
public boolean isTouchWigetFull();
public void setNeedShowWifiTip(boolean needShowWifiTip);//是否需要显示流量提示,默认true
public void setSeekRatio(float seekRatio);//调整触摸滑动快进的比例. 默认1。数值越大，滑动的产生的seek越小
public float getSeekRatio();
public boolean isNeedLockFull();
public void setNeedLockFull(boolean needLoadFull);//是否需要全屏锁定屏幕功能. 如果单独使用请设置setIfCurrentIsFullscreen为true
public void setLockClickListener(LockClickListener lockClickListener);//锁屏点击
public void setDismissControlTime(int dismissControlTime);//设置触摸显示控制ui的消失时间. 毫秒，默认2500
public int getDismissControlTime();
public void setGSYVideoProgressListener(GSYVideoProgressListener videoProgressListener);//进度回调


4.GSYBaseVideoPlayer//4.处理全屏和小屏幕逻辑
public boolean isVerticalFullByVideoSize();//是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，注意，这时候默认旋转无效
public void onConfigurationChanged(Activity activity, Configuration newConfig, OrientationUtils orientationUtils);//旋转处理
//newConfig:配置    orientationUtils:旋转工具类    hideActionBar:是否隐藏actionbar    hideStatusBar:是否隐藏statusbar
public void onConfigurationChanged(Activity activity, Configuration newConfig, OrientationUtils orientationUtils, boolean hideActionBar, boolean hideStatusBar);
//利用window层播放全屏效果    actionBar:是否有actionBar，有的话需要隐藏    statusBar:是否有状态bar，有的话需要隐藏
public GSYBaseVideoPlayer startWindowFullscreen(final Context context, final boolean actionBar, final boolean statusBar);
public GSYBaseVideoPlayer showSmallVideo(Point size, final boolean actionBar, final boolean statusBar);//显示小窗口
public void hideSmallVideo();//隐藏小窗口
public boolean isShowFullAnimation();
public void setShowFullAnimation(boolean showFullAnimation);//全屏动画 是否使用全屏动画效果
public boolean isRotateViewAuto();
public void setRotateViewAuto(boolean rotateViewAuto);//是否开启自动旋转
public boolean isLockLand();
public void setLockLand(boolean lockLand);//一全屏就锁屏横屏，默认false竖屏，可配合setRotateViewAuto使用
public boolean isRotateWithSystem();
public void setRotateWithSystem(boolean rotateWithSystem);//是否更新系统旋转，false的话，系统禁止旋转也会跟着旋转
public GSYVideoPlayer getFullWindowPlayer();//获取全屏播放器对象
public GSYVideoPlayer getSmallWindowPlayer();//获取小窗口播放器对象
public GSYBaseVideoPlayer getCurrentPlayer();//获取当前长在播放的播放控件
//全屏返回监听，如果设置了，默认返回动作无效. 包含返回键和全屏返回按键，前提是这两个按键存在
public void setBackFromFullScreenListener(OnClickListener backFromFullScreenListener);
public void setFullHideActionBar(boolean actionBar);
public void setFullHideStatusBar(boolean statusBar)
public boolean isFullHideActionBar();
public boolean isFullHideStatusBar();
public int getSaveBeforeFullSystemUiVisibility();
public void setSaveBeforeFullSystemUiVisibility(int systemUiVisibility);
public boolean isAutoFullWithSize();
public void setAutoFullWithSize(boolean autoFullWithSize);//是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，注意，这时候默认旋转无效. 默认false


5.GSYVideoPlayer//5.兼容的空View，目前用于 GSYVideoManager的设置
public GSYVideoViewBridge getGSYVideoManager();


6.StandardGSYVideoPlayer//6.标准播放器，继承之后实现一些ui显示效果，如显示／隐藏ui，播放按键等
public int getLayoutId();//继承后重写可替换为你需要的布局
public void startPlayLogic();//开始播放, 显示wifi确定框??
public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar);//将自定义的效果也设置到全屏
public void setBottomShowProgressBarDrawable(Drawable drawable, Drawable thumb);//底部进度条-弹出的
public void setBottomProgressBarDrawable(Drawable drawable);//底部进度条-非弹出
public void setDialogVolumeProgressBar(Drawable drawable);//声音进度条
public void setDialogProgressBar(Drawable drawable);//中间进度条
public void setDialogProgressColor(int highLightColor, int normalColor);//中间进度条字体颜色
public void taskShotPic(GSYVideoShotListener gsyVideoShotListener);//获取截图
public void taskShotPic(GSYVideoShotListener gsyVideoShotListener, boolean high);//获取截图 high,是否需要高清的
public void saveFrame(final File file, GSYVideoShotSaveListener gsyVideoShotSaveListener);//保存截图
public void saveFrame(final File file, final boolean high, final GSYVideoShotSaveListener gsyVideoShotSaveListener);//保存截图 high,是否需要高清的
//重新开启进度查询以及控制view消失的定时任务
//用于解决GSYVideoHelper中通过removeview方式做全屏切换导致的定时任务停止的问题, GSYVideoControlView   onDetachedFromWindow（）
public void restartTimerTask();


7.ListGSYVideoPlayer//7.列表播放支持
public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position);//设置播放URL
public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath);
//cachePath:缓存路径，如果是M3U8或者HLS，请设置为false    mapHeadData:http header
public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData);
public boolean playNext();//播放下一集  return true表示还有下一集


8.GSYSampleADVideoPlayer//8.只支持每个片头广告播放的类,其实就是根据实体，判断播放列表中哪个是广告，哪个不是，从而处理不同的UI显示效果
public boolean setAdUp(ArrayList<GSYADVideoModel> url, boolean cacheWithPlay, int position);//带片头广告的
public boolean setAdUp(ArrayList<GSYADVideoModel> url, boolean cacheWithPlay, int position, File cachePath);
public boolean setAdUp(ArrayList<GSYADVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData);

  
9.GSYADVideoPlayer//9.
public GSYVideoViewBridge getGSYVideoManager();
public void removeFullWindowViewOnly();//移除广告播放的全屏


10.NormalGSYVideoPlayer//10.使用正常播放按键和loading的播放器

