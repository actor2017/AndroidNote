APP支付首页:
https://pay.weixin.qq.com/static/product/product_intro.shtml?name=app

1.添加依赖:
https://developers.weixin.qq.com/doc/oplatform/Downloads/Android_Resource.html

//https://bintray.com/wechat-sdk-team/maven
compile 'com.tencent.mm.opensdk:wechat-sdk-android-with-mta:+'//微信登录支付,包含统计功能(统计什么鬼?微信分享、登录、收藏、支付等功能?)
//https://bintray.com/wechat-sdk-team/maven/com.tencent.mm.opensdk%3Awechat-sdk-android-without-mta	(官方说发布到这儿)
//https://mvnrepository.com/artifact/com.tencent.mm.opensdk/wechat-sdk-android-without-mta?repo=springio-plugins-release	(这儿好像也可以看最新版本号)
compile 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:5.5.8'
下载demo也在这个页面下载, 不要在另外个页面下载, 太傻逼了

使用微信语音识别接口、语音合成接口。点击下载 语音SDK+Demo+开发文档
使用微信图像识别接口。点击下载 图像SDK+Demo+开发文档
使用微信卡券功能接口。点击下载 卡券SDK+开发文档


2.权限
<!--微信支付-->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<!-- for mta statistics(微信统计) -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

4.在包名/wxapi/WXPayEntryActivity extends Activity implements IWXAPIEventHandler

5.在清单文件中注册
<!--微信支付-->
<!--android:exported=“true”，这个属性代表着运行被外界程序所启动这个Activity-->
<activity
    android:name=".wxapi.WXPayEntryActivity"
    android:exported="true"
    android:launchMode="singleTop">
    <!--<intent-filter>-->
    <!--<action android:name="android.intent.action.VIEW"/>-->
    <!--<category android:name="android.intent.category.DEFAULT"/>-->
    <!--<data android:scheme="wx8aee7894414e5f5a"/>-->
    <!--</intent-filter>-->
</activity>
<!--微信登录-->
<activity
    android:name=".wxapi.WXEntryActivity"
    android:exported="true"/>

6.在需要支付的页面初始化
private IWXAPI     iwxapi;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_money_add);

    EventBus.getDefault().register(this);
    iwxapi = WXAPIFactory.createWXAPI(this, Global.WX_APPID, true);
    iwxapi.registerApp(Global.WX_APPID);// 将应用的appId注册到微信
}

7.调用服务端接口, 获取partnerid等字段, 然后调用微信支付
/**
 * 微信支付
 */
private void wxPay(CoinChargeWXPayInfo.DataBean data) {
    //在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
    // iwxapi.registerApp(Global.WX_APPID);
    PayReq req = new PayReq();
    req.appId           = data.appid;//你的微信appid
    req.partnerId       = data.partnerid;//商户号
    req.prepayId        = data.prepayid;//预支付交易会话ID
    req.nonceStr        = data.noncestr;//随机字符串
    req.timeStamp       = data.timestamp;//时间戳
    req.packageValue    = data.packageX;//扩展字段,这里固定填写Sign=WXPay
    req.sign            = data.sign;//签名
    //      req.extData         = "app data"; // optional
    iwxapi.sendReq(req);
}

/**
 * Eventbus微信支付回调
 */
@Subscribe(threadMode = ThreadMode.MAIN)
public void onReceivedPayResult(MessageEvent messageEvent) {
    if (messageEvent.code == Global.MSG_EVT_WX_PAY_RESULT) {
        toast("支付成功!");
    }
}

@Override
protected void onDestroy() {
	super.onDestroy();
	EventBus.getDefault().unregister(this);
}

8.混淆
##----------End: proguard configuration for 微信登录支付----------
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
##----------End: proguard configuration for 微信登录支付----------

