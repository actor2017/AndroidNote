1. 一直 scanning files to index
  删除: C:\Users\my_user_name\.AndroidStudio3.3\system\caches

2.Default Activity not found
  1.Rebuild 看一下是否能编译成功.
  2.查看gradle的 applicationId 是否已经设置(断电重启后有几率发生为空)
  3.AndroidManifest.xml 中是否有默认Activity
    <activity
        android:name=".MainActivity"
        android:label="@string/app_name">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
  4.File => Invalidate Caches/Restart...
  5.删除: C:\Users\my_user_name\.AndroidStudio3.3\system\caches
  6.干脆重建一个项目吧！只不过感觉不是很优雅的样子。


3.右键不能创建Activity
  1.signingConfigs 写在 buildTypes 之前
  2.signingConfigs 中不能有 debug 签名配置


5.Android studio光标变成黑长方形的情况
1.网上说按insert键/shift+insert键
2.把数字键盘锁定，按数字0键（数字0也是ins）
