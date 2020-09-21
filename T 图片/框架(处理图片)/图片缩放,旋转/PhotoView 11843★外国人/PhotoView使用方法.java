https://github.com/chrisbanes/PhotoView
�����°��sdk��android-23��(android 6.0)����һ��ԭ����photoView�����Ҵ���ʵ��Ҳ�ܼ򵥣��߼�Ҳ��������?����?

��֧��Fresco.

0.Add this in your root build.gradle file (not your module build.gradle file):
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}

1.dependencies {
    compile 'com.github.chrisbanes.photoview:library:1.2.4'
}

�ص�:
Features

Out of the box zooming, using multi-touch and double-tap.
Scrolling, with smooth scrolling fling.
Works perfectly when used in a scrolling parent (such as ViewPager).
Allows the application to be notified when the displayed Matrix has changed. Useful for when you need to update your UI based on the current zoom/scroll position.
Allows the application to be notified when the user taps on the Photo.
����
�ö�㴥����˫���������š�
����������������
���ڹ����ĸ�����ʹ��ʱ(����view��ҳ��)�������ǳ�������
����ʾ�ľ������仯ʱ������Ӧ�ó���֪ͨ��������Ҫ���ݵ�ǰ������/����λ�ø������UIʱ��������á�
���û������Ƭʱ������Ӧ�ó���֪ͨ��


2.�����ļ�����PhotoView
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



3.������
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Any implementation of ImageView can be used!ֻҪʵ����imageview������
        mImageView = (ImageView) findViewById(R.id.iv_photo);

        // Set the Drawable displayed������ʾ�ڻ�ͼ
        Drawable bitmap = getResources().getDrawable(R.drawable.search_empty);
        mImageView.setImageDrawable(bitmap);

        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        //not needed unless you are going to change the drawable later
        //����һ��PhotoViewAttacher,�������е����Ź��ܡ�����Ҫ,�������Ժ��ı��
        mAttacher = new PhotoViewAttacher(mImageView);		//���ֻҪ��һ��Ϳ�������
    }

    // If you later call mImageView.setImageDrawable/setImageBitmap/setImageResource/etc
    // then you just need to call
    //������Ժ����mImageView��setImageDrawable / setImageBitmap / setImageResource /�ȵ�,
    // ��ô��ֻ��Ҫ����
    //mAttacher.update();
}

4.ViewPager��PhotoView�����������������������������������
�ص�:PhotoView photoView = new PhotoView(BigPhotosActivity.this);
	��:��ImageView����PhotoView����,PhotoView �̳��� ImageView
//######################################################################################
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(BigPhotosActivity.this);
                Glide
                        .with(BigPhotosActivity.this) // ָ��Context
                        .load(GlobalConstants.PREF_PIC_URL_PREFIX+bigImages[vp_viewpager.getCurrentItem()])// ָ��ͼƬ��URL
                        .placeholder(R.mipmap.ic_launcher)// ָ��ͼƬδ�ɹ�����ǰ��ʾ��ͼƬ
                        .error(R.mipmap.ic_launcher)// ָ��ͼƬ����ʧ����ʾ��ͼƬ
                        //.override(300, 300)//ָ��ͼƬ�ĳߴ�(�����ÿ�ע��)
                        .skipMemoryCache(false)// �����ڴ滺��
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//�������̻���(ALL:������,NONE:����)
                        //.transform(new GlideRoundTransform(this,50))//Բ��ͼƬ,Բ��ͼƬ...
                        .into(photoView);//ָ����ʾͼƬ��ImageView

                container.addView(photoView);

                return photoView;
            }
        });

#######################################################################################
�����viewgroup

��һЩViewGroups����Щ����onintercepttouchevent�����׳��쳣ʱPhotoView���������ǣ�������ViewPager�Ͱ�������һ�����������δ�����Ϊ�˷�ֹ�������⣨��ͨ�������ڵ�����С��������hackydrawerlayout����Կ����Ľ�������Ǽ򵥵ز�׽�쳣���κ�ʹ��onintercepttouchevent ViewGroupҲ��Ҫ��չ�Ͳ�����쳣��ʹ��hackydrawerlayout��Ϊһ��ģ����ô��������ʵ�֣�
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

