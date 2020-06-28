https://github.com/bumptech/glide
�ٷ������ĵ�:	https://muyangmin.github.io/glide-docs-cn/
�����ĵ�:		http://mrfu.me/2016/02/27/Glide_Getting_Started/
����������÷������ֲ���

###ͼ���Ա�
* ���ټ���ͼƬ�Ƽ�Glide
* ��ͼƬ����Ҫ��ϸ��Ƽ�Picasso
* ���Ӧ�ü��ص�ͼƬ�ܶ�,�Ƽ�Fresco > Glide > Picasso

1.�������
repositories {
  mavenCentral()
  google()
}
dependencies {
  implementation 'com.github.bumptech.glide:glide:4.9.0'
  annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
}

2.���Ȩ��
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

3.��ӻ���:
##------------Begin: proguard configuration for Glide------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
##-------------End: proguard configuration for Glide-------------


4.����ͼƬ(ע:�����ܸ�imageView���ñ���,��Ȼ������ʾ������)
Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);


5.��������,���մ洢������,��RecyclerView��ʾ����,��������,�������ʾ��ǰ����Ƭ.
Glide.with(this)
		.load(picPaths.get(position))//�����picPaths����String path
		.dontTransform()//���ص�ͼƬ������(��RecyclerView��һ��Ҫ����) ��һ����û�Թ�!!!!!!!
		.dontAnimate()//����ʱ������
		.skipMemoryCache(true)// �����ڴ滺��
		.diskCacheStrategy(DiskCacheStrategy.NONE)//�������̻���(ALL:������,NONE:����)
		.into(viewHolder.ivPic);


6.��ȡ�����С
long length = 0;
if (photoCacheDir != null) length = photoCacheDir.length();
tvCache.setText(Formatter.formatFileSize(this, length));

7.������Ƶ��xx֡
Glide.with(fragment)
        .setDefaultRequestOptions(new RequestOptions()
                .frame(1000000)//���ص������֡����Ϊ����:4000000
                .transform(new CenterCrop(), new RoundedCorners(dp3))
				.error(R.mipmap.eeeee)//���Ժ���
				.placeholder(R.mipmap.ppppp)//���Ժ���
        )
        .load(url)
        .into(iv);

8.Բ��ͼƬ
class GlideCircleTransform extends BitmapTransformation {
	public GlideCircleTransform(Context context) {
		super(context);
	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
		return circleCrop(pool, toTransform);
	}

	private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
		if (source == null) return null;

		int size = Math.min(source.getWidth(), source.getHeight());
		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;

		// TODO this could be acquired from the pool too
		Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

		Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		float r = size / 2f;
		canvas.drawCircle(r, r, r, paint);
		return result;
	}

	@Override
	public String getId() {
		return getClass().getName();
	}
}
Glide.with(this) // ָ��Context
		.load(URL_GIF)// ָ��ͼƬ��URL
		.transform(new GlideCircleTransform(this)) // ָ���Զ���ͼƬ��ʽ
		.into(imageView);//ָ����ʾͼƬ��ImageView


9.��list
// For a list:
@Override
public View getView(int position, View recycled, ViewGroup container) {
	final ImageView myImageView;
	if (recycled == null) {
		myImageView = (ImageView) inflater.inflate(R.layout.my_image_view,
				container, false);
	} else {
		myImageView = (ImageView) recycled;
	}
	Glide.with(activity)
		.load(urls.get(position))
		.into(myImageView);
	return myImageView;
}

10.����ͼƬ
���Ҫ����һ��Զ���ļ�������Ȼ�󴴽��ļ�·��
Glide.with(fragment).downloadOnly()
// or if you have the url already:
Glide.with(fragment).download(url);

11.ת Bitmap
try {
    Bitmap bitmap = Glide.with(this).asBitmap().load("url").submit(/*width, height*/).get();//�������߳�
    File file = Glide.with(this).asFile().load("url").submit(/*width, height*/).get();
	/*...*/
} catch (ExecutionException | InterruptedException e) {
    e.printStackTrace();
}
