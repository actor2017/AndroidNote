https://www.jiguang.cn/

推送数量和推送频率的限制是多少？https://docs.jiguang.cn//jpush/guideline/faq/
推送数量
没有限制！
通过控制台或 API 推送通知或消息，均不会限制推送的数量。

推送频率
免费版本的每个 Appkey 的最高推送频率为 600 次/分钟。
付费版本可享有更高的推送频率，具体请联系商务。


怎么样向特定的某个用户推送消息？https://docs.jiguang.cn//jpush/guideline/faq/
直接对用户的 registration ID 推送消息。 或在客户端使用 setAlias API 来为该用户指定别名。以便服务器端对该别名推送消息。


Android SDK FAQ https://docs.jiguang.cn/jpush/client/Android/android_faq/
安卓端常见问题


Android SDK 集成指南
https://docs.jiguang.cn//jpush/client/Android/android_guide/
资源下载, 包括 最新的SDK 和 DEMO : http://docs.jiguang.cn/jpush/resources/
把 .jar 和 .so copy到项目中,
AndroidManifest.xml
res 集成 SDK 必须添加的资源文件

★★★或者jcenter 自动集成步骤:★★★
使用 jcenter 自动集成的开发者，不需要在项目中添加 jar 和 so，jcenter 会自动完成依赖；
在 AndroidManifest.xml 中不需要添加任何 JPush SDK 相关的配置，jcenter 会自动导入

注意 ：如果需要处理收到的消息、使用 3.0.7 版本支持的别名与标签的新接口，
AndroidManifest 中的自定义广播接收器仍需开发者手动配置，参考 SDK 压缩包里的 AndroidManifest.xml 样例文件。


Project 根目录的主 gradle 中:
buildscript {
    repositories {
        jcenter()
    }
}
allprojets {
    repositories {
        jcenter()
    }
}

module 的 gradle 中:
android {
    defaultConfig {
        applicationId "com.xxx.xxx" //JPush 上注册的包名.

        ndk {
            //选择要添加的对应 cpu 类型的 .so 库,前2个就差不多。
            abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a', 'x86', 'x86_64', 'mips', 'mips64'
        }

        //一定要添加, 否则报错: Manifest merger failed with multiple errors, see logs
		//Task 'processDebugManifest--stacktrace' not found in root project
        manifestPlaceholders = [
            JPUSH_PKGNAME : applicationId,
            JPUSH_APPKEY : "你的 Appkey ", //JPush 上注册的包名对应的 Appkey.
            JPUSH_CHANNEL : "developer-default", //暂时填写默认值即可.
        ]
    }
}
dependencies {
    compile 'cn.jiguang.sdk:jpush:3.3.4'  // 此处以JPush 3.3.4 版本为例。
    compile 'cn.jiguang.sdk:jcore:2.1.0'  // 此处以JCore 2.1.0 版本为例。
}


<!--极光推送权限, 3.1.5 版本开始变为可选权限，在 3.1.5 前的版本为必须权限, 在这儿把这些加上-->
<uses-permission android:name="android.permission.WAKE_LOCK"/>
<!--从 JPush 3.1.5 版本开始变为可选权限，在 3.1.5 前版本为必须权限-->
<uses-permission android:name="android.permission.VIBRATE"/>
<!--从 JPush 3.3.2 版本开始变为可选权限，在 3.3.2 前版本为必须权限-->
<uses-permission android:name="android.permission.WRITE_SETTINGS"/>


集成 JPush Android SDK 的混淆:
请下载 4.x 及以上版本的 proguard.jar， 并替换你 Android SDK "tools\proguard\lib\proguard.jar"
请在工程的混淆文件中添加以下配置：
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }


错误问题:
如果在添加以上 abiFilter 配置之后 android Studio 出现以下提示：
NDK integration is deprecated in the current plugin. Consider trying the new experimental plugin
则在 Project 根目录的 gradle.properties 文件中添加：
    android.useDeprecatedNdk=true

注 : 使用 NDK r17 时，可能 Android Studio 会出现以下提示：
    A problem occurred starting process ‘command 
    ‘/Users/xxx/Library/Android/sdk/ndk-bundle/toolchains/mips64el-linux-android-4.9/prebuilt
    /darwin-x86_64/bin/mips64el-linux-android-strip”
   系统找不到指定的文件, 这是因为 NDK r17 之后不再支持 mips 平台，在 build.gradle 里增加如下配置可解决
    android {
        defaultConfig {
            .....
        }
        packagingOptions { 
            doNotStrip '*/mips/*.so' 
            doNotStrip '*/mips64/*.so' 
        }
    }

说明：若没有 res/drawable-xxxx/jpush_notification_icon 这个资源默认使用应用图标作为通知 icon，
在 5.0 以上系统将应用图标作为 statusbar icon 可能显示不正常，用户可定义没有阴影和渐变色的 icon 替换这个文件，文件名不要变。


/**
 * Application中初始化极光推送
 */
JPushInterface.setDebugMode(isDebugMode);//设置调试模式,在 init 接口之前调用
JPushInterface.init(this);//初始化
//stopPush，JPush 推送服务完全被停止。具体表现为：
//收不到推送消息
//极光推送所有的其他 API 调用都无效，不能通过 JPushInterface.init 恢复，需要调用 resumePush 恢复。
JPushInterface.stopPush(MyApplication.instance);
//JPushInterface.setAlias(this, 0, "");//瞎设置一个别名, 作用是接收不到消息(设置""好像没作用? 下次设置更复杂的字符串)


//Activity中
JPushInterface.resumePush(MyApplication.instance);//调用了此 API 后，极光推送完全恢复正常工作
JPushInterface.setAlias(this, 0, SPUtils.getString(Global.username));//设置别名, 还有其他推送方式
checkNotificationPermission();
}
//检查是否能显示Notification
private AlertDialog alertDialog;
private void checkNotificationPermission() {
	if (!NotificationHelper.areNotificationsEnabled(this)) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(this)
					.setTitle("没有通知栏权限")
					.setMessage("没有通知栏权限, 不能收到消息通知, 请打开通知权限.")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							NotificationHelper.gotoSet(activity);
						}
					}).setNegativeButton("取消", null)
					.setCancelable(false)
					.create();
		}
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
	}
}
    /**
     * 重写返回键,flagCount是全局变量
     * private int flagCount = 0;
     */
    @Override
    public void onBackPressed() {
        if (tabLayout.getSelectedTabPosition() != 0) {
            switchToPosition(0);
            return;
        }
        if (System.currentTimeMillis() - flagClicked <= 1000) {
            //调用了本 API 后，JPush 推送服务完全被停止。具体表现为：
            //收不到推送消息
            //极光推送所有的其他 API 调用都无效，不能通过 JPushInterface.init 恢复，需要调用 resumePush 恢复。
            JPushInterface.stopPush(MyApplication.instance);
//            JPushInterface.setAlias(this, 0, "");
            super.onBackPressed();
        } else {
            flagClicked = System.currentTimeMillis();
            toast("再点击一下退出");
        }
    }


日志过滤:
jpush等


