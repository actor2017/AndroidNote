service:����
�������Լ�����,��Ҫͨ��Contex.startService()��������:
startService(intent);//��֤�����������ں�̨,
stopService(intent);

Service�з���:
void startForeground(int id, Notification notification);//����ǰ̨����
void stopForeground(boolean removeNotification);//ֹͣǰ̨����
...

���Ϊһ��û�н����activity(��:�����˲������ֵķ���,ʵ���˲������ֵĽӿ�(ֻ��¶����Ա�뱩¶�ķ���)
�ں�̨ĬĬ����һЩ����,���������ں�̨.ΪʲôҪ�÷��񣿳����ں�̨��һЩ����

* ��д����
	1. дһ����̳�ϵͳ��Services
	2. ���嵥�ļ���ע��

### 2 �߳̽��̺�Ӧ�ó���֮��Ĺ�ϵ
* ����Ӧ�ó���ϵͳΪ�䴴��һ��Linux���̣�����������һ�����̣߳�Ĭ������£�Ӧ�ó��������е������������������̵����߳��С�
* ����		����Ҫ��������davlik��������Ĵ�������������������
* Ӧ�ó���	���������Ĵ�����е�һ�����߶�����
* �߳�		�������ڽ����У�������̽����ˣ��߳�Ҳ�ͽ�����

### 3 ���̵��������ڼ������ȼ�
1. ǰ̨���̣�Foreground process														
	* Ӧ�ó���ɼ���Ӧ�ÿ��Բ���
	* activityִ����onResume
	
2. ���ӽ��̣�Visible process
	* Ӧ�ÿɼ�������Ӧ�ò����Բ���
	* activityִ����onPause

3. ������̣�Service process

4. ��̨���̣�Background process
	* Ӧ�ò��ɼ������ɲ���
	* activityִ����onStop

5. �ս��̣�Empty process
	* Ӧ�ó�����û���κλ�����


#���������ֹͣ����������
-----
### 4 start�����������������(�ص�)
* �����������ڣ�onCreate()-->onStartCommand(�滻onStart)-->onDestroy()
* ��������onCreate()-->onStartCommand()
* ֹͣ����onDestroy()

void onCreate();//��ʼ��.���start��bind��ʱ��,�����ε���onCreate(���Ὺ�����)
//void onStart(Intent intent, int startId);//��ʱ,���������
int onStartCommand(Intent intent, int flags, int startId);//ÿ��startʱ�򶼵���onStartCommand
IBinder onBind(Intent intent);	//bindר��
void onRebind(Intent intent);	//bindר��
boolean onUnbind(Intent intent);//bindר��
void onConfigurationChanged(Configuration newConfig);
void onTaskRemoved(Intent rootIntent);
void onTrimMemory(int level);
void onLowMemory();
void onDestroy();

3.���෽��
stopSelf();//ֹͣ�Լ�

* �ص㣺
	* ������Ա���ο�����ÿ�ο���������onStartCommand()�������
	* ����ֻ�ܱ�ֹͣһ�Σ����ֹͣ��Ч
	* ���������ں�̨
