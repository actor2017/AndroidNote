Android��λSDK
1.������Կ
http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/key

2.���û���
��.������������������µĿ��ļ�,SDK,ʾ������,��ο�:
http://lbsyun.baidu.com/index.php?title=android-locsdk/geosdk-android-download

��.ʹ��Eclipse�����Ŀ����ߣ���SO�ļ���ѹ���ļ���ѹ�������Ѷ�Ӧ�ܹ��µ�SO�ļ����뿪�����Լ�APP�Ķ�Ӧ�ܹ��µ��ļ����У�����ȫ����������߳�������ԣ�����JAR�ļ����������̵�libsĿ¼�£����������ڳ�����ʹ��Android��λSDK��
ʹ��AndroidStutio�Ŀ����߳������������⣬����Ҫ��Module��build.gradle������SO�ļ���ʹ�ã�������ʾ��

    defaultConfig {
        applicationId "com.kuchuanyun.wisdomcitymanagement"//����Ҫ����
    }
    //���so��,��buildTypes { ͬ��
    sourceSets{
        main{
            jniLibs.srcDirs = ['libs']
        }
    }

�嵥�ļ�
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.kuchuanyun.wisdomcitymanagement">//���ð���,ȷ�����������һ��


��.�����������ϵͳӦ�ã�������Ҫ�ڹ���������SO�ļ�������Ҫ�ֶ��Ѷ�Ӧ�ܹ���SO�ļ�������/system/lib�£������64λϵͳ,����Ҫ��64λ��SO�ļ�������/sytem/lib64�¡�
ע�⣺�°汾�Ķ�λSDK�������߳���Ҫ����JAR��֮�⣬ͬʱ��Ҫ��עSO�ļ��Ƿ��и��¡����SO�ļ����Ƹı䣬��SO�ļ��и��£�������Ҫ��ʱ�滻���ϰ汾������ᵼ�¶�λʧ�ܡ�


��.����AcessKey
����AK����Application��ǩ�м���
        <!--�ٶȶ�λ����AK����Application��ǩ�м���-->
        <meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:value="AK"/><!--value:�����������AK-->


��.����AndroidManifest.xml
        <!--�ٶȶ�λ����-->
        <service
            android:name="com.baidu.location.f"
            android:enabled="true"
            android:process=":remote">
        </service>

	<!--�ٶȶ�λ-->
    <!-- ���Ȩ�����ڽ������綨λ-->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <!-- ���Ȩ�����ڷ���GPS��λ-->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <!-- ���ڷ���wifi������Ϣ��wifi��Ϣ�����ڽ������綨λ-->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <!-- ��ȡ��Ӫ����Ϣ������֧���ṩ��Ӫ����Ϣ��صĽӿ�-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <!-- ���Ȩ�����ڻ�ȡwifi�Ļ�ȡȨ�ޣ�wifi��Ϣ�������������綨λ-->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <!-- ���ڶ�ȡ�ֻ���ǰ��״̬-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <!-- д����չ�洢������չ��д�����ݣ�����д�����߶�λ����-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <!-- �������磬���綨λ��Ҫ����-->
    <uses-permission android:name="android.permission.INTERNET"/>
    <!-- SD����ȡȨ�ޣ��û�д�����߶�λ����-->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <uses-permission android:name="android.permission.READ_LOGS"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>


��.import�����
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//�����õ�λ�����ѹ��ܣ���Ҫimport����
import com.baidu.location.Poi;


3.����ϵ˵��
Ŀǰ������Ҫ��������������ϵ��
1. WGS84��Ϊһ�ִ������ϵ��Ҳ��Ŀǰ�㷺ʹ�õ�GPSȫ�����Ƕ�λϵͳʹ�õ�����ϵ��
2. GCJ02����ʾ��������ּ��ܵ����ꣻ
3. BD09��Ϊ�ٶ�����ϵ������bd09ll��ʾ�ٶȾ�γ�����꣬bd09mc��ʾ�ٶ�ī�����������ꣻ
Android��λSDK��Ʒ��֧��ȫ��λ���ܹ���׼�Ļ�ȡ��γ����Ϣ�����ݿ����ߵ����ã��ڹ��ڻ�õ�����ϵ���Ϳ����ǣ���������ꡢ�ٶ�ī�������� �� �ٶȾ�γ�����ꡣ�ں��������ֻ�ܻ��WGS84���ꡣ�뿪������ʹ�ù�����ע������ѡ��


4.��ȡλ����Ϣ
�ۺ϶�λ����ָ���Ǹ����û�ʵ�����󣬷����û���ǰλ�õĻ�����λ���񣬰���GPS�����綨λ��WiFi��λ�ͻ�վ��λ�����ܡ�������λ����ͬʱ��֧��λ��������Ϣ���ܣ����߶�λ���ܣ�λ�����ѹ��ܺ�λ�����廯���ܡ�
��.��application�г�ʼ��:
  public LocationClient mLocationClient = null;
 
  public BDAbstractLocationListener myListener = new MyLocationListener();
  //BDAbstractLocationListenerΪ7.2�汾������Abstract���͵ļ����ӿڣ�ԭ��BDLocationListener�ӿ���ʱͬ��������
  //���������ο������е�˵��
 
  public void onCreate() {
        mLocationClient = new LocationClient(getApplicationContext());//����LocationClient��
        mLocationClient.registerLocationListener(myListener);//ע���������,������Ƕ�ʱ����λ��,�������ע��.�õ�ʱ���ע��.
  }
LocationClient���Ƕ�λSDK�ĺ����࣬���巽����μ���ο����ֵĽ��ܡ�

��.�ڶ��������ö�λSDK����
���ö�λ������������λģʽ���߾��ȶ�λģʽ���͹��Ķ�λģʽ�ͽ����豸��λģʽ���������������ͣ��Ƿ��GPS���Ƿ񷵻ص�ַ��Ϣ��λ�����廯��Ϣ��POI��Ϣ�ȵȡ�
�����������Ͱ�����
1. gcj02����������ꣻ
2. bd09���ٶ�ī�������ꣻ
3. bd09ll���ٶȾ�γ�����ꣻ
ע�⣺���������λ���Ĭ�ϡ���ֻ����WGS84�������ꡣ
LocationClientOption�࣬�����������ö�λSDK�Ķ�λ��ʽ�����磺
private void initLocation(){
 
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(LocationMode.Hight_Accuracy);
    //��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
 
    option.setCoorType("bd09ll");
    //��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
 
    int span=1000;
    option.setScanSpan(span);
    //��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
 
    option.setIsNeedAddress(true);
    //��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
 
    option.setOpenGps(true);
    //��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
 
    option.setLocationNotify(true);
    //��ѡ��Ĭ��false�������Ƿ�GPS��Чʱ����1S/1��Ƶ�����GPS���
 
    option.setIsNeedLocationDescribe(true);
    //��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
 
    option.setIsNeedLocationPoiList(true);
    //��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
 
    option.setIgnoreKillProcess(false);
    //��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��  
 
    option.setIgnoreCacheException(false);
    //��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
 
    option.setEnableSimulateGps(false);
    //��ѡ��Ĭ��false�������Ƿ���Ҫ����GPS��������Ĭ����Ҫ
 
    option.setWifiValidTime(5*60*1000);
    //��ѡ��7.2�汾�������������������������ӿڣ��״�������λʱ�������жϵ�ǰWiFi�Ƿ񳬳���Ч�ڣ�������Ч�ڵĻ�����������ɨ��WiFi��Ȼ���ٶ�λ
 
    mLocationClient.setLocOption(option);
}
�߾��ȶ�λģʽ�����ֶ�λģʽ�£���ͬʱʹ�����綨λ��GPS��λ�����ȷ�����߾��ȵĶ�λ�����
�͹��Ķ�λģʽ�����ֶ�λģʽ�£�����ʹ��GPS���ж�λ��ֻ��ʹ�����綨λ��WiFi��λ�ͻ�վ��λ����
�����豸��λģʽ�����ֶ�λģʽ�£�����Ҫ�������磬ֻʹ��GPS���ж�λ������ģʽ�²�֧�����ڻ����Ķ�λ��



���Ĳ�����ʼ��λ
������λ���������£�
mLocationClient.start();
 stop���رն�λSDK��
��v7.2�汾������LocationClient.reStart()������������ĳЩ�ض����쳣������������λ�������쳣������μ�������ָ�� �C �����롷���ֵĽ��ܡ�
�����߶�λ��������ǵ��ζ�λ�ĳ��������յ���λ���֮��ֱ�ӵ���stop�������ɡ�
���stop֮����Ȼ����ж�λ�������ٴ�start�ȴ���λ����ص����ɡ�
����������밴���Լ��߼�����λ��������start֮�����Լ����߼�����LocationClient.requestLocation()������������������λSDK�ڲ���λ�߼����ȴ���λ�ص����ɡ�



λ������ʹ��
λ�������������3�Σ�3�ι��󽫲������ѡ� ������Ҫ�ٴ����ѣ�����Ҫ�޸����ѵ����꣬����ͨ������SetNotifyLocation()��ʵ�֡�

//λ��������ش���
mNotifyer = new NotifyLister();
 
mNotifyer.SetNotifyLocation(42.03249652949337,113.3129895882556,3000,"gps");
//4����������Ҫλ�����ѵĵ�����꣬���庬������Ϊ��γ�ȣ����ȣ����뷶Χ������ϵ����(gcj02,gps,bd09,bd09ll)
 
mLocationClient.registerNotify(mNotifyer);
//ע��λ�����Ѽ����¼��󣬿���ͨ��SetNotifyLocation ���޸�λ���������ã��޸ĺ�������Ч��
 
//BDNotifyListnerʵ��
public class NotifyLister extends BDNotifyListener{
 
    public void onNotify(BDLocation mlocation, float distance){
 
        mVibrator01.vibrate(1000);
        //�������ѵ��趨λ�ø���
    }
}
 
//ȡ��λ������
mLocationClient.removeNotifyEvent(mNotifyer);



6.ע������http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/notice
ǿ�ҽ��鿪�������ò����ܺ��Լ���prodName���������������ṩ���õĶ�λ���Ϸ���

��λSDK����ע��GPS�������ʹ��Ȩ�ޡ�

ʹ�ö�λSDK�뾡����֤��������ͨ����GPS��λ��ʽ����Ҫ�������������Ҫ��ַ��Ϣ��λ�����廯��POI����Ϣ����Ҫ�����ģ���Ŀǰ���߹����Ѿ�֧�ֻ�ȡ������Ϣ�������߶�λ���ǰٷְٶ��ܶ�λ�ɹ��ģ�����������α�֤���߶�λ�ɹ��Ĵ�ʩ��

��λSDK���Է��ع�������ꡢ�ٶȾ�γ������ �� �ٶ�ī�������꣬�����������꣨�������ֻ�ܷ���WGS84���͵����꣩ ������Ҫ����λ���λ��ͨ���ٶ�Android��ͼ SDK���е�ͼչʾ����ֱ�ӷ��ذٶȾ�γ������Ķ�λ������ƫ��ĵ����ڰٶȵ�ͼ�ϡ�

�е��ƶ��豸������Ϊ��ʡ����Զ��ر��������ӣ���ʱ���綨λģʽ�Ķ�λʧЧ�����⣬�������ƶ��豸������CPU���ߣ���ʱ��λ����ҲʧЧ��������Ҫʵ����CPU����״̬���趨ʱ��λ��������AlarmManager ʵ��1��CPU�ɽ��ѵ�Timer����ʱ����λ��



7.Android 6.0ϵͳ������֪http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/androidmnotice
Google��Android 6.0�������˶�̬Ȩ�޻�ȡ���ƣ�Runtime Permission����ʹ��Android��Ȩ�޹�������ϸ����ơ�
��̬Ȩ�޻�ȡҪ�󿪷����ڵ����漰���Ȩ�޵Ĵ���ʱ��ʹ��ϵͳ�ӿ�����̬��������ӦȨ�ޡ���λSDK�漰Ȩ�޼��ڴ˷����С�
��δ��ȡ����λȨ������£���λSDK��ȡ���Ķ�λ���ݣ���վ��WiFi����Ϊ��ֵ������޷���Ч��λ����λ����᷵�ش�����167��

��̬Ȩ�޻��ƵĿ���
Android 6.0���ڶ�̬Ȩ�޻��ƵĿ�����Ҫ����Ӧ���趨��targetSdkVersion������������
targetSdkVersion	�Ƿ�Ĭ�Ͻ�������Ȩ��	�Ƿ�����̬Ȩ��
	<23			��			��
	>=23			��			��
�����Ҫ����Ŀ�����ļ����޸�����targetSdkVersion���á�
����Eclipse�����������AndroidManifest.xml�д��룻AndroidStudio�����������build.gradle�еĴ��롣

��̬Ȩ�޴�������
��Android 6.0ϵͳ�У���Ҫ��̬��ȡ��Ȩ���漰����
1����ȡ�ֻ�״̬��
Manifest.permission.READ_PHONE_STATE

2����ȡλ����Ϣ��
Manifest.permission.ACCESS_COARSE_LOCATION
Manifest.permission.ACCESS_FINE_LOCATION

3����дSD����
Manifest.permission.READ_EXTERNAL_STORAGE
Manifest.permission.WRITE_EXTERNAL_STORAGE

��ȡȨ��ʹ��API
1��int android.content.Context.checkSelfPermission(String permission)���˽ӿڻᴥ��ϵͳ�������û�ѡ��󴥷�Activity�еĻص�������
void YourActivity.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)

��READ_PHONE_STATEΪ����
private static final int BAIDU_READ_PHONE_STATE =100;
���Զ���һ��Ȩ�޻�ȡ�룬���ڻص�����������Ӧ���� ����checkSelfPermission�ӿ�����Ȩ��:
if(mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) !=PackageManager.PERMISSION_GRANTED) {
 
    // ����һ����������Ȩ�ޣ����ṩ���ڻص����صĻ�ȡ�루�û�����)
    requestPermissions( new String[]{ 
 
        Manifest.permission.READ_PHONE_STATE },BAIDU_READ_PHONE_STATE);
 
}

3����Activity��onRequestPermissionResult�ص�������������
@Override public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
 
    super.onRequestPermissionsResult(requestCode, permissions,grantResults);
 
    switch(requestCode) {
 
        // requestCode����������Ȩ�޻�ȡ�룬��checkSelfPermissionʱ����
        case 1:
            BAIDU_READ_PHONE_STATE:
 
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 
                // ��ȡ��Ȩ�ޣ�����Ӧ�������ö�λSDKӦ��ȷ�����Ȩ�޾�����Ȩ�������������λʧ�ܣ�
 
            } else{
 
                // û�л�ȡ��Ȩ�ޣ������⴦��
            }
            break;
 
        default:
            break;
 
     }
}

Android 6.0ϵͳ��λ�ÿ���

Android 6.0ԭ��ϵͳ�벿�ֳ��̶��Ƶ�6.0ϵͳ��ϵͳ��λ���ر��������в��졣����������
ϵͳ					��������			�Զ�λ��Ӱ��
							ԭ��ϵͳ�£�λ�ÿ��ؿ���Ӱ�쵽ϵͳ����GPS�����綨λ��
							�ڹر�λ�ÿ�������£�Ӧ���޷���ȡ��Wifi��Ϣ��
ԭ��ϵͳ��Ҳ����δ���ж��Ƶ�ϵͳ��	λ����Ϣ	Ҳ�޷�ʹ��GPS������ʹ�û�վ��λ��cl���ͣ�*��
							��ɶ�λ�����

							��λ�ÿ��غ�Ӧ�ÿ���ʹ��Wifi��Ϣ��GPS���ж�λ��
							����������λ����
//-------------------------------------------------------------------------------------------------------
							���̶���ϵͳ�����֣����ؽ�Ӱ��GPSʹ�ã�
							�ر�GPS��������£�Ӧ���Կɷ��ʵ�Wifi��Ϣ��
�����������̶���ϵͳ	GPS				����ʹ��wifi��λ

							��GPS��������£�Ӧ�òſ���ʹ��GPS��λ

ע��
1.����ԭ��ϵͳ��λ���ػ�Ӱ��Wifi��ȡ�����⣬�ɲ���Google Android Bug������̳��
https://code.google.com/p/android/issues/detail?id=185370
����Android6.0ԭ��ϵͳ��֪���⣬��ҪGoogle�޸���
���̶���ROM���ֻ��������졣Ҳ�ɲ��չ���һЩ����������������
http://www.jianshu.com/p/3400ca0deeee.

2.��������Android6.0�����λ�ÿ��عرպ��޷���λ�����⣬Ŀǰ�������µ�Android6.0�汾ԭ��ϵͳ���Ѳ����ڴ����⡣
���6.0ϵͳ��λ�ÿ������⡣����GPS���õ���Ϊ�ڸ��汾��һ�£���˿��Լ򵥵�ʹ��ϵͳAPI���ж��û��Ƿ����λ�ð�ť���ü�⵽ϵͳ�汾Ϊ6.0�������û�δ�򿪰�ťʱ��������ʾ��
LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 
if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
 
    // δ��λ�ÿ��أ����ܵ��¶�λʧ�ܻ�λ��׼����ʾ�û�������Ӧ����
}

8.������http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/ermsg
61��GPS��λ�����GPS��λ�ɹ���
62���޷���ȡ��Ч��λ���ݣ���λʧ�ܣ�������Ӫ���������WiFi�����Ƿ�����������������������λ��
63�������쳣��û�гɹ������������������ȷ�ϵ�ǰ�����ֻ������Ƿ�ͨ����������������λ��
65����λ����Ľ����
66�����߶�λ�����ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ����
67�����߶�λʧ�ܡ�ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ����
68����������ʧ��ʱ�����ұ������߶�λʱ��Ӧ�ķ��ؽ����
161�����綨λ��������綨λ�ɹ���
162���������Ľ���ʧ�ܣ�һ�������ڿͻ���SO�ļ�����ʧ����ɣ����ϸ���տ���ָ�ϻ�demo�����������ӦSO�ļ���
167������˶�λʧ�ܣ���������Ƿ���û�ȡλ����ϢȨ�ޣ�������������λ��
502��AK���������밴��˵���ĵ���������AK��
505��AK�����ڻ��߷Ƿ����밴��˵���ĵ���������AK��
601��AK���񱻿������Լ����ã��밴��˵���ĵ���������AK��
602��key mcode��ƥ�䣬����AK���ù����а�ȫ�����������⣬��ȷ����SHA1��ȷ����;���ֺ���Ӣ��״̬���Ұ���������ǰ����Ӧ�õİ������밴��˵���ĵ���������AK��
501��700��AK��֤ʧ�ܣ��밴��˵���ĵ���������AK��

1. ������ܶ�λ����¼���󷵻��룬�����ٶȵ�ͼ����ƽ̨��̳Andriod��λSDK����н��н�����
��ַ��http://bbs.lbsyun.baidu.com/forum.php?mod=forumdisplay&fid=10 ��
2. ������ֵ��162~167���뽫�����롢IMEI�Ͷ�λʱ�䷴��������loc-bugs@baidu.com���Ա����Ǹ���׷�����⡣
