1.���ٷ��ĵ�
https://developer.android.google.cn/guide/topics/ui/accessibility/services.html
http://www.jianshu.com/p/959217070c87
http://www.cnblogs.com/itchq/articles/5648657.html
https://github.com/lendylongli/qianghongbao

2.���ݹٷ��ĵ�,дһ����,ʵ������ʵ�ֵķ���,��:AppLockService�̳�AccessibilityService
ע:������ֶ����ɵ�Service,Ҫ���嵥�ļ����޸�ע��:
        <service---------------------------------��Щ�Զ����ɵĲ���Ҫ,Ӧ�øĳ�3.��������ݡ�����
            android:name=".service.AppLockService"
            android:enabled="true"
            android:exported="true">
        </service>

3.���嵥�ļ�AndroidManifest.xml�����÷���
    <service android:name=".MyAccessibilityService" //�Լ��ķ�����,��:.service.AppLockService
        android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
        android:label="@string/accessibility_service_label">//alt+�س�:�ֻ���ʿ12������
        <intent-filter>
            <action android:name="android.accessibilityservice.AccessibilityService" />
        </intent-filter>

        <!--��Android 4.0��ʼ,���԰���һ�� <meta-data>Ԫ��-->
		<!-- ͨ��xml�ļ���ɸ�������������ã�Ҳ������onServiceConnected�ж�̬���� -->
        <meta-data
            android:name="android.accessibilityservice"
            android:resource="@xml/accessibility_service_config" />
    </service>

4.��<project_dir>/res/xml/accessibility_service_config.xml�ﴴ����д����:
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeWindowStateChanged"//���ڷ����仯,Ĭ��ֵ:typeAllMask
    android:accessibilityFeedbackType="feedbackGeneric"	//ͨ�õķ�����ʽ,Ĭ��ֵ:feedbackSpoken
    android:accessibilityFlags="flagDefault"
    android:canRetrieveWindowContent="true"	//�ܷ��ȡ�����ڵ�����,����Ҫ��ɾ��
    android:description="@string/accessibility_service_description"//alt+�س�:�ֻ���ʿ������,������Ӧ�õ���˽
    android:notificationTimeout="100"	//֪ͨ��ʱ�䳬ʱ,��ʦ˵����,ɾ��
    android:packageNames="com.example.android.apis"//�����ĸ�Ӧ��,����(ɾ��)�Ļ�,��������Ӧ��,ɾ��
    android:settingsActivity="com.example.android.accessibility.ServiceSettingsActivity"//����ҳ��,�������Ҫ����ҳ��,��ʦ˵ɾ��
/>

�����ĵ���֪:
accessibilityEventTypes:���������¼�������,Ĭ��ֵ:"typeAllMask"
			�������͵���·�������:android:accessibilityEventTypes

accessibilityFeedbackType:�������ܷ���������,Ĭ��ֵ:feedbackSpoken
			�������͵���·�������:android:accessibilityFeedbackType

canRetrieveWindowContent:�ܷ��ȡ�����ڵ�����,Ĭ��ֵ:true,����Ҫ��ɾ��

description:����,��������������ܸ�ʲô,��ʾ���������ܵĽ��湩�û���


5. ����app, ������->���������п����ҵ�������������
<p>
�������ܷ���Ŀ����͹ر�,���������û�������->���������н���

