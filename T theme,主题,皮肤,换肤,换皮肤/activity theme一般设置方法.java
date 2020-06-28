https://blog.csdn.net/u010356768/article/details/83651127
1.Theme的分类:
android:Theme				API 1 开始
android:Theme.Holo			API 11（android3.0） 开始
android:Theme.DeviceDefault	API 14（android4.0） 开始
android:Theme.Material		API 21（android5.0） 开始
Theme.AppCompat				兼容包, AppCompat_v7中的主题

//===========================================================
API 1 开始:
android:Theme					根主题
android:Theme.Black 			背景黑色
android:Theme.Light 			背景白色
android:Theme.Wallpaper 		以桌面墙纸为背景
android:Theme.Translucent		透明背景
android:Theme.Panel 			平板风格
android:Theme.Dialog 			对话框风格
 
API 11（android3.0） 开始:
android:Theme.Holo				Holo根主题
android:Theme.Holo.Black		Holo黑主题
android:Theme.Holo.Light		Holo白主题
 
API 14（android4.0） 开始:
Theme.DeviceDefault				设备默认根主题
Theme.DeviceDefault.Black		设备默认黑主题
Theme.DeviceDefault.Light		设备默认白主题
 
API 21:
Theme.Material					Material根主题
Theme.Material.Light			Material白主题
 
兼容包v7中带的主题：
Theme.AppCompat					兼容主题的根主题
Theme.AppCompat.Black			兼容主题的黑色主题
Theme.AppCompat.Light			兼容主题的白色主题

//===========================================================
Black 黑色风格
Light 光明风格
Dark 黑暗风格
DayNight 白昼风格
Wallpaper 墙纸为背景
Translucent 透明背景
Panel 平板风格
Dialog 对话框风格
NoTitleBar 没有TitleBar
NoActionBar 没有ActionBar
Fullscreen 全屏风格
MinWidth 对话框或者ActionBar的宽度根据内容变化，而不是充满全屏
WhenLarge 对话框充满全屏
TranslucentDecor 半透明风格
NoDisplay 不显示，也就是隐藏了
WithActionBar 在旧版主题上显示ActionBar


//===========================================================
2.下方3种主题写法都指向的是同一个Theme
parent="@android:style/Theme"
parent="android:style/Theme"
parent="android:Theme"//默认这种写法

3.Theme说明//											StatusBar文字	TitleBar/ActionBar	windowBackground(窗口背景颜色)
API 1:
parent="android:Theme"//根主题								黑				有				黑~灰,上下渐变
parent="android:Theme.NoTitleBar"//							黑				无				黑~灰,上下渐变
parent="android:Theme.NoTitleBar.Fullscreen"//				白(全屏)		无				黑~灰,上下渐变
parent="android:Theme.Light"//光明风格						黑				有				白	ScanKing(OCR图片扫描器)
parent="android:Theme.Light.NoTitleBar"//					黑				无				白
parent="android:Theme.Light.NoTitleBar.Fullscreen"//		白(全屏)		无				白
parent="android:Theme.Black"//黑色风格						黑				有				黑
parent="android:Theme.Black.NoTitleBar"//					黑				无				黑	Fullter-go(alibaba)
parent="android:Theme.Black.NoTitleBar.Fullscreen"//		白(全屏)		无			  	黑
parent="android:Theme.Wallpaper"//系统桌面为背景			黑				有				系统桌面
parent="android:Theme.Wallpaper.NoTitleBar"//				黑				无				系统桌面
parent="android:Theme.Wallpaper.NoTitleBar.Fullscreen"//	白(全屏)		无				系统桌面
parent="android:Theme.Translucent"//半透明					黑				有				半透明
parent="android:Theme.Translucent.NoTitleBar"//				黑				无				半透明
parent="android:Theme.Translucent.NoTitleBar.Fullscreen"//	白(全屏)		无			  	半透明
parent="android:Theme.Panel"//平板风格						白				无				半透明
parent="android:Theme.Light.Panel"//						白				无				半透明
parent="android:Theme.Dialog"//★★★对话框风格★★★

API 11:
parent="android:Theme.Holo"//Holo根主题						黑			有(默认图标&文字)	黑~灰,上下渐变
parent="android:Theme.Holo.Light"//							黑			有(默认图标&文字)	白	ScanKing(OCR图片扫描器)
parent="android:Theme.Holo.Light.DarkActionBar"//			黑			有(默认图标&文字)	白	ScanKing(OCR图片扫描器)

API 14:
parent="android:Theme.DeviceDefault"//设备默认=Material.Light	白			有				灰
parent="android:Theme.DeviceDefault.Light"//				白				有				白
parent="android:Theme.DeviceDefault.Light.DarkActionBar"//	白				有				白

API 21:
parent="android:Theme.Material"//Material根主题				白			有					灰
parent="android:Theme.Material.Light"//						白			有					白
parent="android:Theme.Material.Light.DarkActionBar"//		白			有					白(状态栏&标题栏背景黑色)
parent="android:Theme.Material.Light.NoActionBar"//			白			无					白
parent="android:Theme.Material.Light.NoActionBar.Fullscreen"//白(全屏)	无					白

//AppCompat
parent="Theme.AppCompat"//兼容主题的根主题					白			无				灰
parent="Theme.AppCompat.Light"//							白			无				白
parent="Theme.AppCompat.Light.NoActionBar"//				白			无				白
parent="Theme.AppCompat.Light.DarkActionBar"//				白			无				白


2.设置windowBackground(窗口背景颜色)
<style name="AppTheme" parent="android:Theme.Light.NoTitleBar">
    <!--Theme.AppCompat.Light.DarkActionBar 默认#EEEEEE-->
    <item name="android:windowBackground">#EEEEEE</item>
</style>


3.主题应用Application范围
在AndroidManifest.xml中的application节点中设置theme属性，主题theme应用到整个应用程序中。
<application
    Android:icon=”@drawable/icon”
    Android:label=”@string/app_name”
    Android:theme=”@style/AppTheme”>

3.1.Activity范围
使用Java代码或者在AndroidManifest.xml中对活动Activity的主题进行设置，主题仅应用到当前活动中。
在AndroidMainifest.xml设置方法：
<activity
android:name=“.MainActivity”
android:label=“@string/app_name”
android:theme=“@style/FullScreen” >


//===========================================================
4.在activity创建前动态设置主题

比如我现在有activity A1，A2,B，A1可以进入B，A2也可以进入B，A1进入B，此时B想用红色主题，A2进入B，此时B想用绿色主题。how to？

用 setTheme，必须写在 super.onCreate(savedInstanceState);之前
protected void onCreate(Bundle savedInstanceState) {
        Random random = new Random();
        boolean b = random.nextBoolean();
        setTheme(b ? R.style.AppThemeRed : R.style.AppThemeGreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



4.1.在activity创建后动态设置主题
其实设置主题必须在任何view创建之前，所以我们不可能在activity的onCreate之后来更改主题，
如果一定要做，就只能调用setTheme()，然后调用recreate()，重新创建一个activity，并且销毁上一个activity,代码如下:

FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRandomTheme();
                recreate();
            }
        });
