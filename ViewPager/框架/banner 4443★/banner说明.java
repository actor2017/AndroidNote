https://github.com/youth5201314/banner

��Ŀ�е�bug:��Fragment �� ViewPagerǶ��Fragment�б����Բ�㶼����ʾ, �ر��Ǳ���...
Android���ͼƬ�ֲ��ؼ�,֧������ѭ���Ͷ�������,������������ֲ���ʽ���������ֲ����л�ʱ�䡢λ�á�ͼƬ���ؿ�ܵ�!


2.      ��������                                     ����                          ��������
BannerConfig.NOT_INDICATOR                    ����ʾָʾ���ͱ���                setBannerStyle
BannerConfig.CIRCLE_INDICATOR                 ��ʾԲ��ָʾ�� 	                setBannerStyle
BannerConfig.NUM_INDICATOR                    ��ʾ����ָʾ�� 	                setBannerStyle
BannerConfig.NUM_INDICATOR_TITLE              ��ʾ����ָʾ���ͱ���              setBannerStyle
BannerConfig.CIRCLE_INDICATOR_TITLE           ��ʾԲ��ָʾ���ͱ��⣨��ֱ��ʾ��  setBannerStyle
BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE    ��ʾԲ��ָʾ���ͱ��⣨ˮƽ��ʾ��  setBannerStyle

BannerConfig.LEFT                             ָʾ������                        setIndicatorGravity
BannerConfig.CENTER                           ָʾ������                        setIndicatorGravity
BannerConfig.RIGHT                            ָʾ������                        setIndicatorGravity


3.���������ࣨsetBannerAnimation�������ã�
    ��������
Transformer.Default
Transformer.Accordion
Transformer.BackgroundToForeground
Transformer.ForegroundToBackground
Transformer.CubeIn
Transformer.CubeOut
Transformer.DepthPage
Transformer.FlipHorizontal
Transformer.FlipVertical
Transformer.RotateDown
Transformer.RotateUp
Transformer.ScaleInOut
Transformer.Stack
Transformer.Tablet
Transformer.ZoomIn
Transformer.ZoomOut
Transformer.ZoomOutSlide

4.����
������                                                ����                                        �汾����
setBannerStyle(int bannerStyle) 	       �����ֲ���ʽ��Ĭ��ΪCIRCLE_INDICATOR��                ��
setIndicatorGravity(int type)                  ����ָʾ��λ�ã�û�б���Ĭ��Ϊ�ұ�,�б���ʱĬ����ߣ� ��
isAutoPlay(boolean isAutoPlay)                 �����Ƿ��Զ��ֲ���Ĭ���Զ���                          ��
setViewPagerIsScroll(boolean isScroll)         �����Ƿ������ֶ������ֲ�ͼ��Ĭ��true��            1.4.5��ʼ
update(List<?> imageUrls,List titles)          ����ͼƬ�ͱ���                                    1.4.5��ʼ
update(List<?> imageUrls)                      ����ͼƬ                                          1.4.5��ʼ
startAutoPlay()                                ��ʼ�ֲ�            1.4��ʼ���˷���ֻ������banner�������-->��Ҫ��start()��ִ��
stopAutoPlay()                                 �����ֲ�            1.4��ʼ���˷���ֻ������banner�������-->��Ҫ��start()��ִ��
start()                                        ��ʼ����banner��Ⱦ                                1.4��ʼ
setOffscreenPageLimit(int limit)               ͬviewpager�ķ�������һ��                         1.4.2��ʼ
setBannerTitles(List titles)                   �����ֲ�Ҫ��ʾ�ı����ͼƬ��Ӧ���������Ĭ�ϲ���ʾ���⣩ 1.4��ʼ
setDelayTime(int time)                         �����ֲ�ͼƬ���ʱ�䣨��λ���룬Ĭ��Ϊ2000��             ��
setImages(Object[]/List<?> imagesUrl)                               �����ֲ�ͼƬ(�������ò������������ڴ˷���֮ǰִ��)  1.4��ȥ�����鴫��
setOnBannerListener(this)                                           ���õ���¼����±��Ǵ�0��ʼ                     1.4.9�Ժ�
setImageLoader(Object implements ImageLoader)                       ����ͼƬ������                                1.4��ʼ
setOnPageChangeListener(this)                                       ����viewpager�Ļ�������                          ��
setBannerAnimation(Class<? extends PageTransformer> transformer)                ����viewpager��Ĭ�϶���,��ֵ�������� ��
setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer)  ����viewpager���Զ��嶯��    ��


5.Attributes���ԣ�banner�����ļ��е��ã�
Attributes 			  forma 	describe
delay_time 			integer 	�ֲ����ʱ�䣬Ĭ��2000
scroll_time 			integer 	�ֲ�����ִ��ʱ�䣬Ĭ��800
is_auto_play 			boolean 	�Ƿ��Զ��ֲ���Ĭ��true
title_background 		color 		reference
title_textcolor 		color 		����������ɫ
title_textsize 			dimension 	���������С
title_height 			dimension 	�������߶�
indicator_width 		dimension 	ָʾ��Բ�ΰ�ť�Ŀ��
indicator_height 		dimension 	ָʾ��Բ�ΰ�ť�ĸ߶�
indicator_margin 		dimension 	ָʾ��֮��ļ��
indicator_drawable_selected 	reference 	ָʾ��ѡ��Ч��
indicator_drawable_unselected 	reference 	ָʾ��δѡ��Ч��
image_scale_type 		enum 		��imageview��ScaleType����һ��


6.ʹ�ò���
Step 1.����banner
compile 'com.youth.banner:banner:1.4.9'  //���°汾


Step 2.���Ȩ�޵���� AndroidManifest.xml
<!-- if you want to load images from the internet -->
<uses-permission android:name="android.permission.INTERNET" /> 
<!-- if you want to load images from a file OR from the internet -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />


Step 3.�ڲ����ļ������Banner�����������Զ�������
�������˲������ʡ�ԣ�ֱ����Activity����Fragment��new Banner();
<com.youth.banner.Banner
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/banner"
    android:layout_width="match_parent"
    android:layout_height="�߶��Լ�����" />


Step 4.��дͼƬ������
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
          ע�⣺
          1.ͼƬ���������Լ�ѡ�����ﲻ���ƣ�ֻ���ṩ����ʹ�÷���
          2.���ص�ͼƬ·��ΪObject���ͣ����ڲ���ȷ���㵽��ʹ�õ�����ͼƬ��������
          ����ĵ�����ʲô��ʽ����ô���־�ʹ��Object���պͷ��أ���ֻ��Ҫǿת���㴫������;��У�
          �мǲ�Ҫ����ǿת��
         */
        eg��
        
        //Glide ����ͼƬ���÷�
        Glide.with(context).load(path).into(imageView);

        //Picasso ����ͼƬ���÷�
        Picasso.with(context).load(path).into(imageView);
        
        //��fresco����ͼƬ���÷����ǵ�Ҫд�����createImageView����
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }
    
    //�ṩcreateImageView ������������ÿ��Բ���д�����������Ҫ�Ƿ����Զ���ImageView�Ĵ���
    @Override
    public ImageView createImageView(Context context) {
        //ʹ��fresco����Ҫ�������ṩ��ImageView����Ȼ��Ҳ�������Լ��Զ���ľ���ͼƬ���ع��ܵ�ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;
    }
}


Step 5.��Activity����Fragment������Banner
--------------------------��ʹ��-------------------------------
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Banner banner = (Banner) findViewById(R.id.banner);
    //����ͼƬ������
    banner.setImageLoader(new GlideImageLoader());
    //����ͼƬ����
    banner.setImages(images);
    //banner���÷���ȫ���������ʱ������(���ظ�����, ����ˢ��ʱ)
    banner.start();
}
--------------------------��ϸʹ��-------------------------------
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Banner banner = (Banner) findViewById(R.id.banner);
    //����banner��ʽ
    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
    //����ͼƬ������
    banner.setImageLoader(new GlideImageLoader());
    //����ͼƬ����
    banner.setImages(images);
    //����banner����Ч��
    banner.setBannerAnimation(Transformer.DepthPage);
    //���ñ��⼯�ϣ���banner��ʽ����ʾtitleʱ��
    banner.setBannerTitles(titles);
    //�����Զ��ֲ���Ĭ��Ϊtrue
    banner.isAutoPlay(true);
    //�����ֲ�ʱ��
    banner.setDelayTime(1500);
    //����ָʾ��λ�ã���bannerģʽ����ָʾ��ʱ��
    banner.setIndicatorGravity(BannerConfig.CENTER);
    //banner���÷���ȫ���������ʱ������
    banner.start();
}
-----------------��Ȼ�������͵����Ҳ������ô��--------------------
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Banner banner = (Banner) findViewById(R.id.banner);
    banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
}


Step 6.����ѡ����������
//�������Ҫ���Ǹ��õ����飬������ô����
@Override
protected void onStart() {
    super.onStart();
    //��ʼ�ֲ�
    banner.startAutoPlay();
}

@Override
protected void onStop() {
    super.onStop();
    //�����ֲ�
    banner.stopAutoPlay();
}


��������
# glide �Ļ�������
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
##------------Begin: proguard configuration for banner-----------
-keep class com.youth.banner.** {
    *;
 }
##------------End: proguard configuration for banner-------------
