1. һֱ scanning files to index
  ɾ��: C:\Users\my_user_name\.AndroidStudio3.3\system\caches

2.Default Activity not found
  1.Rebuild ��һ���Ƿ��ܱ���ɹ�.
  2.�鿴gradle�� applicationId �Ƿ��Ѿ�����(�ϵ��������м��ʷ���Ϊ��)
  3.AndroidManifest.xml ���Ƿ���Ĭ��Activity
    <activity
        android:name=".MainActivity"
        android:label="@string/app_name">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
  4.File => Invalidate Caches/Restart...
  5.ɾ��: C:\Users\my_user_name\.AndroidStudio3.3\system\caches
  6.�ɴ��ؽ�һ����Ŀ�ɣ�ֻ�����о����Ǻ����ŵ����ӡ�


3.�Ҽ����ܴ���Activity
  1.signingConfigs д�� buildTypes ֮ǰ
  2.signingConfigs �в����� debug ǩ������


5.Android studio����ɺڳ����ε����
1.����˵��insert��/shift+insert��
2.�����ּ���������������0��������0Ҳ��ins��
