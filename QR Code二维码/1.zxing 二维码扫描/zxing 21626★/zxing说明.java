https://github.com/zxing/zxing
https://zxing.org/w/decode.jspx			//官方 Online Decoder
https://zxing.appspot.com/generator		//官方 QR Code Generator
https://zxing.github.io/zxing/apidocs/	//官方 Javadoc
https://zxing.github.io/zxing/			//官方 Documentation Site
https://github.com/zxing/zxing/releases/download/BS-4.7.8/BarcodeScanner-4.7.8.apk	//Barcode Scanner 4.7.8(条码扫描器)
https://github.com/zxing/zxing/wiki/Getting-Started-Developing	//帮助文档

支持各种条形码，二维码扫描，由多个模块组成， 而且支持PC端，移动端
core				The core image decoding library, and test code//图像解码库和测试代码
docs															//介绍ZXing项目的文档
javase				JavaSE-specific client code					//JavaSE的客户端代码
android				Android client Barcode Scanner	//以导入Eclipse项目方式编译apk,目录: "F:\Android\zxing\androidFromEclipse", 需要Google Play, Google也商店下载
android-integration	Supports integration with Barcode Scanner via Intent//通过Intent支持与"条形码扫描器app"的集成
android-core		Android-related code shared among android, other Android apps//只有一个类, 配置Android摄像头
zxingorg			The source behind zxing.org	//在线解析二维码
zxing.appspot.com	The source behind web-based barcode generator at zxing.appspot.com//基于web的条形码生成器的源代码，请访问zxing.appspot.com

1.导入到AndroidStudio
  1.1.android 模块, 以导入Eclipse项目方式导入
  1.2.File–>New–>NewModule -> Java Library -> 修改名称 -> Finish
      然后将生成的Library中Java目录下的文件删除，将Zxing中的core文件夹下的Java目录下的文件拷贝过来，最后，将此模块设为android模块的依赖
  1.3.同理导入 Android Library 方式导入 android-core

1.Maven仓库
https://repo1.maven.org/maven2/com/google/zxing/
https://mvnrepository.com/artifact/com.google.zxing/core

2.release版本: https://github.com/zxing/zxing/releases
//API 24（Android 7.0）, 也就是说 minSdkVersion 24: https://github.com/zxing/zxing/issues/1170
低版本手机(android 5.x)解码时会崩溃, 因为调用了List.sort()方法, 但是低版本没有这个方法
implementation 'com.google.zxing:core:3.4.0'//Requires Java 8+. Note: Android apps using this version must target API 24 or higher.
android {
	compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

3.修改源代码, 解决崩溃问题, 在 FinderPatternFinder 615行:
      //edited: https://github.com/zxing/zxing/issues/1170
    //    possibleCenters.sort(moduleComparator);
    Collections.sort(possibleCenters, moduleComparator);


//权限
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />


可打开默认二维码扫描页面
支持对图片Bitmap的扫描功能
支持对UI的定制化操作
支持对条形码的扫描功能
支持生成二维码操作
支持控制闪光灯开关

•CaptureActivity。这个是启动Activity 也就是扫描器。
  <ViewfinderView 自定义的View，就是我们看见的拍摄时中间的框框了
  <SurfaceView	 原生控件,摄像时填满屏幕的布局(效率高,一般扫描,游戏等用)
•CaptureActivityHandler 解码处理类，负责调用另外的线程进行解码。
•DecodeThread 解码的线程。
•com.google.zxing.client.android.camera 包，摄像头控制包。


2.将几个核心的类包，拷贝到你的项目中，然后添加zxing.jar即可。
  没有zxing.jar的童靴自己下载,记得右键AddAsLibrary
//-----------------------------------------------注意:本例的扫描本地二维码有bug,会崩,还未修复-----


7.把"我用的这个网页资料"里的文件复制到我的工程(注意layout中改包名,否则报错)

8.启动扫描二维码的页面:
Intent intent = new Intent(MainActivity.this,CaptureActivity.class);
startActivityForResult(intent,26);	//26:随便写的,防止撞车

//获取扫描结果
@Override
protected void onActivityResult(int requestCode,int resultCode,Intent data){
    if(resultCode == RESULT_OK && requestCode == 26){
        Bundle bundle = data.getExtras();
        String scanResult = bundle.getString("result");//扫描结果
        Toast.makeText(this,"扫描结果:"+scanResult,Toast.LENGTH_SHORT).show();
    }
}

9.自定义取景框（扫描框）(中间的框框)
ViewfinderView的onDraw方法中定制（比如：四个角，扫描线，等等。。）

10.问题:
（1）你是否遇到了，取景框太小，以至于离二维码很远才可以扫到？(作者菌已改)
    在camera包下的CameraManager类中，修改代码
//        int width = screenResolution.x * 3 / 4;
//       if (width < MIN_FRAME_WIDTH) {
//          width = MIN_FRAME_WIDTH;
//       } else if (width > MAX_FRAME_WIDTH) {
//          width = MAX_FRAME_WIDTH;
//       }
//       int height = screenResolution.y * 3 / 4;
//       if (height < MIN_FRAME_HEIGHT) {
//          height = MIN_FRAME_HEIGHT;
//       } else if (height > MAX_FRAME_HEIGHT) {
//          height = MAX_FRAME_HEIGHT;
//       }

         DisplayMetrics dm = context.getResources().getDisplayMetrics();
         int width = (int)(dm.widthPixels * 0.6);
         int height = (int)(width * 0.9);

         int leftOffset = (screenResolution.x - width) / 2;
         int topOffset = (screenResolution.y - height) / 2;

（2）取景框对准二维码，二维码拉伸变形了，根本扫不出结果来！(作者菌已改)
    找到findBestPreviewSizeValue方法,将newDiff的变量计算代码改成如下:
    int newDiff=Math.abs(newY - screenResolution.x) + Math.abs(newX - screenResolution.y);

（3）距离扫描二维码扫描不出，需要远一点才可以。那么解决这个问题的方案如下：(作者菌已改)
    找到CameraConfigurationManager类的setDesiredCameraParameters（Camera camera）方法，
    将其中的代码注释，然后添加如下代码：
    Camera.Parameters parameters = camera.getParameters();
    List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
    int position =0;
    if(supportedPreviewSizes.size()>2){
      position=supportedPreviewSizes.size()/2+1;//supportedPreviewSizes.get();
    }else {
      position=supportedPreviewSizes.size()/2;
    }

    int width = supportedPreviewSizes.get(position).width;
    int height = supportedPreviewSizes.get(position).height;
    Log.d(TAG, "Setting preview size: " + cameraResolution);
    camera.setDisplayOrientation(90);
    cameraResolution.x=width;
    cameraResolution.y=height;
    parameters.setPreviewSize(width,height);
    setFlash(parameters);
    setZoom(parameters);
    camera.setParameters(parameters);


-keep class com.google.zxing.** {*;}  
-dontwarn com.google.zxing.**  
