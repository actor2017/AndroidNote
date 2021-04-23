1.在安卓7(API25), 魅族8.1(API27) 等手机上, 像下方这样设置背景 selector 会崩溃:
<LinearLayout
    android:background="color/selector_white_green_ebfef8"/>

Caused by: android.view.InflateException: Binary XML file line #2: Error inflating class <unknown>
Caused by: java.lang.reflect.InvocationTargetException
...
Caused by: android.content.res.Resources$NotFoundException: Drawable com.cqzonjo.smartfactory:color/selector_color_gray666_white with resource ID #0x7f06015c
Caused by: android.content.res.Resources$NotFoundException: File res/color/selector_color_gray666_white.xml from drawable resource ID #0x7f06015c
...
Caused by: org.xmlpull.v1.XmlPullParserException: Binary XML file line #3: <item> tag requires a 'drawable' attribute or child tag defining a drawable
...

2.在安卓7(API25), 魅族8.1(API27) 等手机上, 像下方这样设置字体颜色, 不崩溃, 但是字体颜色会一直是未选择状态的颜色.
<TextView
    android:textColor="color/selector_white_green_ebfef8"/>


3.定义在 color/selector_white_green_ebfef8.xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- 在安卓7, 魅族8.1(API27) 等手机上 -->
    <item android:color="@color/green_ebfef8" android:state_selected="true" />
    <item android:color="@color/white" />
</selector>

4.所以, 要定义在 drawable/ 目录下:
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- 注意: 这儿是 android:drawable -->
    <item android:drawable="@color/green_ebfef8" android:state_selected="true" />
    <item android:drawable="@color/white" />
</selector>
