Android定位SDK
1.申请密钥
http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/key

2.配置环境
①.在相关下载里下载最新的库文件,SDK,示例代码,类参考:
http://lbsyun.baidu.com/index.php?title=android-locsdk/geosdk-android-download

②.使用Eclipse开发的开发者，将SO文件的压缩文件解压出来，把对应架构下的SO文件放入开发者自己APP的对应架构下的文件夹中（建议全部放入以提高程序兼容性）；将JAR文件拷贝到工程的libs目录下，这样即可在程序中使用Android定位SDK。
使用AndroidStutio的开发者除了上述操作外，还需要在Module的build.gradle中配置SO文件的使用，如下所示：

    defaultConfig {
        applicationId "com.kuchuanyun.wisdomcitymanagement"//包名要配置
    }
    //添加so库,与buildTypes { 同级
    sourceSets{
        main{
            jniLibs.srcDirs = ['libs']
        }
    }

清单文件
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.kuchuanyun.wisdomcitymanagement">//配置包名,确保和上面包名一致


③.如果开发的是系统应用，除了需要在工程中配置SO文件，还需要手动把对应架构的SO文件拷贝到/system/lib下，如果是64位系统,则需要将64位的SO文件拷贝到/sytem/lib64下。
注意：新版本的定位SDK，开发者除了要更新JAR包之外，同时需要关注SO文件是否有更新。如果SO文件名称改变，即SO文件有更新，开发者要及时替换掉老版本，否则会导致定位失败。


④.设置AcessKey
设置AK，在Application标签中加入
        <!--百度定位设置AK，在Application标签中加入-->
        <meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:value="AK"/><!--value:开发者申请的AK-->


⑤.设置AndroidManifest.xml
        <!--百度定位服务-->
        <service
            android:name="com.baidu.location.f"
            android:enabled="true"
            android:process=":remote">
        </service>

	<!--百度定位-->
    <!-- 这个权限用于进行网络定位-->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <!-- 这个权限用于访问GPS定位-->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <!-- 用于访问wifi网络信息，wifi信息会用于进行网络定位-->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <!-- 获取运营商信息，用于支持提供运营商信息相关的接口-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <!-- 这个权限用于获取wifi的获取权限，wifi信息会用来进行网络定位-->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <!-- 用于读取手机当前的状态-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <!-- 写入扩展存储，向扩展卡写入数据，用于写入离线定位数据-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <!-- 访问网络，网络定位需要上网-->
    <uses-permission android:name="android.permission.INTERNET"/>
    <!-- SD卡读取权限，用户写入离线定位数据-->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <uses-permission android:name="android.permission.READ_LOGS"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>


⑥.import相关类
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.Poi;


3.坐标系说明
目前国内主要有以下三种坐标系：
1. WGS84：为一种大地坐标系，也是目前广泛使用的GPS全球卫星定位系统使用的坐标系；
2. GCJ02：表示经过国测局加密的坐标；
3. BD09：为百度坐标系，其中bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托米制坐标；
Android定位SDK产品，支持全球定位，能够精准的获取经纬度信息。根据开发者的设置，在国内获得的坐标系类型可以是：国测局坐标、百度墨卡托坐标 和 百度经纬度坐标。在海外地区，只能获得WGS84坐标。请开发者在使用过程中注意坐标选择。


4.获取位置信息
综合定位功能指的是根据用户实际需求，返回用户当前位置的基础定位服务，包含GPS和网络定位（WiFi定位和基站定位）功能。基本定位功能同时还支持位置描述信息功能，离线定位功能，位置提醒功能和位置语义化功能。
①.在application中初始化:
  public LocationClient mLocationClient = null;
 
  public BDAbstractLocationListener myListener = new MyLocationListener();
  //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口，原有BDLocationListener接口暂时同步保留。
  //具体介绍请参考后文中的说明
 
  public void onCreate() {
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener);//注册监听函数,如果不是定时发送位置,这儿不用注册.用的时候才注册.
  }
LocationClient类是定位SDK的核心类，具体方法请参见类参考部分的介绍。

②.第二步，配置定位SDK参数
设置定位参数包括：定位模式（高精度定位模式、低功耗定位模式和仅用设备定位模式），返回坐标类型，是否打开GPS，是否返回地址信息、位置语义化信息、POI信息等等。
返回坐标类型包括：
1. gcj02：国测局坐标；
2. bd09：百度墨卡托坐标；
3. bd09ll：百度经纬度坐标；
注意：海外地区定位结果默认、且只能是WGS84类型坐标。
LocationClientOption类，该类用来设置定位SDK的定位方式，例如：
private void initLocation(){
 
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(LocationMode.Hight_Accuracy);
    //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
 
    option.setCoorType("bd09ll");
    //可选，默认gcj02，设置返回的定位结果坐标系
 
    int span=1000;
    option.setScanSpan(span);
    //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
 
    option.setIsNeedAddress(true);
    //可选，设置是否需要地址信息，默认不需要
 
    option.setOpenGps(true);
    //可选，默认false,设置是否使用gps
 
    option.setLocationNotify(true);
    //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
 
    option.setIsNeedLocationDescribe(true);
    //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
 
    option.setIsNeedLocationPoiList(true);
    //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
 
    option.setIgnoreKillProcess(false);
    //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
 
    option.setIgnoreCacheException(false);
    //可选，默认false，设置是否收集CRASH信息，默认收集
 
    option.setEnableSimulateGps(false);
    //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
 
    option.setWifiValidTime(5*60*1000);
    //可选，7.2版本新增能力，如果您设置了这个接口，首次启动定位时，会先判断当前WiFi是否超出有效期，超出有效期的话，会先重新扫描WiFi，然后再定位
 
    mLocationClient.setLocOption(option);
}
高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
低功耗定位模式：这种定位模式下，不会使用GPS进行定位，只会使用网络定位（WiFi定位和基站定位）；
仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。



第四步，开始定位
启动定位，代码如下：
mLocationClient.start();
 stop：关闭定位SDK。
自v7.2版本起，新增LocationClient.reStart()方法，用于在某些特定的异常环境下重启定位，具体异常环境请参见《开发指南 – 错误码》部分的介绍。
开发者定位场景如果是单次定位的场景，在收到定位结果之后直接调用stop函数即可。
如果stop之后仍然想进行定位，可以再次start等待定位结果回调即可。
如果开发者想按照自己逻辑请求定位，可以在start之后按照自己的逻辑请求LocationClient.requestLocation()函数，会主动触发定位SDK内部定位逻辑，等待定位回调即可。



位置提醒使用
位置提醒最多提醒3次，3次过后将不再提醒。 假如需要再次提醒，或者要修改提醒点坐标，都可通过函数SetNotifyLocation()来实现。

//位置提醒相关代码
mNotifyer = new NotifyLister();
 
mNotifyer.SetNotifyLocation(42.03249652949337,113.3129895882556,3000,"gps");
//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
 
mLocationClient.registerNotify(mNotifyer);
//注册位置提醒监听事件后，可以通过SetNotifyLocation 来修改位置提醒设置，修改后立刻生效。
 
//BDNotifyListner实现
public class NotifyLister extends BDNotifyListener{
 
    public void onNotify(BDLocation mlocation, float distance){
 
        mVibrator01.vibrate(1000);
        //振动提醒已到设定位置附近
    }
}
 
//取消位置提醒
mLocationClient.removeNotifyEvent(mNotifyer);



6.注意事项http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/notice
强烈建议开发者设置并保管好自己的prodName，这样方便我们提供更好的定位保障服务。

定位SDK必须注册GPS和网络的使用权限。

使用定位SDK请尽量保证网络连接通畅（GPS定位方式不需要连网，但如果需要地址信息、位置语义化、POI等信息都需要联网的）。目前离线功能已经支持获取上述信息，但离线定位不是百分百都能定位成功的，增加我们如何保证在线定位成功的措施。

定位SDK可以返回国测局坐标、百度经纬度坐标 和 百度墨卡托坐标，三种类型坐标（海外地区只能返回WGS84类型的坐标） ，若需要将定位点的位置通过百度Android地图 SDK进行地图展示，请直接返回百度经纬度坐标的定位，将无偏差的叠加在百度地图上。

有的移动设备锁屏后为了省电会自动关闭网络连接，此时网络定位模式的定位失效。此外，锁屏后移动设备若进入CPU休眠，定时定位功能也失效。若您需要实现在CPU休眠状态仍需定时定位，可以用AlarmManager 实现1个CPU可叫醒的Timer，定时请求定位。



7.Android 6.0系统开发须知http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/androidmnotice
Google在Android 6.0中引入了动态权限获取机制（Runtime Permission），使得Android的权限管理更加严格完善。
动态权限获取要求开发者在调用涉及相关权限的代码时，使用系统接口来动态得申请相应权限。定位SDK涉及权限即在此范畴中。
在未获取到定位权限情况下，定位SDK获取到的定位依据（基站、WiFi）均为空值，因此无法有效定位，定位服务会返回错误码167。

动态权限机制的开启
Android 6.0对于动态权限机制的开启主要根据应用设定的targetSdkVersion，具体来讲：
targetSdkVersion	是否默认禁用敏感权限	是否开启动态权限
	<23			否			否
	>=23			是			是
因此需要在项目配置文件中修改您的targetSdkVersion配置。
对于Eclipse环境，请更改AndroidManifest.xml中代码；AndroidStudio环境，请更改build.gradle中的代码。

动态权限代码样例
在Android 6.0系统中，需要动态获取的权限涉及到：
1、获取手机状态：
Manifest.permission.READ_PHONE_STATE

2、获取位置信息：
Manifest.permission.ACCESS_COARSE_LOCATION
Manifest.permission.ACCESS_FINE_LOCATION

3、读写SD卡：
Manifest.permission.READ_EXTERNAL_STORAGE
Manifest.permission.WRITE_EXTERNAL_STORAGE

获取权限使用API
1、int android.content.Context.checkSelfPermission(String permission)，此接口会触发系统弹窗，用户选择后触发Activity中的回调函数：
void YourActivity.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)

以READ_PHONE_STATE为例：
private static final int BAIDU_READ_PHONE_STATE =100;
、自定义一个权限获取码，用于回调函数中做对应处理 调用checkSelfPermission接口申请权限:
if(mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) !=PackageManager.PERMISSION_GRANTED) {
 
    // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
    requestPermissions( new String[]{ 
 
        Manifest.permission.READ_PHONE_STATE },BAIDU_READ_PHONE_STATE);
 
}

3、在Activity的onRequestPermissionResult回调函数中做处理：
@Override public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
 
    super.onRequestPermissionsResult(requestCode, permissions,grantResults);
 
    switch(requestCode) {
 
        // requestCode即所声明的权限获取码，在checkSelfPermission时传入
        case 1:
            BAIDU_READ_PHONE_STATE:
 
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
 
            } else{
 
                // 没有获取到权限，做特殊处理
            }
            break;
 
        default:
            break;
 
     }
}

Android 6.0系统的位置开关

Android 6.0原生系统与部分厂商定制的6.0系统在系统定位开关表现上稍有差异。具体来讲：
系统					开关名称			对定位的影响
							原生系统下，位置开关控制影响到系统级的GPS及网络定位：
							在关闭位置开关情况下，应用无法获取到Wifi信息，
原生系统（也包括未进行定制的系统）	位置信息	也无法使用GPS，仅可使用基站定位（cl类型）*，
							造成定位误差变大；

							打开位置开关后，应用可以使用Wifi信息或GPS进行定位，
							会大幅提升定位精度
//-------------------------------------------------------------------------------------------------------
							厂商定制系统（部分）开关仅影响GPS使用：
							关闭GPS开关情况下，应用仍可访问到Wifi信息，
第三方、厂商定制系统	GPS				可以使用wifi定位

							打开GPS开关情况下，应用才可以使用GPS定位

注：
1.关于原生系统定位开关会影响Wifi获取的问题，可参照Google Android Bug反馈论坛：
https://code.google.com/p/android/issues/detail?id=185370
这是Android6.0原生系统已知问题，需要Google修复；
厂商定制ROM表现会有所差异。也可参照国内一些开发者文章描述：
http://www.jianshu.com/p/3400ca0deeee.

2.部分早期Android6.0版存在位置开关关闭后无法定位的问题，目前测试最新的Android6.0版本原生系统，已不存在此问题。
针对6.0系统的位置开关问题。由于GPS设置的行为在各版本中一致，因此可以简单的使用系统API来判断用户是否打开了位置按钮，让检测到系统版本为6.0，并且用户未打开按钮时，进行提示。
LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 
if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
 
    // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
}

8.错误码http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/ermsg
61：GPS定位结果，GPS定位成功。
62：无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位。
63：网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
65：定位缓存的结果。
66：离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
67：离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
68：网络连接失败时，查找本地离线定位时对应的返回结果。
161：网络定位结果，网络定位成功。
162：请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件。
167：服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
502：AK参数错误，请按照说明文档重新申请AK。
505：AK不存在或者非法，请按照说明文档重新申请AK。
601：AK服务被开发者自己禁用，请按照说明文档重新申请AK。
602：key mcode不匹配，您的AK配置过程中安全码设置有问题，请确保：SHA1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请AK。
501～700：AK验证失败，请按照说明文档重新申请AK。

1. 如果不能定位，记录错误返回码，并到百度地图开放平台论坛Andriod定位SDK版块中进行交流。
网址：http://bbs.lbsyun.baidu.com/forum.php?mod=forumdisplay&fid=10 。
2. 若返回值是162~167，请将错误码、IMEI和定位时间反馈至邮箱loc-bugs@baidu.com，以便我们跟进追查问题。
