http://xg.qq.com/

下载Demo:
https://xg.qq.com/xg/ctr_index/download

1.1.1. 导入依赖
如果在添加以上 abiFilter 配置之后 Android Studio 出现以下提示：
NDK integration is deprecated in the current plugin. Consider trying the new experimental plugin.
则在 Project 根目录的 gradle.properties 文件中添加：android.useDeprecatedNdk=true
https://xg.qq.com/docs/android_access/upgrade_guide.html

android {
	defaultConfig {
		manifestPlaceholders = [
                XG_ACCESS_ID : "2100338579",
                XG_ACCESS_KEY: "AQM8J759C2AT",
//                HW_APPID: "华为的APPID", //如果需要华为通道，则加上华为的APPID
//                PACKAGE_NAME : applicationId, //如果需要加入小米通道，则加上应用包名
        ]
	}
}

    //信鸽依赖
    implementation 'com.tencent.xinge:xinge:4.3.2-release'
    implementation 'com.tencent.wup:wup:1.0.0.E-Release'
    implementation 'com.tencent.jg:jg:1.1'
    implementation 'com.tencent.mid:mid:4.0.7-Release'
//    implementation 'com.tencent.xinge:mipush:4.3.2-xiaomi-release'//小米
//    implementation 'com.tencent.xinge:xgmz:4.3.2-meizu-release'//魅族
//    implementation 'com.tencent.xinge:xghw:4.3.2-huawei-release'//华为


//如需监听消息请参考XGBaseReceiver接口或者是 demo 的 MessageReceiver 类。自行继承XGBaseReceiver并且在配置文件中配置如下内容
        <!-- 信鸽接收消息和结果反馈的receiver，需要开发者自己在AndroidManifest.xml静态注册 -->
        <!-- <receiver android:name="com.tencent.android.tpush.XGPushBaseReceiver"> -->
        <!-- </receiver> -->
        <receiver
            android:name=".receiver.XGMessageReceiver"
            android:exported="true">
            <intent-filter>
                <!-- 接收消息透传 -->
                <action android:name="com.tencent.android.tpush.action.PUSH_MESSAGE"/>
                <!-- 监听注册、反注册、设置/删除标签、通知被点击等处理结果 -->
                <action android:name="com.tencent.android.tpush.action.FEEDBACK"/>
            </intent-filter>
        </receiver>

//Global.java
    /**
     * 信鸽推送
     */
    public static final String XG_APP_ID = "4aaaca270811d";
    public static final String XG_SECRET_KEY = "ad834bf4244b68b5cec3cf80a062a587";
    public static final String XG_ACCESS_ID = "2100338579";
    public static final String XG_ACCESS_KEY = "AQM8J759C2AT";


    //MainActivity中
    private void initXg() {
        XGPushConfig.enableDebug(this, ConfigUtils.isDebugMode);
        XGPushConfig.setNotificationShowEnable(this, true);
//        XGPushConfig.enableOtherPush(getApplicationContext(), false);
//        XGPushConfig.setMiPushAppId(getApplicationContext(), "APPID");
//        XGPushConfig.setMiPushAppKey(getApplicationContext(), "APPKEY");
//        XGPushConfig.setMzPushAppId(this, "APPID");
//        XGPushConfig.setMzPushAppKey(this, "APPKEY");
        /**
         * account: 绑定的账号，绑定后可以针对账号发送推送消息，account不能为单个字符如“2”，“a”。
         */
        XGPushManager.bindAccount(this, "username", new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                logFormat("注册成功: 设备token=%s, flag=%d", String.valueOf(data), flag);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                logFormat("注册失败: errCode=%d, msg=%s, data=%s", errCode, msg, String.valueOf(data));
            }
        });
    }

1.4. 代码混淆
https://xg.qq.com/docs/android_access/upgrade_guide.html


1.2. 推送数据问题
https://xg.qq.com/docs/android_access/android_faq.html
[推送暂停]
	全量推送限制(V2、V3)：
		全量推送每小时最多只能创建30次推送，超过30次会推送失败。
		相同内容的全量推送每小时只能推送一次，超过一次推送会推送失败。
	
	标签推送限制(V3)：
		同一应用每小时最多只能创建30次标签推送，超过30次会推送失败。
		同一标签相同内容的推送每小时只能推送一次，超过一次推送会推送失败。

[效果统计]
	次日：推送完第二天才能看到推送数据
	实时：推送完马上可以看到推送数据。目前每周仅支持14次的实时数据统计

1.3. 消息点击事件以及跳转页面方法
https://xg.qq.com/docs/android_access/android_faq.html
由于目前SDK点击消息默认会有点击事件，默认的点击事件是打开主界面。所以在终端点击消息回调的「onNotifactionClickedResult」方法内设置跳转操作时，自定义的跳转和默认的点击事件造成冲突。结果是点击之后会跳转到指定界面过后再回到主界面，所以不能再「onNotifactionClickedResult」内设置跳转

解决办法如下(推荐使用第一种方式)：

[1] 使用Intent来跳转指定页面（Android 3.2.3 以上版本使用此方式）
[2] 在下发消息的时候设置点击消息要跳转的页面


Android SDK 错误码对照表
https://xg.qq.com/docs/android_access/android_returncode.html

Android P 兼容方法
https://xg.qq.com/docs/android_access/android_p_compatibility.html



1. Rest API 概述（V3）
https://xg.qq.com/docs/server_api/v3/rest_api_summary.html
