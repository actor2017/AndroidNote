# Google于2018年3月8日悄无声息的推送了Android P

## 1.室内WIFI定位
- 增加了对RTT Wi-Fi协议的支持，以此作为室内定位的基础。
- 在支持硬件支持的Android P设备上，开启定位并且打开WIFI扫描后就可以使用该功能进行定位。应用可以测量与附近支持RTT的Wi-Fi接入点（AP）的距离。设备必须启用位置并启用Wi-Fi扫描（在设置>位置下）。使用这个功能不会连接到WIFI，而且为了保持隐私，只有手机能确定AP到设备的距离，反之则不能。 
如果设备测量到3个或更多AP的距离，则可以使用多点定位算法来估算最适合这些测量值的设备位置。其结果通常可以精确到1至2米范围。
	<pre>
	//注：该处在2018.3.8的版本中还有bug，WIFI_RTT_RANGING_SERVICE没有添加到@ServiceName标记中
	//android.net.wifi.rtt
	WifiRttManager wifiRttManager = (WifiRttManager) getSystemService(Context.WIFI_RTT_RANGING_SERVICE);
	RangingRequest.Builder builder = new RangingRequest.Builder();
	builder.addAccessPoint();
	builder.addWifiAwarePeer();
	wifiRttManager.startRanging(builder.build(), () -> {...}, new RangingResultCallback{...});
	</pre>

## 2.“刘海”屏幕支持
- Android P 支持了手机屏幕是不规则形状时的获取（主要是应对刘海屏吧）。可以使用类似windowInsets.getDisplayCutout()来获取一些你想要的信息。
	<pre>
	//您可以在自己的View中获取到不应该绘制的部分屏幕
	getRootWindowInsets().getDisplayCutout().getBounds();
	getRootWindowInsets().getDisplayCutout().getSafeInsetBottom();
	getRootWindowInsets().getDisplayCutout().getSafeInsetLeft();
	getRootWindowInsets().getDisplayCutout().getSafeInsetRight();
	getRootWindowInsets().getDisplayCutout().getSafeInsetTop();
	//也可以设置Window的属性
	WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
	WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
	layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
	layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
	layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
	</pre>

## 3.通知(又改通知...),详见[链接](https://blog.csdn.net/GenlanFeng/article/details/79496359#%E9%80%9A%E7%9F%A5)
- Android P还增加了许多对通知的支持。
- **增强体验**
  1. 从Android 7.0开始，就优化了Android通知栏的体验。 
  2. 在P当中，又新增了下述功能：
    <ul>
     <li><b>支持图像</b>：Android P现在在手机上的消息通知中显示图像。您可以在消息上使用setData（）来显示图像。</li>
     <li><b>会话参与者的简化支持</b>：新的Notification.Person类用于标记参与聊天的人，包括他们的头像和URI。还有其他的一些API，现在都用Person类作为标志参数而不是CharSequence。</li>
    </ul>
    <pre>
	Notification.Builder builder = new Notification.Builder(this, "a");
	//新的聊天对象
	Notification.Person p = new Notification.Person();
	//在MessagingStyle中用Person代替了以往的CharSequence
	Notification.MessagingStyle messageStyle = new Notification.MessagingStyle(p);
	Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message("aaa", 100, p);
	//可以显示图像了
	message.setData();
	messageStyle.addMessage(message);
	builder.setStyle(messageStyle);
	Notification notification = builder.build();
    </pre>

## 4.多相机支持和相机更新
- 现在，可以同时从两个或更多的物理摄像头同时获得数据流。在具有双前置或双后置摄像头的设备上，可以实现无法使用单个摄像头实现的功能，例如无缝缩放，散景 ，和立体视觉。 该API还允许您调用合理的或者融合的相机流，以便在两台或更多台相机之间自动切换。 
- 相机的其他改进包括新的android.hardware.camera2.params.SessionConfiguration，有助于减少初始捕捉期间的延迟。而Surface共享可让相机客户端处理各种使用情况，而无需停止和启动相机流式传输。 此外还添加了基于显示的闪光灯支持的[API](https://developer.android.com/reference/android/hardware/camera2/CameraMetadata.html#CONTROL_AE_MODE_ON_EXTERNAL_FLASH)。 
- Android P还支持支持deveices上的[外部USB / UVC相机](https://blog.csdn.net/GenlanFeng/article/details/android.hardware.camera2.CameraCharacteristics)。

## 5.新的图片解码
- Android P新增了[ImageDecoder](https://developer.android.com/reference/android/graphics/ImageDecoder.html)类，为解码图像提供了一种更优的方法。由此可以用ImageDecoder来替换BitmapFactory和BitmapFactory.Options。更多使用方法请参见官方API。
	<pre>
	String filePath = "test";
	File file = new File(filePath);
	ImageDecoder.Source source = ImageDecoder.createSource(file);
	ImageDecoder.decodeBitmap(source);
	ImageDecoder.decodeDrawable(source, (imageDecoder, imageInfo, source1) -> {
	    //裁剪图像
	    imageDecoder.setCrop();
	    //调整大小
	    imageDecoder.setResize();
	});
	BitmapFactory.decodeFile(filePath);
	</pre>

## 6.动画
- Android P引入了一个新的`AnimatedImageDrawable`类来绘制和显示GIF和WebP动画图像。 AnimatedImageDrawable与AnimatedVectorDrawable类似，因为AnimatedImageDrawable动画也是基于RenderThread工作的。 RenderThread本身在内部使用工作线程进行解码，因此解码不会干扰RenderThread。 这种实现允许您的应用拥有动画图像，而无需管理其更新或干扰应用的UI线程。
	<pre>
	Drawable d = ImageDecoder.decodeDrawable(...);
	if (d instanceof AnimatedImageDrawable) {
	    // Prior to start(), the first frame is displayed
	    ((AnimatedImageDrawable) d).start();  
	}
	</pre>

## 7.HDR VP9视频，HEIF图像压缩和媒体API [链接](https://developer.android.com/sdk/api_diff/p-dp1/changes.html)
- Android P增加了对HDR VP9 Profile 2的内置支持。
- Android P支持HEIF图像（隔壁IOS在2017年10月推的新的图片编码）编码。 
- Android P还引入了MediaPlayer2。该播放器支持使用DataSourceDesc构建的播放列表。
    <pre>MediaPlayer2.create();</pre>

## 8.神经网络API 1.1
- 对神经网络API新增了9个功能：Pad, BatchToSpaceND, SpaceToBatchND, Transpose, Strided Slice, Mean, Div, Sub, and Squeeze。

## 9.安全增强
Android P引入了许多新的安全功能，包括统一的**指纹验证对话框**和**敏感交易的高确信度**的用户确认。 有关更多详细信息，请参阅[安全更新](https://developer.android.com/preview/features/security.html)页面。

