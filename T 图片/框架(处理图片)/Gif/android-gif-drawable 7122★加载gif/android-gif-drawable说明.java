https://github.com/koral--/android-gif-drawable
Views and Drawable for displaying animated GIFs on Android
���, ����������gifͼƬ
�б����gifò��û�Ż�

ͨ��JNI�󶨵�GIFLib������Ⱦ֡�����ַ���Ӧ�ñ�WebView��Movie�����Ч��

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


GifDrawable����:
stop() - ֹͣ���ţ����Դ��κ��̵߳���
start() - ��ʼ���ţ����Դ��κ��̵߳���
isRunning() - �Ƿ����ڲ���
reset() - ��λ�����¿�ʼ����
setLoopCount( 2 ); //���ò��ŵĴ������������˾��Զ�ֹͣ
getCurrentLoop()�� //��ȡ���ڲ��ŵĴ���
setSpeed(float factor) - �����µĶ����ٶ����ӣ����硣����2.0f��ʹ�����ٶȼӱ�
seekTo(int position)- Ѱ�Ҹ����Ķ������ڵ�ǰѭ���ڣ�position���Ժ���Ϊ��λ��
getDuration() - ��ȡ����һ������Ҫ��ʱ��
getCurrentPosition() - ��ȡ���ڵ��ӿ�ʼ������������ʱ��
gifDrawable.addAnimationListener(getAnimationListener());


private GifDrawable gifDrawable;
private AnimationListener animationListener;
//��ʾ���ﶯ��
public void showGift(SendGiftBean sendGiftBean) {
	try {
		//���ŵ�2��Gifʱ, ȷ����һ��Gif��AnimationListener����ص�
		//���һ��ҳ��ֻ����һ��gif, ���õ����������
		if (gifDrawable != null) gifDrawable.stop();
		Uri uri = Uri.fromFile(new File(FileUtils.getExternalStorageDir() + File.separator + FileUtils.getFileNameFromUrl(sendGiftBean.getGiftImg())));
		ivGiftShow.setImageURI(uri);
		ivGiftShow.setWillNotDraw(false);
		gifDrawable = (GifDrawable) ivGiftShow.getDrawable();
		gifDrawable.setLoopCount(2);//���������, ��һֱѭ������
		//remove��, AnimationListener���ǻ�ص�2��(��ʹ��new��Ҳ������)
		//���һ��ҳ��ֻ����һ��gif, ���õ����������
		gifDrawable.removeAnimationListener(animationListener);//��bug...
		animationListener = null;
		gifDrawable.addAnimationListener(getAnimationListener());
		//���һ��ҳ��ֻ����һ��gif, ���õ����������
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
				if (loopNumber == 1) {//��0��ʼ
					if (ivGiftShow != null) ivGiftShow.setWillNotDraw(true);//���ͼƬ
				}
			}
		};
	}
	return animationListener;
}
@Override
protected void onStop() {
	if (gifDrawable != null) gifDrawable.stop();
	ivGiftShow.setWillNotDraw(true);//���ͼƬ
}
@Override
protected void onDestroy() {
	super.onDestroy();
    if (gifDrawable != null) {
        gifDrawable.removeAnimationListener(animationListener);
        gifDrawable.recycle();
    }
}
