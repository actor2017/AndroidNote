1��ģ����api22��23��6.0Ҫ��Ȩ�ޣ�?

2.����˵��:�����㲥��������,��������ע���ظı�Ĺ㲥������,
  �㲥�����߼�����ر仯,ÿ�仯1�μ��notificationService�Ƿ�����,���û������,������

4��Ȩ��
<!--�ڲ�����-->
<uses-sdk android:minSdkVersion="3" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.VIBRATE" />
<!--��������Ȩ��-->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />


5.
        <!--����notification�ķ���-->
        <service
            android:name="org.androidpn.client.NotificationService"
            android:enabled="true"
            android:label="NotificationService">
            <intent-filter>
                <action android:name="org.androidpn.client.NotificationService"/>
            </intent-filter>
        </service>

        <!-- ���տ��������Ĺ㲥 -->
        <receiver
            android:name="org.androidpn.client.MyBootReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED"/>
            </intent-filter>
        </receiver>
        <!--������ر仯�ķ���-->
        <service
            android:name="org.androidpn.client.BatteryService"
            android:enabled="true"
            android:exported="true">
        </service>


6.��¼֮��MainActivity��
        //notification��service
        if (!ServiceStateUtils.isServiceRunning(this, NotificationService.class)) {
            ServiceManager serviceManager = new ServiceManager(this);
            serviceManager.setNotificationIcon(R.drawable.icon);
            serviceManager.startService();
            serviceManager.setAlias("xuyusong");
        }
        //������ص����仯�ķ���
        if (!ServiceStateUtils.isServiceRunning(this, BatteryService.class)) {
            startService(new Intent(this, BatteryService.class));
        }


7.��Ҫ�޸ĵĵط��б�:
BatteryService(�ظ�)
Constants
NotificationIQ
NotificationIQProvider
NotificationPacketListener
NotificationReceiver
Notifier(�ظ�)
XmppManager(244,379�бظ�);244�ĳɵ�¼��(1001).   379������,�̶�Ϊ123.
ע��:�����¼����,����˵������Ƿ�û�����?


