1.���ϵͳ����
ANDROID_ADB
D:\AndroidStudioSDK\platform-tools

2.��ϵͳ������ӵ�Path��
%ANDROID_ADB%


adb help				//�鿴adb���������Ϣ
adb devices				//��ʾ��ǰ���е�ȫ���豸
adb install ����		//��װ
adb install xxx.apk		//ֱ�Ӱ�װ
adb shell				//�����shellģʽ
adb shell netcfg		//�鿴�豸IP��ַ(ǰ��:�豸�Ѿ���PC������usb����)
adb shell pm clear ����	//�������
adb uninstall ����		//ж��apk


//ж��ϵͳapp, �Ѳ��� https://www.toutiao.com/i6823525407623479811/
//vivo: ����-��������-Ӧ�ó���-ȫ��-�ɿ�����ж�ص�ϵͳapp, ж�غ�ע�ⱸ��, ��������û��ʲô����!
1.adb shell						//������� $ ,˵������ shell ģʽ�ɹ�
2.pm uninstall -k --user 0 ����	//-k: ж�غ���data & cacheDir
								//--user 0: Android ϵͳ֧�ֶ���û���Ĭ���û�ֻ��һ����id=0

//����ϵͳapp, �Ѳ���
adb shell pm path ����	//�������װ����APK·��: /system/app/ZKFramework/ZKFramework.apk
adb pull /system/app/ZKFramework/ZKFramework.apk F:\Users\actor\Desktop\	//����������


//��װϵͳapp, �Ѳ���
adb shell
adb push xxx.apk /system/app	//��app���������
adb push xxx.apk /system/priv-app//�е������Ŀ¼
pm list packages | grep com.ibimuyu.lockscreen

//δ����
pm disable -k --user 0 packageName ������APP��
pm enable -k --user 0 packageName ���ָ�APP��

//δ����
adb connect xxx.xxx.xxx.xxx:5555	//ͨ��wifi�����豸�����磺192.168.10.103:5555��
adb pull <remote> <local>		//��ȡģ�����е��ļ�
adb push <local> <remote>		//��ģ������д�ļ�

adb tcpip 5555				//�ֻ�����5555�ӿ�?
android					//����SDK���ĵ���ʵ�����ع�����
adb install -r D://xxx/Ӧ�ó���.apk	//��װӦ�ó���

cd data/app
  rm apk��
  exit

adb logcat -s ��ǩ��	//���������в鿴LOG��Ϣ

����Activity��
adb shell am start -n ����/������������-n ����,-a action,-d date,-m MIME-TYPE,-c category,-e ��չ����,�ȣ���

��ȡ�豸��ID�����кţ�
adb get-product 
adb get-serialno

�������ݿ�SQLite3
adb shell 
sqlite3

debugģʽ
adb logcat -s TAG:*


#cd system/sd/data //����ϵͳ��ָ���ļ��� 
#ls //�б���ʾ��ǰ�ļ������� 
#rm -r xxx //ɾ������Ϊxxx���ļ��м�������������ļ� 
#rm xxx //ɾ���ļ�xxx 
#rmdir xxx //ɾ��xxx���ļ���


