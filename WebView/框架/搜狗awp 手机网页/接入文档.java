//接入文档
//第一步
//下载AWP SDK aar文件放到工程libs目录下，并在工程build.gradle编译脚本中，添加如下改动：
android {
    aaptOptions {
      additionalParameters '--extra-packages', 'com.sogou.android.chromium:com.sogou.org.chromium.ui:com.sogou.com.android.webview.chromium:com.sogou.org.chromium.content:com.sogou.org.chromium.components.autofill:com.sogou.org.chromium.components.web_contents_delegate_android'
    }

    repositories {
        flatDir {
            dirs 'libs'
    }

    dependencies {
      compile(name: 'awp_sdk', ext: 'aar')
    }

//第二步
//AndroidManifest.xml中增加以下权限声明：
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.INTERNET" />

//第三步
//初始化AWP环境，在Application.onCreate()中增加如下代码：
        class MyApplication extends Application {

                @Override
                public void onCreate() {
                      super.onCreate();
                      AwpEnvironment.init(this, true);
                }
        }

//完成上述三个步骤，即可实现对Android系统WebView的性能增强和功能扩展；
//在应用第一次启动时，AWP会自动从本地查找或云端下载内核引擎，
//如果内核引擎是从云端下载，那么需要再次启动应用进程，AWP才会生效。
//其中，AWP对Android系统WebView扩展功能的详细说明和使用， 请参考API文档。
//http://awp.mse.sogou.com/api.html

//Now, you can use many fantasy features of AWP like these:

    // Enables debugging of web contents (HTML / CSS / JavaScript)//允许调试web内容（HTML/CSS/JavaScript）
    AwpEnvironment.getInstance().setAwpDebuggingEnabled(true);
    AwpExtension extension = AwpEnvironment.getInstance().getAwpExtension(mWebView);
    if (extension != null) {
        // Enables SmartImages loading//图片智能加载
        extension.getAwpSettings().setSmartImagesEnabled(true);
        // Enables NightMode//日/夜间模式
        extension.getAwpSettings().setNightModeEnabled(true);
    }
    // Enables AdBlock//广告过滤??
    AwpSharedStatics statics = AwpEnvironment.getInstance().getSharedStatics();
    if (statics != null) {
        statics.setAdBlockEnabled(true);
    }
