https://github.com/GcsSloop/AndroidNote/issues/7

[转]Android如何关闭硬件加速 #7
原文地址：http://developer.android.com/guide/topics/graphics/hardware-accel.html
译文地址：http://blog.chenming.info/blog/2012/09/18/android-hardware-accel/

硬件加速
Android 3.0 (API level 11), 开始支持
所有的View 的canvas都会使用GPU，但是硬件的加速会占有一定的RAM。
在API >= 14上，默认是开启的，如果你的应用只是标准的View和Drawable，全局都打开硬件加速，是不会有任何问题的。
然而，硬件加速并不支持所有的2D画图的操作，这时开着它，可能会影响到你的自定义控件或者绘画，出现异常等行为，
所以android对于硬件加速提供了可选性

如果你的应用执行了自定义的绘画，可以通过在真机上测试开启硬件加速查找问题

硬件加速的级别
<application 
    android:hardwareAccelerated="true">

    <activity ... />
    <activity android:hardwareAccelerated="false" />
</application>


Window
getWindow().setFlags(
   WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
   WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
   
   
View
myView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


Note: 你可以关闭View级别的硬件加速，但是你不能在View级别开启硬件加速，因为它还依赖其他的设置

两种获取是否支持硬件加速的方式
View.isHardwareAccelerated()   //returns true if the View is attached to a hardware accelerated window.
Canvas.isHardwareAccelerated() //returns true if the Canvas is hardware accelerated

如果必须进行这样的验证，建议你在draw的代码块中使用：Canvas.isHardwareAccelerated()，因为如果一个View被attach到一个硬件加速的Window上，
即使没有硬件加速的Canvas，它也是可以被绘制的。比如：将一个View以bitmap的形式进行缓存


//---------------------------------------------------------------
zidanpiaoguo commented on 5 Mar
我感觉还是最好不要关闭硬件加速器，关闭之后，toolbar不显示阴影,cardview不显示圆角边框,circleimageview 不显示边框阴影。有很多后遗症。

 @GcsSloop GcsSloop referenced this issue on 30 May
 Closed
大佬，华为8.0的图片不显示圆角。。。RCImageView #22
