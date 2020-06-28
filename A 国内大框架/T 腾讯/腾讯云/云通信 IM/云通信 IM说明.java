https://cloud.tencent.com/document/product/269/3794
Demo 体验
在 Demo 中，可以体验单聊、群聊、聊天室、用户资料、在线状态等功能，包含 iOS、Android、Web 三个平台 Demo，同时为了方便您可以快速集成 Demo，我们为您提供了开源组件 TUI Kit( https://cloud.tencent.com/document/product/269/30375 )。

Github: https://github.com/tencentyun/TIMSDK

1.Demo下载
https://github.com/tencentyun/TIMSDK/tree/master/Android
apk下载: https://cloud.tencent.com/document/product/269/35527 (扫底部二维码)
注册的账号和密码都是: actor

2.集成SDK
https://cloud.tencent.com/document/product/269/32679

api 'com.tencent.imsdk:imsdk:版本号'//腾讯im,收发消息
最新版:https://github.com/tencentyun/TIMSDK/tree/master/Android/tuikit/libs

价格:
https://cloud.tencent.com/document/product/269/11673


3.权限
<!--腾讯云通信IM-->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

4.依赖
implementation 'com.tencent.imsdk:imsdk:4.3.81'//腾讯im,收发消息

5.添加混淆
##--------Begin: proguard configuration for Tencent imsdk--------
-keep class com.tencent.** { *; }
##--------End: proguard configuration for Tencent imsdk----------

5.ImSDK 对象简介 https://cloud.tencent.com/document/product/269/9227
ImSDK 对象主要分为通讯管理器，会话、消息，群管理，具体的含义参见下表：
对象				介绍					功能
TIMManager		管理器类,负责SDK基本操作	初始化、登录、注销、创建会话等，可以通过扩展类 TIMManagerExt 使用更多管理器相关高级功能
TIMConversation	会话,负责会话相关操作	如发送消息,获取会话消息缓存,获取未读计数等,可以通过扩展类 TIMConversationExt 使用更多会话相关高级功能
TIMMessage		消息					包括文本、图片等不同类型消息。可以通过扩展类 TIMMessageExt 使用更多消息相关高级功能
TIMGroupManager	群组管理器				负责创建群组、加群、退群等，可以通过扩展类 TIMGroupManagerExt 使用更多群组相关高级功能

6.调用顺序介绍
ImSDK 调用 API 需要遵循以下顺序，其余辅助方法需要在登录成功后调用。
步骤			对应函数					说明
初始化		TIMSdkConfig					设置 SDK 基本配置，比如 sdkappid、日志等级等
			TIMManager : init				初始化 SDK
			TIMManager : setUserConfig		设置用户基本配置
			TIMManager : addMessageListener	设置消息监听
登录		TIMManager : login				登录
消息收发	TIMManager : getConversation	获取会话
			TIMConversation : sendMessage	发送消息
群组管理	TIMGroupManager					群组管理
注销		TIMManager : logout				注销

7.初始化(Android) https://cloud.tencent.com/document/product/269/9229
在application中:
initIMSdk();//初始化腾讯IMSdk

//初始化腾讯IMSdk
private void initIMSdk() {
	//在存在多进程的情况下，请只在一个进程进行 SDK 初始化
	if (SessionWrapper.isMainProcess(getApplicationContext())) {
		TencentImUtils.init(this, Global.IM_APPID);
//            TUIKit.init(this, Global.IM_APPID, TencentIMConfigs.getDefaultConfigs());
		//注册IM事件回调，这里示例为用户被踢的回调，更多事件注册参考文档
//            TUIKit.getBaseConfigs().setIMEventListener(new IMEventListener());
		//添加自定义表情(已经抽取到ChatLayout框架中)
		//TUIKit.getBaseConfigs().setFaceConfigs(initCustomConfig());
	}
}
private ArrayList<CustomFaceGroupConfigs> initCustomConfig() {
	ArrayList<CustomFaceGroupConfigs> groupFaces = new ArrayList<>();
	//创建一个表情组对象
	CustomFaceGroupConfigs faceConfigs = new CustomFaceGroupConfigs();
	//设置表情组每页可显示的表情列数
	faceConfigs.pageColumnCount = 5;
	//设置表情组每页可显示的表情行数
	faceConfigs.pageRowCount = 2;
	//设置表情组号
	faceConfigs.faceGroupId = 1;
	//设置表情组的主ICON
	faceConfigs.faceIconPath = "4349/xx07@2x.png";
	//设置表情组的名称
	faceConfigs.faceIconName = "4350";
	for (int i = 1; i <= 15; i++) {
		//创建一个表情对象
		FaceConfig faceConfig = new FaceConfig();
		String index = "" + i;
		if (i < 10)
			index = "0" + i;
		//设置表情所在Asset目录下的路径
		faceConfig.assetPath = "4349/xx" + index + "@2x.png";
		//设置表情所名称
		faceConfig.faceName = "xx" + index + "@2x";
		//设置表情宽度
		faceConfig.faceWidth = 240;
		//设置表情高度
		faceConfig.faceHeight = 240;
		faceConfigs.array.add(faceConfig);
	}
	groupFaces.add(faceConfigs);


	faceConfigs = new CustomFaceGroupConfigs();
	faceConfigs.pageColumnCount = 5;
	faceConfigs.pageColumnCount = 5;
	faceConfigs.faceGroupId = 1;
	faceConfigs.faceIconPath = "4350/tt01@2x.png";
	faceConfigs.faceIconName = "4350";
	for (int i = 1; i <= 16; i++) {
		FaceConfig faceConfig = new FaceConfig();
		String index = "" + i;
		if (i < 10)
			index = "0" + i;
		faceConfig.assetPath = "4350/tt" + index + "@2x.png";
		faceConfig.faceName = "tt" + index + "@2x";
		faceConfig.faceWidth = 240;
		faceConfig.faceHeight = 240;
		faceConfigs.array.add(faceConfig);
	}
	groupFaces.add(faceConfigs);
	return groupFaces;
}

8.目前的版本4.3.81, 后面的文档更新很快, 新版本很多需要修改





















