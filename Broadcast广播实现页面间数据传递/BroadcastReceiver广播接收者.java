BroadcastReceiver:�㲥������:���Բ����嵥�ļ���ע��,���඼Ҫ���嵥�ļ���ע��

* Androidϵͳ�㲥��ͨ���㲥Ⱥ��ϵͳ�Ĺ����¼�
* �㲥�����ߣ�BroadcastReceiver������ϵͳ�Ĺ����¼���

* ϵͳ�����Ĺ㲥�����ߣ�SD��Ρ����ŵ������Ⲧ�绰����������(BOOT_COMPLETED)��
  ����������Ӧ�ó���ж�ذ�װ,�����仯,NETWORK_CHANGED,����,ʱ��ı�ȹ㲥...

* �㲥������û���û�����,����������һ��activity��serice ����Ӧ�����յ�����Ϣ��
������NotificationManager��֪ͨ�û���(�������ơ��𶯡���������)�ȡ�
һ����˵����״̬���Ϸ�һ���־õ�ͼ�꣬�û����Դ�������ȡ��Ϣ��

<receiver android:enabled=["true" | "false"]
android:exported=["true" | "false"]//��broadcastReceiver�ܷ��������App�ķ����Ĺ㲥,�����intent-filter��Ĭ��ֵΪtrue������Ϊfalse
android:icon="drawable resource"
android:label="string resource"
android:name="com.google.receiver.xxxReceiver"
android:permission="string"//������ã�������ӦȨ�޵Ĺ㲥���ͷ����͵Ĺ㲥���ܱ���broadcastReceiver������
android:process="string" >//broadcastReceiver���������Ľ��̡�Ĭ��Ϊapp�Ľ��̡�����ָ�������Ľ��̣�Android�Ĵ�������������ͨ��������ָ���Լ��Ķ������̣�
. . .
</receiver>

ע�⣺
1.��������ֻ��ʮ������,�����onReceive()��������ʮ���ڵ����飬
  �ͻᱨANR(Application No Response) ��������Ӧ�Ĵ�����Ϣ��
  ������������Ϊ�ӻص�onReceive()������ʼ���÷������ؽ�������
  �����Ҫ���һ��ȽϺ�ʱ�Ĺ���,Ӧ��ͨ������Intent��Service,��Service�����.���ﲻ��ʹ�����߳������


9 �㲥�����ߵ��ص�Ͱ汾����   
* �ص㣺ֻҪ�㲥�����߰�װ���ֻ��ϣ�����Ӧ����û�������������Խ�����ע��Ĺ㲥
* �汾����죺ϵͳ�����㲥,����û�ǿ��ֹͣ�˽�����,�ڵͰ汾�Ͽ��Խ��ܹ㲥.�߰汾���޷����ܹ㲥��,����һ��Ӧ�ò������½���


11 ����㲥������㲥(�ص�)
����㲥:������ֻҪע����������������ܽ��ܵ��㲥�������߽��ܹ㲥û���Ⱥ�˳��
����㲥:�����߰������ȼ��Ӹߵ���һ��һ���ؽ��ܣ���������ĺ�ͷ�ļ�
	* ���ȼ� 1000 ~ -1000,<intent-filter android:priority="">��дĬ��0
	* �����ȼ��Ľ����߿���������ֹ�㲥:abortBroadcast();
	* �����ȼ��Ľ����߿����޸Ĺ㲥�е�����:setResultData("��ÿ����ũ������1000.00Ԫ");
	* ����ָ��һ�����յĹ㲥������:�����߿���ָ��,��Ҫ���嵥�ļ���ע��


	��ʵ���Ը��й㲥��priority���ó�int���ֵ2147483647,���Ǻ�����Ҫ���붯̬ע��һ��?
	IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");   
    filter.setPriority(2147483647);
    receiver=new BroadReceiver();
    registerReceiver(receiver,filter);
	

����:
	* ����㲥:������ֹ�㲥,�����޸Ĺ㲥�������
	* ����㲥:����ֹ�㲥,���޸Ĺ㲥�������,��ָ��һ�����յĹ㲥������


//�Զ���㲥
//1.Activity�з��͹㲥
Intent intent = new Intent();
intent.setAction("com.google.broadcast.EAT");// 2. ���ö���
intent.setData(Uri.parse("buy://��ƿ����"));//�������ݣ���д�ɲ�д
sendBroadcast(intent);// 3. �������
sendOrderedBroadcast(intent, receiverPermission);//��������㲥.receiverPermission:���͹㲥��Ȩ��(�Զ���Ȩ��<uses-permission)

//��ͨ�㲥����:
дһ���̳�BroadCastReceiver����,��дonReceive()����,�㲥������������ִ���������ʱ���ڻ�Ծ״̬��
��onReceive()���غ�����Ϊʧ��״̬,ע��:Ϊ�˱�֤�û��������̵�����,һЩ��ʱ�Ĳ���Ҫ�ŵ��߳���,
������SMSBroadcastReceiver.

public class JjjReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if ("com.google.broadcast.EAT".equals(intent.getAction())) {
			//intent.getData()��ȡ����:buy://��ƿ����
			System.out.println("���飬�һ����ˡ�����"+intent.getData().toString());
			//����������:���飬�һ����ˡ�����buy://��ƿ����
		}
	}
}

//�嵥�ļ�
<receiver android:name="com.google.broadcast.JjjReceiver">
    <intent-filter >
		<!-- 1.��д���action -->
		<action android:name="com.google.broadcast.EAT"/>
		
        <!-- 2.���㲥��ʱ��д�����ݣ������յ�ʱ��ͱ���д,������ղ��� -->
        <data android:scheme="buy"/>
	</intent-filter>
</receiver>

//==========================================================4.ϵͳ�㲥==========================
https://www.cnblogs.com/lwbqqyumidi/p/4168017.html

//==========================================================4.�첽�㲥==========================
Sticky Broadcast��ճ�Թ㲥(�� android 5.0/api 21��deprecated,�����Ƽ�ʹ�ã���Ӧ�Ļ���ճ������㲥��ͬ���Ѿ�deprecated)
//�첽�㲥
Context.sendStickyBroadcast(Intent myIntent);

//�÷�����������㲥������Ҳ���첽�㲥�����ԣ�
sendStickyOrderedBroadcast(intent, resultReceiver, scheduler,  initialCode, initialData, initialExtras);
�����첽�㲥Ҫ:<uses-permission android:name="android.permission.BROADCAST_STICKY" />Ȩ��

���ղ�������Intent�󣬹㲥��Ȼ���ڣ�ֱ�������removeStickyBroadcast(intent)��������ȥ��

//=====================================================5.�Զ���Ȩ�޹㲥==========================
https://blog.csdn.net/shineflowers/article/details/40426361    //<uses-permission


https://www.cnblogs.com/lwbqqyumidi/p/4168017.html
https://www.cnblogs.com/macroxu-1982/p/3648516.html
1 ���ȸ��ݹ㲥Ӧ���ڽ��պ�Ӧ������գ�����������й���
[1]  LocalBroadcastManager,Ӧ���ڹ㲥������
[2]  BroadcastManager  �㲥������(����Ӧ���ڣ�Ӧ����)