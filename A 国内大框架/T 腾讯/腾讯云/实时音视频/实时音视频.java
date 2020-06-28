文档中心
https://cloud.tencent.com/document/product/647

Demo下载体验
https://cloud.tencent.com/document/product/647/17021

客户端 API  错误码:
https://cloud.tencent.com/document/product/647/32257

SDK 下载
https://cloud.tencent.com/document/product/647/32689
	GitHub:https://github.com/tencentyun/TRTCSDK/tree/master/Android
	SDK集成指引(一分钟集成SDK ):https://cloud.tencent.com/document/product/647/32175
		defaultConfig {
			ndk {
			  abiFilters "armeabi", "armeabi-v7a"
			}
		}
		//最新版本号:https://github.com/tencentyun/TRTCSDK/tree/master/Android/SDK
		compile 'com.tencent.liteav:LiteAVSDK_TRTC:latest.release'
		
		<!--腾讯实时音视频,TRTC SDK 需要以下权限-->
		<uses-permission android:name="android.permission.INTERNET" />
		<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
		<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
		<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
		<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
		<uses-permission android:name="android.permission.RECORD_AUDIO" />
		<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
		<uses-permission android:name="android.permission.BLUETOOTH" />
		<uses-permission android:name="android.permission.CAMERA" />
		<uses-permission android:name="android.permission.READ_PHONE_STATE" />
		<uses-feature android:name="android.hardware.Camera"/>
		<uses-feature android:name="android.hardware.camera.autofocus" />

		##-----End: proguard configuration for 腾讯实时音视频,TRTC SDK-----
		-keep class com.tencent.** { *; }
		##-----End: proguard configuration for 腾讯实时音视频,TRTC SDK-----


Global中:
public static final int    RTC_SDK_APPID = 1400198917;//实时音视频的appid
//一个与直播服务相关的数字，您可以在腾讯云实时音视频 控制台 右上角的【账号信息】- 直播信息页面看到 appid 和 bizid
public static final int    bizid         = 45643;//旁路推流时会用到



