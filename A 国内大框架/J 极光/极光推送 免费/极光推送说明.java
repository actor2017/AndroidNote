https://www.jiguang.cn/

��������������Ƶ�ʵ������Ƕ��٣�https://docs.jiguang.cn//jpush/guideline/faq/
��������
û�����ƣ�
ͨ������̨�� API ����֪ͨ����Ϣ���������������͵�������

����Ƶ��
��Ѱ汾��ÿ�� Appkey ���������Ƶ��Ϊ 600 ��/���ӡ�
���Ѱ汾�����и��ߵ�����Ƶ�ʣ���������ϵ����


��ô�����ض���ĳ���û�������Ϣ��https://docs.jiguang.cn//jpush/guideline/faq/
ֱ�Ӷ��û��� registration ID ������Ϣ�� ���ڿͻ���ʹ�� setAlias API ��Ϊ���û�ָ���������Ա�������˶Ըñ���������Ϣ��


Android SDK FAQ https://docs.jiguang.cn/jpush/client/Android/android_faq/
��׿�˳�������


Android SDK ����ָ��
https://docs.jiguang.cn//jpush/client/Android/android_guide/
��Դ����, ���� ���µ�SDK �� DEMO : http://docs.jiguang.cn/jpush/resources/
�� .jar �� .so copy����Ŀ��,
AndroidManifest.xml
res ���� SDK ������ӵ���Դ�ļ�

�������jcenter �Զ����ɲ���:����
ʹ�� jcenter �Զ����ɵĿ����ߣ�����Ҫ����Ŀ����� jar �� so��jcenter ���Զ����������
�� AndroidManifest.xml �в���Ҫ����κ� JPush SDK ��ص����ã�jcenter ���Զ�����

ע�� �������Ҫ�����յ�����Ϣ��ʹ�� 3.0.7 �汾֧�ֵı������ǩ���½ӿڣ�
AndroidManifest �е��Զ���㲥���������迪�����ֶ����ã��ο� SDK ѹ������� AndroidManifest.xml �����ļ���


Project ��Ŀ¼���� gradle ��:
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

module �� gradle ��:
android {
    defaultConfig {
        applicationId "com.xxx.xxx" //JPush ��ע��İ���.

        ndk {
            //ѡ��Ҫ��ӵĶ�Ӧ cpu ���͵� .so ��,ǰ2���Ͳ�ࡣ
            abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a', 'x86', 'x86_64', 'mips', 'mips64'
        }

        //һ��Ҫ���, ���򱨴�: Manifest merger failed with multiple errors, see logs
		//Task 'processDebugManifest--stacktrace' not found in root project
        manifestPlaceholders = [
            JPUSH_PKGNAME : applicationId,
            JPUSH_APPKEY : "��� Appkey ", //JPush ��ע��İ�����Ӧ�� Appkey.
            JPUSH_CHANNEL : "developer-default", //��ʱ��дĬ��ֵ����.
        ]
    }
}
dependencies {
    compile 'cn.jiguang.sdk:jpush:3.3.4'  // �˴���JPush 3.3.4 �汾Ϊ����
    compile 'cn.jiguang.sdk:jcore:2.1.0'  // �˴���JCore 2.1.0 �汾Ϊ����
}


<!--��������Ȩ��, 3.1.5 �汾��ʼ��Ϊ��ѡȨ�ޣ��� 3.1.5 ǰ�İ汾Ϊ����Ȩ��, ���������Щ����-->
<uses-permission android:name="android.permission.WAKE_LOCK"/>
<!--�� JPush 3.1.5 �汾��ʼ��Ϊ��ѡȨ�ޣ��� 3.1.5 ǰ�汾Ϊ����Ȩ��-->
<uses-permission android:name="android.permission.VIBRATE"/>
<!--�� JPush 3.3.2 �汾��ʼ��Ϊ��ѡȨ�ޣ��� 3.3.2 ǰ�汾Ϊ����Ȩ��-->
<uses-permission android:name="android.permission.WRITE_SETTINGS"/>


���� JPush Android SDK �Ļ���:
������ 4.x �����ϰ汾�� proguard.jar�� ���滻�� Android SDK "tools\proguard\lib\proguard.jar"
���ڹ��̵Ļ����ļ�������������ã�
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }


��������:
������������ abiFilter ����֮�� android Studio ����������ʾ��
NDK integration is deprecated in the current plugin. Consider trying the new experimental plugin
���� Project ��Ŀ¼�� gradle.properties �ļ�����ӣ�
    android.useDeprecatedNdk=true

ע : ʹ�� NDK r17 ʱ������ Android Studio �����������ʾ��
    A problem occurred starting process ��command 
    ��/Users/xxx/Library/Android/sdk/ndk-bundle/toolchains/mips64el-linux-android-4.9/prebuilt
    /darwin-x86_64/bin/mips64el-linux-android-strip��
   ϵͳ�Ҳ���ָ�����ļ�, ������Ϊ NDK r17 ֮����֧�� mips ƽ̨���� build.gradle �������������ÿɽ��
    android {
        defaultConfig {
            .....
        }
        packagingOptions { 
            doNotStrip '*/mips/*.so' 
            doNotStrip '*/mips64/*.so' 
        }
    }

˵������û�� res/drawable-xxxx/jpush_notification_icon �����ԴĬ��ʹ��Ӧ��ͼ����Ϊ֪ͨ icon��
�� 5.0 ����ϵͳ��Ӧ��ͼ����Ϊ statusbar icon ������ʾ���������û��ɶ���û����Ӱ�ͽ���ɫ�� icon �滻����ļ����ļ�����Ҫ�䡣


/**
 * Application�г�ʼ����������
 */
JPushInterface.setDebugMode(isDebugMode);//���õ���ģʽ,�� init �ӿ�֮ǰ����
JPushInterface.init(this);//��ʼ��
//stopPush��JPush ���ͷ�����ȫ��ֹͣ���������Ϊ��
//�ղ���������Ϣ
//�����������е����� API ���ö���Ч������ͨ�� JPushInterface.init �ָ�����Ҫ���� resumePush �ָ���
JPushInterface.stopPush(MyApplication.instance);
//JPushInterface.setAlias(this, 0, "");//Ϲ����һ������, �����ǽ��ղ�����Ϣ(����""����û����? �´����ø����ӵ��ַ���)


//Activity��
JPushInterface.resumePush(MyApplication.instance);//�����˴� API �󣬼���������ȫ�ָ���������
JPushInterface.setAlias(this, 0, SPUtils.getString(Global.username));//���ñ���, �����������ͷ�ʽ
checkNotificationPermission();
}
//����Ƿ�����ʾNotification
private AlertDialog alertDialog;
private void checkNotificationPermission() {
	if (!NotificationHelper.areNotificationsEnabled(this)) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(this)
					.setTitle("û��֪ͨ��Ȩ��")
					.setMessage("û��֪ͨ��Ȩ��, �����յ���Ϣ֪ͨ, ���֪ͨȨ��.")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							NotificationHelper.gotoSet(activity);
						}
					}).setNegativeButton("ȡ��", null)
					.setCancelable(false)
					.create();
		}
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
	}
}
    /**
     * ��д���ؼ�,flagCount��ȫ�ֱ���
     * private int flagCount = 0;
     */
    @Override
    public void onBackPressed() {
        if (tabLayout.getSelectedTabPosition() != 0) {
            switchToPosition(0);
            return;
        }
        if (System.currentTimeMillis() - flagClicked <= 1000) {
            //�����˱� API ��JPush ���ͷ�����ȫ��ֹͣ���������Ϊ��
            //�ղ���������Ϣ
            //�����������е����� API ���ö���Ч������ͨ�� JPushInterface.init �ָ�����Ҫ���� resumePush �ָ���
            JPushInterface.stopPush(MyApplication.instance);
//            JPushInterface.setAlias(this, 0, "");
            super.onBackPressed();
        } else {
            flagClicked = System.currentTimeMillis();
            toast("�ٵ��һ���˳�");
        }
    }


��־����:
jpush��


