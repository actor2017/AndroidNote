https://github.com/chrisbanes/PhotoView
在最新版的sdk（android-23）(android 6.0)有了一个原生的photoView，并且代码实现也很简单，逻辑也很清晰。?有吗?

不支持Fresco.

0.Add this in your root build.gradle file (not your module build.gradle file):
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}

1.dependencies {
    compile 'com.github.chrisbanes.photoview:library:1.2.4'
}

特点:
Features

Out of the box zooming, using multi-touch and double-tap.
Scrolling, with smooth scrolling fling.
Works perfectly when used in a scrolling parent (such as ViewPager).
Allows the application to be notified when the displayed Matrix has changed. Useful for when you need to update your UI based on the current zoom/scroll position.
Allows the application to be notified when the user taps on the Photo.
特性
用多点触屏和双触点来缩放。
滚动，滚动流畅。
当在滚动的父类中使用时(比如view分页器)，工作非常完美。
当显示的矩阵发生变化时，允许应用程序被通知。当你需要根据当前的缩放/滚动位置更新你的UI时，这很有用。
当用户点击照片时，允许应用程序被通知。


2.布局文件中是PhotoView
<com.github.chrisbanes.photoview.PhotoView	//1.2.4
    android:id="@+id/photo_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>

<uk.co.senab.photoview.PhotoView			//1.2.6
    android:id="@+id/photoview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:scaleType="fitXY"
    />

PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
photoView.setImageResource(R.drawable.image);



3.代码中
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Any implementation of ImageView can be used!只要实现了imageview都能用
        mImageView = (ImageView) findViewById(R.id.iv_photo);

        // Set the Drawable displayed设置显示在绘图
        Drawable bitmap = getResources().getDrawable(R.drawable.search_empty);
        mImageView.setImageDrawable(bitmap);

        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        //not needed unless you are going to change the drawable later
        //附加一个PhotoViewAttacher,负责所有的缩放功能。不需要,除非你以后会改变的
        mAttacher = new PhotoViewAttacher(mImageView);		//★就只要这一句就可以缩放
    }

    // If you later call mImageView.setImageDrawable/setImageBitmap/setImageResource/etc
    // then you just need to call
    //如果你以后调用mImageView。setImageDrawable / setImageBitmap / setImageResource /等等,
    // 那么你只需要调用
    //mAttacher.update();
}

4.ViewPager中PhotoView★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
重点:PhotoView photoView = new PhotoView(BigPhotosActivity.this);
	即:把ImageView换成PhotoView即可,PhotoView 继承了 ImageView
//######################################################################################
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(BigPhotosActivity.this);
                Glide
                        .with(BigPhotosActivity.this) // 指定Context
                        .load(GlobalConstants.PREF_PIC_URL_PREFIX+bigImages[vp_viewpager.getCurrentItem()])// 指定图片的URL
                        .placeholder(R.mipmap.ic_launcher)// 指定图片未成功加载前显示的图片
                        .error(R.mipmap.ic_launcher)// 指定图片加载失败显示的图片
                        //.override(300, 300)//指定图片的尺寸(不设置可注销)
                        .skipMemoryCache(false)// 跳过内存缓存
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//跳过磁盘缓存(ALL:不跳过,NONE:跳过)
                        //.transform(new GlideRoundTransform(this,50))//圆形图片,圆角图片...
                        .into(photoView);//指定显示图片的ImageView

                container.addView(photoView);

                return photoView;
            }
        });

#######################################################################################
问题的viewgroup

有一些ViewGroups（那些利用onintercepttouchevent），抛出异常时PhotoView放置在他们，尤其是ViewPager和安。这是一个框架问题尚未解决。为了防止这种例外（这通常发生在当你缩小），看看hackydrawerlayout你可以看到的解决方案是简单地捕捉异常。任何使用onintercepttouchevent ViewGroup也需要扩展和捕获的异常。使用hackydrawerlayout作为一个模板怎么做。基本实现：
public class HackyProblematicViewGroup extends ProblematicViewGroup {

    public HackyProblematicViewGroup(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
                        //uncomment if you really want to see these errors
            //e.printStackTrace();
            return false;
        }
    }
}

