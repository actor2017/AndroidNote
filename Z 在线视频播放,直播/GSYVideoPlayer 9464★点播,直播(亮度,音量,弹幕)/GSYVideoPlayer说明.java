https://github.com/CarGuo/GSYVideoPlayer
1.�������
compile 'com.github.CarGuo:GSYVideoPlayer:v1.6.3'//ֱ��,�㲥,�б���,ͬʱ���Ŷ����Ƶ

2.https://github.com/CarGuo/GSYVideoPlayer/blob/master/doc/GSYVIDEO_PLAYER_PROJECT_INFO.md
��Ŀ������汾����֮��Ŀǰ���½ṹ��Ϊ���� ��
Player �����ں˲㣺IjkMediaPlayer��ExoPlayr2��MediaPlayer��IPlayerManager����
Cache ����㣺ProxyCacheManager��ExoPlayerCacheManager��ICacheManager����
Manager �ں˹���㣺GSYVideoManager��GSYVideoBaseManager <- GSYVideoViewBridge����
Video �������ؼ��㣺GSYTextureRenderVIew �� GSYVideoPlayer ��㡣
Render ��Ⱦ�ؼ��㣺TextureView��SurfaceView��GLSurfaceView��GSYRenderView <- IGSYRenderView����

2.�ӿ��ĵ����	--- ʹ��˵�����ӿ��ĵ� - ��� https://github.com/CarGuo/GSYVideoPlayer/wiki

2.��������ȫ����Ч
  2.1.PlayerFactory
PlayerFactory.setPlayManager(new Exo2PlayerManager());//EXOģʽ
PlayerFactory.setPlayManager(new SystemPlayerManager());//ϵͳģʽ
PlayerFactory.setPlayManager(new IjkPlayerManager());//ijkģʽ

GSYVideoManager.instance().clearAllDefaultCache(MainActivity.this);//������

  2.2.��������ṹ��CacheFactory �������Զ��壬Ĭ�� ProxyCacheManager��
CacheFactory.setCacheManager(new ExoPlayerCacheManager());//exo����ģʽ��֧��m3u8��ֻ֧��exo
CacheFactory.setCacheManager(new ProxyCacheManager());//������ģʽ��֧������ģʽ����֧��m3u8��

  2.3.���� ExoMediaSourceInterceptListener������ Exo ģʽ��ʹ���Զ���� MediaSource��
  ExoSourceManager.setSkipSSLChain(true);//7.0.1 ExoPlayer ���� SSL ֤�����֧��
ExoSourceManager.setExoMediaSourceInterceptListener(new ExoMediaSourceInterceptListener() {
           /**
            * @param dataSource  ����
            * @param preview     �Ƿ����header��Ĭ����header�Զ�����Ϊtrue
            * @param cacheEnable �Ƿ���Ҫ����
            * @param isLooping   �Ƿ�ѭ��
            * @param cacheDir    �Զ��建��Ŀ¼
            * @return ���ز�Ϊ��ʱ��ʹ�÷��ص��Զ���mediaSource
            */
            @Override
            public MediaSource getMediaSource(String dataSource, boolean preview, boolean cacheEnable, boolean isLooping, File cacheDir) {
                return null;
            }
});

  2.4.�л�����
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT);//Ĭ����ʾ����
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);//16:9
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);//ȫ���ü���ʾ��Ϊ����ʾ���� CoverImageView ����ʹ��FrameLayout��Ϊ������
GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);//ȫ��������ʾ��ʹ���������ʱ��surface_container����ʹ��FrameLayout
GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3);//4:3

2.5.�л���Ⱦ
GSYVideoType.setRenderType(GSYVideoType.TEXTURE);//Ĭ��TextureView
GSYVideoType.setRenderType(GSYVideoType.SUFRACE);//SurfaceView�������л���ʱ��Ч���Ƚϲ�
GSYVideoType.setRenderType(GSYVideoType.GLSURFACE);//GLSurfaceView��֧���˾�


2.�̳й�ϵ
FrameLayout
--  GSYTextureRenderView//1.����View
----  GSYVideoView//2.��Ƶ�ص���״̬�������ز�
------  GSYVideoControlView//3.����UI����ʾ�����Ʋ㡢���ƴ����
--------  GSYBaseVideoPlayer//4.����ȫ����С��Ļ�߼�
----------  GSYVideoPlayer//5.���ݵĿ�View��Ŀǰ���� GSYVideoManager������
------------  StandardGSYVideoPlayer//6.��׼���������̳�֮��ʵ��һЩui��ʾЧ��������ʾ������ui�����Ű�����
--------------   SwitchVideo(�Զ���)//�б���ת����, ����Ԫ������ȥ, �޸�֪(Ŀǰ��bug)
--------------   DanmakuVideoPlayer(�Զ���)//���õ�Ļʹ�õĲ�����,bվ�ĵ�Ļsoֻ��v5 v7 x86��û��64�����Լǵ�������ndk����
--------------   ListGSYVideoPlayer//7.�б���֧��
----------------   GSYSampleADVideoPlayer//8.ֻ֧��ÿ��Ƭͷ��沥�ŵ���,��ʵ���Ǹ���ʵ�壬�жϲ����б����ĸ��ǹ�棬�ĸ����ǣ��Ӷ�����ͬ��UI��ʾЧ��
--------------   GSYADVideoPlayer//9.
--------------   NormalGSYVideoPlayer//10.ʹ���������Ű�����loading�Ĳ�����
--------------   EmptyControlVideo extends StandardGSYVideoPlayer(�Զ���)//���κο���ui�Ĳ���
--------------   MultiSampleVideo(�Զ���)//���ͬʱ���ŵĲ��ſؼ�
--------------   SampleVideo extends StandardGSYVideoPlayer(�Զ���)//�����������demo�����л���ȫ��������
--------------   SampleControlVideo extends StandardGSYVideoPlayer(�Զ���)//�����������demo�����л���ȫ��������
----------------   CustomRenderVideoPlayer(�Զ���)//�Զ�����Ⱦ�ؼ�
----------------   PreViewGSYVideoPlayer extends NormalGSYVideoPlayer(�Զ���)//����ͼСͼԤ��������ʵ��
--------------   LandLayoutVideo(�Զ���)//CustomGSYVideoPlayer �������У�����ʹ�õ�ʱ��ʹ�� StandardGSYVideoPlayer
--------------   FloatingVideo(�Զ���)//�ര���µ�������ҳ��֧��Video
--------------   GSYExo2PlayerView(�Զ���)//�Զ���View֧��exo��list���ݣ�ʵ���޷��л�Ч��
--------------   ListADVideoPlayer(�Զ���)
----------------   RequestListADVideoPlayer(�Զ���)
--------------   SampleCoverVideo extends StandardGSYVideoPlayer(�Զ���)//������
--------------   SmartPickVideo(�Զ���)//�޷��л���Ƶ��DEMO.�������л������ȣ���΢�޸���Ҳ������Ϊ�л���һ����


3.Activity
GSYBaseActivityDetail<T extends GSYBaseVideoPlayer>//����ģʽ����ҳ�������
public void initVideo();//ѡ����ͨģʽ
public void initVideoBuilderMode();//ѡ��builderģʽ
public void showFull();//ȫ��
public void onBackPressed();
public void onConfigurationChanged(Configuration newConfig);
public void onStartPrepared(String url, Object... objects);//��ʼ���أ�objects[0]��title��object[1]�ǵ�ǰ������������ȫ�����ȫ����
public void onPrepared(String url, Object... objects);//���سɹ�
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
public abstract T getGSYVideoPlayer();//���󷽷�, ���ſؼ�
public abstract GSYVideoOptionBuilder getGSYVideoOptionBuilder();//���ò�����
public abstract void clickForFullScreen();//�����ȫ��
public abstract boolean getDetailOrientationRotateAuto();//�Ƿ�������ת������true��ʾ����
public boolean isAutoFullWithSize();//�Ƿ������Ƶ�ߴ磬�Զ�ѡ������ȫ�����ߺ���ȫ����ע�⣬��ʱ��Ĭ����ת��Ч

GSYBaseADActivityDetail<T extends GSYBaseVideoPlayer, R extends GSYADVideoPlayer> extends GSYBaseActivityDetail<T>//����ADģʽ����ҳ�������
public void startAdPlay();//��ʾ���Ź��
public void showADFull();//�����Ƶ��ȫ����ʾ
public abstract R getGSYADVideoPlayer();
public abstract GSYVideoOptionBuilder getGSYADVideoOptionBuilder();//����AD������
public abstract boolean isNeedAdOnStart();//�Ƿ񲥷ſ�ʼ���


4.���������� https://github.com/CarGuo/GSYVideoPlayer/wiki/%E5%9F%BA%E7%A1%80Player-API
1.GSYTextureRenderView//1.����View
public GSYVideoGLView.ShaderInterface getEffectFilter();//GL���˾�
public GSYRenderView getRenderProxy();//��ȡ��Ⱦ�Ĵ����
public void setEffectFilter(GSYVideoGLView.ShaderInterface effectFilter);//�����˾�Ч��
public void setMatrixGL(float[] matrixGL);//GLģʽ�µĻ���matrixЧ��, matrixGL 16λ����
public void setCustomGLRenderer(GSYVideoGLViewBaseRender renderer);//�Զ���GL����Ⱦrender
public void setGLRenderMode(int mode);//GL���ֵĻ���ģʽ�����ò��ּ����С����ʹ��render�����С, MODE_LAYOUT_SIZE = 0,  MODE_RENDER_SIZE = 1


2.GSYVideoView//2.��Ƶ�ص���״̬�������ز�
public int getCurrentVideoWidth();
public int getCurrentVideoHeight();
public int getVideoSarNum();
public int getVideoSarDen();
public boolean setUp(String url, boolean cacheWithPlay, String title);//���ò���URL, cacheWithPlay:�Ƿ�߲��߻���
//cachePath:����·���������M3U8����HLS��������Ϊfalse                  mapHeadData:ͷ����Ϣ
public boolean setUp(String url, boolean cacheWithPlay, File cachePath, Map<String, String> mapHeadData, String title);
public boolean setUp(String url, boolean cacheWithPlay, File cachePath, String title);
public void onVideoReset();//����
public void onVideoPause();//��ͣ״̬
public void onVideoResume();//�ָ���ͣ״̬
public void onVideoResume(boolean seek);//�ָ���ͣ״̬, seek �Ƿ����seek����
public void onPrepared();
public void onAutoCompletion();
public void onCompletion();
public void onSeekComplete();
public void onError(int what, int extra);
public void onInfo(int what, int extra);
public void onVideoSizeChanged();
public void clearCurrentCache();//�����ǰ����
public int getCurrentPositionWhenPlaying();//��ȡ��ǰ���Ž���
public int getDuration();//��ȡ��ǰ��ʱ��
public void release();//�ͷŰ�
public void startAfterPrepared();//prepared�ɹ�֮��Ὺʼ����
public boolean isInPlayingState();//����״̬�ж��Ƿ񲥷���
public String getPlayTag();//����tag��ֹ������Ϊ��ͨ��urlҲ�����ظ�
public void setPlayTag(String playTag);//����tag��ֹ������Ϊ��ͨ��urlҲ�����ظ�(�ɴ���position)
public int getPlayPosition();
public void setPlayPosition(int playPosition);//���ò���λ�÷�ֹ��λ
public long getNetSpeed();//�����ٶ�.ע�⣬��������ǿ����˻��棬��Ϊ��ȡ���ش�������ɹ����Ǵ����ٶȵ�. �ٴ��Ѿ�����ı����ļ��������ٶȲŻ��0.��Ϊ�ǲ��ű����ļ���
public String getNetSpeedText();//�����ٶ�, ͬ��
public long getSeekOnStart();
public void setSeekOnStart(long seekOnStart);//�����￪ʼ����. Ŀǰ��ʱ��ǰ�������������⣬����. ��Ҫ��startPlayLogic֮ǰ�������ſ�ʼ֮ǰ
public int getBuffterPoint();//�������/�������
public boolean isIfCurrentIsFullscreen();//�Ƿ�ȫ��
public void setIfCurrentIsFullscreen(boolean ifCurrentIsFullscreen);//
public boolean isLooping();
public void setLooping(boolean looping);//����ѭ��
public void setVideoAllCallBack(VideoAllCallBack mVideoAllCallBack);//���ò��Ź����еĻص�
public float getSpeed();
public void setSpeed(float speed);//�����ٶ�
public void setSpeed(float speed, boolean soundTouch);//soundTouch �Ƿ��6.0�¿������ٲ����
public void setSpeedPlaying(float speed, boolean soundTouch);//��������Ч�Ĳ�������
public boolean isShowPauseCover();
public void setShowPauseCover(boolean showPauseCover);//�Ƿ���Ҫ������ʾ��ͣ��coverͼƬ
public void seekTo(long position);
public boolean isStartAfterPrepared();
public void setStartAfterPrepared(boolean startAfterPrepared);//׼���ɹ�֮����������. Ĭ��true��false��ʱ����Ҫ��prepared�����startAfterPrepared()
public boolean isReleaseWhenLossAudio();
public void setReleaseWhenLossAudio(boolean releaseWhenLossAudio);//��ʱ��ʧȥ��Ƶ���㣬��ͣ������. Ĭ��true��false��ʱ��ֻ����ͣ
public Map<String, String> getMapHeadData();
public void setMapHeadData(Map<String, String> headData);//��������mapHeader
public String getOverrideExtension();
public void setOverrideExtension(String overrideExtension);//�Ƿ���Ҫ������չ���ͣ�Ŀǰֻ���exoPlayer�ں�ģʽ��Ч. overrideExtension ���紫�� m3u8,mp4,avi ������


3.GSYVideoControlView//3.����UI����ʾ�����Ʋ㡢���ƴ����
public void onStopTrackingTouch(SeekBar seekBar);//�϶�������
public boolean setUpLazy(String url, boolean cacheWithPlay, File cachePath, Map<String, String> mapHeadData, String title);//�ڵ�����ŵ�ʱ��Ž�������setup(startPlayLogic())
public void initUIState();//��ʼ��Ϊ����״̬
public RelativeLayout getThumbImageViewLayout();//���沼��
public View getThumbImageView();
public void setThumbImageView(View view);//���÷���
public void clearThumbImageView();//�������
public TextView getTitleTextView();//title
public View getStartButton();//��ȡ���Ű���
public ImageView getFullscreenButton();//��ȡȫ������
public ImageView getBackButton();//��ȡ���ذ���
public int getEnlargeImageRes();
public void setEnlargeImageRes(int mEnlargeImageRes);//�������½�"��ʾ�л���ȫ��"�İ�����Դ,������setUp֮ǰ����,������ʹ��Ĭ��
public int getShrinkImageRes();
public void setShrinkImageRes(int mShrinkImageRes);//�������½�"��ʾ�˳�ȫ��"�İ�����Դ,������setUp֮ǰ����,������ʹ��Ĭ��
public void setIsTouchWigetFull(boolean isTouchWigetFull);//�Ƿ����ȫ����������ı���ȣ�������. Ĭ�� true
public void setThumbPlay(boolean thumbPlay);//�Ƿ���������Բ���
public boolean isHideKey();
public void setHideKey(boolean hideKey);//ȫ���������ⰴ����Ĭ�ϴ�
public boolean isNeedShowWifiTip();
public boolean isTouchWiget();
public void setIsTouchWiget(boolean isTouchWiget);//�Ƿ���Ի�������ı���ȣ������� Ĭ��true
public boolean isTouchWigetFull();
public void setNeedShowWifiTip(boolean needShowWifiTip);//�Ƿ���Ҫ��ʾ������ʾ,Ĭ��true
public void setSeekRatio(float seekRatio);//����������������ı���. Ĭ��1����ֵԽ�󣬻����Ĳ�����seekԽС
public float getSeekRatio();
public boolean isNeedLockFull();
public void setNeedLockFull(boolean needLoadFull);//�Ƿ���Ҫȫ��������Ļ����. �������ʹ��������setIfCurrentIsFullscreenΪtrue
public void setLockClickListener(LockClickListener lockClickListener);//�������
public void setDismissControlTime(int dismissControlTime);//���ô�����ʾ����ui����ʧʱ��. ���룬Ĭ��2500
public int getDismissControlTime();
public void setGSYVideoProgressListener(GSYVideoProgressListener videoProgressListener);//���Ȼص�


4.GSYBaseVideoPlayer//4.����ȫ����С��Ļ�߼�
public boolean isVerticalFullByVideoSize();//�Ƿ������Ƶ�ߴ磬�Զ�ѡ������ȫ�����ߺ���ȫ����ע�⣬��ʱ��Ĭ����ת��Ч
public void onConfigurationChanged(Activity activity, Configuration newConfig, OrientationUtils orientationUtils);//��ת����
//newConfig:����    orientationUtils:��ת������    hideActionBar:�Ƿ�����actionbar    hideStatusBar:�Ƿ�����statusbar
public void onConfigurationChanged(Activity activity, Configuration newConfig, OrientationUtils orientationUtils, boolean hideActionBar, boolean hideStatusBar);
//����window�㲥��ȫ��Ч��    actionBar:�Ƿ���actionBar���еĻ���Ҫ����    statusBar:�Ƿ���״̬bar���еĻ���Ҫ����
public GSYBaseVideoPlayer startWindowFullscreen(final Context context, final boolean actionBar, final boolean statusBar);
public GSYBaseVideoPlayer showSmallVideo(Point size, final boolean actionBar, final boolean statusBar);//��ʾС����
public void hideSmallVideo();//����С����
public boolean isShowFullAnimation();
public void setShowFullAnimation(boolean showFullAnimation);//ȫ������ �Ƿ�ʹ��ȫ������Ч��
public boolean isRotateViewAuto();
public void setRotateViewAuto(boolean rotateViewAuto);//�Ƿ����Զ���ת
public boolean isLockLand();
public void setLockLand(boolean lockLand);//һȫ��������������Ĭ��false�����������setRotateViewAutoʹ��
public boolean isRotateWithSystem();
public void setRotateWithSystem(boolean rotateWithSystem);//�Ƿ����ϵͳ��ת��false�Ļ���ϵͳ��ֹ��תҲ�������ת
public GSYVideoPlayer getFullWindowPlayer();//��ȡȫ������������
public GSYVideoPlayer getSmallWindowPlayer();//��ȡС���ڲ���������
public GSYBaseVideoPlayer getCurrentPlayer();//��ȡ��ǰ���ڲ��ŵĲ��ſؼ�
//ȫ�����ؼ�������������ˣ�Ĭ�Ϸ��ض�����Ч. �������ؼ���ȫ�����ذ�����ǰ������������������
public void setBackFromFullScreenListener(OnClickListener backFromFullScreenListener);
public void setFullHideActionBar(boolean actionBar);
public void setFullHideStatusBar(boolean statusBar)
public boolean isFullHideActionBar();
public boolean isFullHideStatusBar();
public int getSaveBeforeFullSystemUiVisibility();
public void setSaveBeforeFullSystemUiVisibility(int systemUiVisibility);
public boolean isAutoFullWithSize();
public void setAutoFullWithSize(boolean autoFullWithSize);//�Ƿ������Ƶ�ߴ磬�Զ�ѡ������ȫ�����ߺ���ȫ����ע�⣬��ʱ��Ĭ����ת��Ч. Ĭ��false


5.GSYVideoPlayer//5.���ݵĿ�View��Ŀǰ���� GSYVideoManager������
public GSYVideoViewBridge getGSYVideoManager();


6.StandardGSYVideoPlayer//6.��׼���������̳�֮��ʵ��һЩui��ʾЧ��������ʾ������ui�����Ű�����
public int getLayoutId();//�̳к���д���滻Ϊ����Ҫ�Ĳ���
public void startPlayLogic();//��ʼ����, ��ʾwifiȷ����??
public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar);//���Զ����Ч��Ҳ���õ�ȫ��
public void setBottomShowProgressBarDrawable(Drawable drawable, Drawable thumb);//�ײ�������-������
public void setBottomProgressBarDrawable(Drawable drawable);//�ײ�������-�ǵ���
public void setDialogVolumeProgressBar(Drawable drawable);//����������
public void setDialogProgressBar(Drawable drawable);//�м������
public void setDialogProgressColor(int highLightColor, int normalColor);//�м������������ɫ
public void taskShotPic(GSYVideoShotListener gsyVideoShotListener);//��ȡ��ͼ
public void taskShotPic(GSYVideoShotListener gsyVideoShotListener, boolean high);//��ȡ��ͼ high,�Ƿ���Ҫ�����
public void saveFrame(final File file, GSYVideoShotSaveListener gsyVideoShotSaveListener);//�����ͼ
public void saveFrame(final File file, final boolean high, final GSYVideoShotSaveListener gsyVideoShotSaveListener);//�����ͼ high,�Ƿ���Ҫ�����
//���¿������Ȳ�ѯ�Լ�����view��ʧ�Ķ�ʱ����
//���ڽ��GSYVideoHelper��ͨ��removeview��ʽ��ȫ���л����µĶ�ʱ����ֹͣ������, GSYVideoControlView   onDetachedFromWindow����
public void restartTimerTask();


7.ListGSYVideoPlayer//7.�б���֧��
public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position);//���ò���URL
public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath);
//cachePath:����·���������M3U8����HLS��������Ϊfalse    mapHeadData:http header
public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData);
public boolean playNext();//������һ��  return true��ʾ������һ��


8.GSYSampleADVideoPlayer//8.ֻ֧��ÿ��Ƭͷ��沥�ŵ���,��ʵ���Ǹ���ʵ�壬�жϲ����б����ĸ��ǹ�棬�ĸ����ǣ��Ӷ�����ͬ��UI��ʾЧ��
public boolean setAdUp(ArrayList<GSYADVideoModel> url, boolean cacheWithPlay, int position);//��Ƭͷ����
public boolean setAdUp(ArrayList<GSYADVideoModel> url, boolean cacheWithPlay, int position, File cachePath);
public boolean setAdUp(ArrayList<GSYADVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData);

  
9.GSYADVideoPlayer//9.
public GSYVideoViewBridge getGSYVideoManager();
public void removeFullWindowViewOnly();//�Ƴ���沥�ŵ�ȫ��


10.NormalGSYVideoPlayer//10.ʹ���������Ű�����loading�Ĳ�����

