1.修改dialog位置详细说明
http://blog.csdn.net/fancylovejava/article/details/21617553

2.构造方法
public Dialog(@NonNull Context context, @StyleRes int themeResId);//R.style.dialog_notify

    <!--白色背景, 没有阴影-->
    <style name="dialog_notify" parent="@android:style/Theme.Dialog">
        <item name="android:windowFrame">@null</item><!--边框-->
        <item name="android:windowFullscreen">false</item>
        <item name="android:windowIsFloating">true</item><!--是否浮现在activity之上-->
        <item name="android:windowIsTranslucent">false</item><!--半透明-->
        <item name="android:windowNoTitle">true</item><!--无标题-->
        <item name="android:windowBackground">@color/transparent</item><!--背景透明-->
        <item name="android:backgroundDimEnabled">false</item><!--模糊-->
    </style>
