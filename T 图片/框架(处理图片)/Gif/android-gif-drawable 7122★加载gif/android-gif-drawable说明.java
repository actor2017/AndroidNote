https://github.com/koral--/android-gif-drawable
Views and Drawable for displaying animated GIFs on Android
最快, 但需先下载gif图片
列表加载gif貌似没优化

通过JNI绑定的GIFLib用于渲染帧。这种方法应该比WebView或Movie类更有效。

//GifImageButton 
<pl.droidsonroids.gif.GifImageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:src="@drawable/src_anim"
    android:background="@drawable/bg_anim"
    />

<pl.droidsonroids.gif.GifTextView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:drawableTop="@drawable/left_anim"
    android:drawableStart="@drawable/left_anim"
    android:background="@drawable/bg_anim"
    />


//asset file
GifDrawable gifFromAssets = new GifDrawable( getAssets(), "anim.gif" );
		
//resource (drawable or raw)
GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.anim );
		
//Uri
ContentResolver contentResolver = ... //can be null for file:// Uris
GifDrawable gifFromUri = new GifDrawable( contentResolver, gifUri );

//byte array
byte[] rawGifBytes = ...
GifDrawable gifFromBytes = new GifDrawable( rawGifBytes );
		
//FileDescriptor
FileDescriptor fd = new RandomAccessFile( "/path/anim.gif", "r" ).getFD();
GifDrawable gifFromFd = new GifDrawable( fd );
		
//file path
GifDrawable gifFromPath = new GifDrawable( "/path/anim.gif" );
		
//file
File gifFile = new File(getFilesDir(),"anim.gif");
GifDrawable gifFromFile = new GifDrawable(gifFile);
		
//AssetFileDescriptor
AssetFileDescriptor afd = getAssets().openFd( "anim.gif" );
GifDrawable gifFromAfd = new GifDrawable( afd );
				
//InputStream (it must support marking)
InputStream sourceIs = ...
BufferedInputStream bis = new BufferedInputStream( sourceIs, GIF_LENGTH );
GifDrawable gifFromStream = new GifDrawable( bis );
		
//direct ByteBuffer
ByteBuffer rawGifBytes = ...
GifDrawable gifFromBytes = new GifDrawable( rawGifBytes );


GifDrawable方法:
stop() - 停止播放，可以从任何线程调用
start() - 开始播放，可以从任何线程调用
isRunning() - 是否正在播放
reset() - 复位，重新开始播放
setLoopCount( 2 ); //设置播放的次数，播放完了就自动停止
getCurrentLoop()； //获取正在播放的次数
setSpeed(float factor) - 设置新的动画速度因子，例如。传递2.0f将使动画速度加倍
seekTo(int position)- 寻找给定的动画（在当前循环内）position（以毫秒为单位）
getDuration() - 获取播放一次所需要的时间
getCurrentPosition() - 获取现在到从开始播放所经历的时间
gifDrawable.addAnimationListener(getAnimationListener());


private GifDrawable gifDrawable;
private AnimationListener animationListener;
//显示礼物动画
public void showGift(SendGiftBean sendGiftBean) {
	try {
		//播放第2个Gif时, 确保第一个Gif的AnimationListener不会回调
		//如果一个页面只播放一次gif, 不用调用这个方法
		if (gifDrawable != null) gifDrawable.stop();
		Uri uri = Uri.fromFile(new File(FileUtils.getExternalStorageDir() + File.separator + FileUtils.getFileNameFromUrl(sendGiftBean.getGiftImg())));
		ivGiftShow.setImageURI(uri);
		ivGiftShow.setWillNotDraw(false);
		gifDrawable = (GifDrawable) ivGiftShow.getDrawable();
		gifDrawable.setLoopCount(2);//如果不设置, 会一直循环播放
		//remove后, AnimationListener还是会回调2次(即使新new的也会这样)
		//如果一个页面只播放一次gif, 不用调用这个方法
		gifDrawable.removeAnimationListener(animationListener);//有bug...
		animationListener = null;
		gifDrawable.addAnimationListener(getAnimationListener());
		//如果一个页面只播放一次gif, 不用调用这个方法
		gifDrawable.start();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
private AnimationListener getAnimationListener() {
	if (animationListener == null) {
		animationListener = new AnimationListener() {
			@Override
			public void onAnimationCompleted(int loopNumber) {
				if (loopNumber == 1) {//从0开始
					if (ivGiftShow != null) ivGiftShow.setWillNotDraw(true);//清空图片
				}
			}
		};
	}
	return animationListener;
}
@Override
protected void onStop() {
	if (gifDrawable != null) gifDrawable.stop();
	ivGiftShow.setWillNotDraw(true);//清空图片
}
@Override
protected void onDestroy() {
	super.onDestroy();
    if (gifDrawable != null) {
        gifDrawable.removeAnimationListener(animationListener);
        gifDrawable.recycle();
    }
}
