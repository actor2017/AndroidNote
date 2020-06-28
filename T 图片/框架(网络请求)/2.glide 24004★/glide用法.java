https://github.com/bumptech/glide
官方中文文档:	https://muyangmin.github.io/glide-docs-cn/
中文文档:		http://mrfu.me/2016/02/27/Glide_Getting_Started/
具体更深层次用法见郭林博客

###图像库对比
* 快速加载图片推荐Glide
* 对图片质量要求较高推荐Picasso
* 如果应用加载的图片很多,推荐Fresco > Glide > Picasso

1.添加依赖
repositories {
  mavenCentral()
  google()
}
dependencies {
  implementation 'com.github.bumptech.glide:glide:4.9.0'
  annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'
}

2.添加权限
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

3.添加混淆:
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


4.加载图片(注:好像不能给imageView设置背景,不然好像显示不出来)
Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);


5.跳过缓存,拍照存储到本地,并RecyclerView显示出来,跳过缓存,否则会显示以前的照片.
Glide.with(this)
		.load(picPaths.get(position))//这里的picPaths里是String path
		.dontTransform()//加载的图片不变形(在RecyclerView中一定要设置) 这一条还没试过!!!!!!!
		.dontAnimate()//加载时不闪动
		.skipMemoryCache(true)// 跳过内存缓存
		.diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存(ALL:不跳过,NONE:跳过)
		.into(viewHolder.ivPic);


6.获取缓存大小
long length = 0;
if (photoCacheDir != null) length = photoCacheDir.length();
tvCache.setText(Formatter.formatFileSize(this, length));

7.加载视频第xx帧
Glide.with(fragment)
        .setDefaultRequestOptions(new RequestOptions()
                .frame(1000000)//加载第四秒的帧数作为封面:4000000
                .transform(new CenterCrop(), new RoundedCorners(dp3))
				.error(R.mipmap.eeeee)//可以忽略
				.placeholder(R.mipmap.ppppp)//可以忽略
        )
        .load(url)
        .into(iv);

8.圆形图片
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
Glide.with(this) // 指定Context
		.load(URL_GIF)// 指定图片的URL
		.transform(new GlideCircleTransform(this)) // 指定自定义图片样式
		.into(imageView);//指定显示图片的ImageView


9.给list
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

10.下载图片
如果要下载一个远程文件到缓存然后创建文件路径
Glide.with(fragment).downloadOnly()
// or if you have the url already:
Glide.with(fragment).download(url);

11.转 Bitmap
try {
    Bitmap bitmap = Glide.with(this).asBitmap().load("url").submit(/*width, height*/).get();//必须子线程
    File file = Glide.with(this).asFile().load("url").submit(/*width, height*/).get();
	/*...*/
} catch (ExecutionException | InterruptedException e) {
    e.printStackTrace();
}
