1，模拟器api22（23是6.0要求权限）?

2.本例说明:开机广播开启服务,服务里面注册电池改变的广播接收者,
  广播接收者监听电池变化,每变化1次检查notificationService是否启动,如果没有启动,启动它

4，权限
<!--内部推送-->
<uses-sdk android:minSdkVersion="3" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.VIBRATE" />
<!--开机自启权限-->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />


5.
        <!--接收notification的服务-->
        <service
            android:name="org.androidpn.client.NotificationService"
            android:enabled="true"
            android:label="NotificationService">
            <intent-filter>
                <action android:name="org.androidpn.client.NotificationService"/>
            </intent-filter>
        </service>

        <!-- 接收开机启动的广播 -->
        <receiver
            android:name="org.androidpn.client.MyBootReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED"/>
            </intent-filter>
        </receiver>
        <!--监听电池变化的服务-->
        <service
            android:name="org.androidpn.client.BatteryService"
            android:enabled="true"
            android:exported="true">
        </service>


6.登录之后MainActivity中
        //notification的service
        if (!ServiceStateUtils.isServiceRunning(this, NotificationService.class)) {
            ServiceManager serviceManager = new ServiceManager(this);
            serviceManager.setNotificationIcon(R.drawable.icon);
            serviceManager.startService();
            serviceManager.setAlias("xuyusong");
        }
        //开启电池电量变化的服务
        if (!ServiceStateUtils.isServiceRunning(this, BatteryService.class)) {
            startService(new Intent(this, BatteryService.class));
        }


7.需要修改的地方列表:
BatteryService(必改)
Constants
NotificationIQ
NotificationIQProvider
NotificationPacketListener
NotificationReceiver
Notifier(必改)
XmppManager(244,379行必改);244改成登录名(1001).   379改密码,固定为123.
注意:如果登录不上,服务端的密码是否没有清除?


