package com.heima.mobilesafe_work.receiver;

import android.app.admin.DeviceAdminReceiver;

/**
 * Created by Kevin.
 * <p>
 * 设备管理员组件
 * <p>
 * 1. 写一个类, 继承DeviceAdminReceiver
 * 2. 在清单文件中配置receiver
 * <receiver
 * android:name=".receiver.AdminReceiver"
 * android:description="@string/sample_device_admin_description"
 * android:label="@string/sample_device_admin"
 * android:permission="android.permission.BIND_DEVICE_ADMIN">
 * <meta-data
 * android:name="android.app.device_admin"
 * android:resource="@xml/device_admin_sample"/>
 * <p>
 * <intent-filter>
 * <action android:name="android.app.action.DEVICE_ADMIN_ENABLED"/>
 * </intent-filter>
 * </receiver>
 * <p>
 * 3. 在strings.xml中设置描述和标题
 * <string name="app_name">手机卫士121</string>
 * <!--设置描述和标题-->
 * <string name="sample_device_admin_description">来自string.xml的描述,显示在:设置→安全→"设备管理器"界面</string>
 * <string name="sample_device_admin">来自strings.xml的标题,显示在激活界面</string>
 * <p>
 * 4. 在res目录下新建xml文件夹, device_admin_sample.xml文件
 *    (因为在清单文件中配置了:android:resource="@xml/device_admin_sample"), 设置配置相关的信息
 * <device-admin xmlns:android="http://schemas.android.com/apk/res/android">
 * <uses-policies>
 * <limit-password />
 * <watch-login />
 * <reset-password />
 * <force-lock />
 * <wipe-data />
 * <expire-password />
 * <encrypted-storage />
 * <disable-camera />
 * </uses-policies>
 * </device-admin>
 * 5. 在设置->安全->设备管理器->激活/取消激活设备管理员
 * 6. 一旦激活, 就无法卸载; 必须取消激活才可卸载
 */

public class AdminReceiver extends DeviceAdminReceiver {

}
