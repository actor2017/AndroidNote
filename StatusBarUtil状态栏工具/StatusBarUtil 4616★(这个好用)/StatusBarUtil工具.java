https://github.com/laobie/StatusBarUtil
http://www.jcodecraeer.com/a/opensource/2016/0328/4095.html
http://jaeger.itscoder.com/android/2016/03/27/statusbar-util.html(中文版详细说明)
Demo下载地址:https://fir.im/5mnp


4.4之上才可以修改'状态栏颜色'
Android6.0以上系统或者小米的MIUI V6以上版本或者，魅族的Flyme4.0以上版本才可以设置'状态栏为黑字'。


使用
1.在 build.gradle 文件中添加依赖, StatusBarUtil 已经发布在 JCenter:
compile 'com.jaeger.statusbarutil:library:1.4.0'

2.在 setContentView() 之后调用你需要的方法，例如:
setContentView(R.layout.main_activity);
ButterKnife.bind(this);//绑定后调用, 否则view没有初始化
StatusBarUtil.setColor(MainActivity.this, mColor);
示例:StatusBarUtil.setColor(this, getResources().getColor(R.color.deep_green));

3.如果你在一个包含 DrawerLayout 的界面中使用,
你需要在布局文件中为 DrawerLayout 添加 android:fitsSystemWindows="true" 属性:



1.设置状态栏颜色
StatusBarUtil.setColor(Activity activity, int color);//Status颜色有个暗色
StatusBarUtil.setColor(this, getResources().getColor(R.color.deep_green),0);//Status就是设置的颜色,0:透明度

2.设置状态栏半透明
StatusBarUtil.setTranslucent(Activity activity, int statusBarAlpha);

3.设置状态栏全透明
StatusBarUtil.setTransparent(Activity activity);

4.为包含 DrawerLayout 的界面设置状态栏颜色（也可以设置半透明和全透明）
StatusBarUtil.setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color);

5.为使用 ImageView 作为头部的界面设置状态栏透明.注意:如果设置android:scaleType="centerCrop",否则可能显示效果不好
StatusBarUtil.setTranslucentForImageView(Activity activity, int statusBarAlpha, View needOffsetView);
示例:StatusBarUtil.setTranslucentForImageView(this, 30, null);//设置透明度

6.在 Fragment 中使用
在activity中重写setStatusBar方法(就像重写onCreate一样)
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(UseInFragmentActivity.this, null);
    }

7.为滑动返回界面设置状态栏颜色
推荐配合 bingoogolapple/BGASwipeBackLayout-Android: Android Activity 滑动返回 这个库一起使用。
这个库地址:https://github.com/bingoogolapple/BGASwipeBackLayout-Android
StatusBarUtil.setColorForSwipeBack(Activity activity, @ColorInt int color, int statusBarAlpha);

8.StatusBarUtil.setTranslucentForCoordinatorLayout(this,0);//当使用CollapsingToolbarLayout的时候,有bug!!!

9.StatusBarUtil.hideFakeStatusBarView(this);//隐藏伪状态栏 View

10.设置StatusBarUtil.setLightMode为深色字体(经测试无效, 好像6.0+才行)
含有CoordinatorLayout布局的时候，字体颜色好像无效？(在根布局ConstraintLayout外面再嵌套一层LinearLayout 或者RelativeLayout，应该就可以了)
StatusBarUtil.setLightMode(this);

11.通过传入 statusBarAlpha 参数，可以改变状态栏的透明度值，默认值是112，该值需要在 0 ~ 255 之间
