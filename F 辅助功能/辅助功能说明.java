1.读官方文档
https://developer.android.google.cn/guide/topics/ui/accessibility/services.html
http://www.jianshu.com/p/959217070c87
http://www.cnblogs.com/itchq/articles/5648657.html
https://github.com/lendylongli/qianghongbao

2.根据官方文档,写一个类,实现你想实现的方法,例:AppLockService继承AccessibilityService
注:如果是手动生成的Service,要在清单文件中修改注册:
        <service---------------------------------这些自动生成的不能要,应该改成3.里面的内容★★★★★
            android:name=".service.AppLockService"
            android:enabled="true"
            android:exported="true">
        </service>

3.在清单文件AndroidManifest.xml中配置服务
    <service android:name=".MyAccessibilityService" //自己的服务名,例:.service.AppLockService
        android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
        android:label="@string/accessibility_service_label">//alt+回车:手机卫士12程序锁
        <intent-filter>
            <action android:name="android.accessibilityservice.AccessibilityService" />
        </intent-filter>

        <!--从Android 4.0开始,可以包括一个 <meta-data>元素-->
		<!-- 通过xml文件完成辅助功能相关配置，也可以在onServiceConnected中动态配置 -->
        <meta-data
            android:name="android.accessibilityservice"
            android:resource="@xml/accessibility_service_config" />
    </service>

4.在<project_dir>/res/xml/accessibility_service_config.xml里创建并写内容:
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeWindowStateChanged"//窗口发生变化,默认值:typeAllMask
    android:accessibilityFeedbackType="feedbackGeneric"	//通用的反馈方式,默认值:feedbackSpoken
    android:accessibilityFlags="flagDefault"
    android:canRetrieveWindowContent="true"	//能否获取到窗口的内容,不需要可删掉
    android:description="@string/accessibility_service_description"//alt+回车:手机卫士程序锁,保护您应用的隐私
    android:notificationTimeout="100"	//通知的时间超时,老师说不管,删掉
    android:packageNames="com.example.android.apis"//监听哪个应用,不配(删除)的话,监听所有应用,删除
    android:settingsActivity="com.example.android.accessibility.ServiceSettingsActivity"//设置页面,这儿不需要设置页面,老师说删掉
/>

根据文档可知:
accessibilityEventTypes:辅助功能事件的类型,默认值:"typeAllMask"
			更多类型点击下方的连接:android:accessibilityEventTypes

accessibilityFeedbackType:辅助功能反馈的类型,默认值:feedbackSpoken
			更多类型点击下方的连接:android:accessibilityFeedbackType

canRetrieveWindowContent:能否获取到窗口的内容,默认值:true,不需要可删掉

description:描述,你这个辅助功能能干什么,显示给辅助功能的界面供用户看


5. 运行app, 在设置->辅助功能中可以找到开启服务的入口
<p>
辅助功能服务的开启和关闭,都必须由用户在设置->辅助功能中进行

