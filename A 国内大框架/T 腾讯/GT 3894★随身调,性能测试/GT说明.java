https://github.com/Tencent/GT
https://github.com/Tencent/GT/tree/master/android
https://gt.qq.com/
https://gt.qq.com/docs/a/APIUserGuide4Android.pdf	//�ĵ�(���ĵ�������)

https://blog.csdn.net/huxp370/article/details/80655322	//ԭ�� GT���ܲ���Android��ʹ��˵��

github����Ŀ¼˵��:
GT\android\GT_APP: ����"GT"��������
GT\android\GT_SDK: sdkԴ��
GT\android\GTDemo: ����ʾ��




GT�����������APP���������ƽ̨������ֱ���������ֻ��ϵġ����ɵ��⻷����(IDTE, Integrated Debug Environment)������GT����ƾһ���ֻ����������ӵ��ԣ������ɶ�APP���п��ٵ����ܲ���(CPU���ڴ桢������������֡��/�����ȵȵ�)��������־�Ĳ鿴��Crash��־�鿴���������ݰ���ץȡ��APP�ڲ������ĵ��ԡ���������ʱͳ�Ƶȡ����������GT�ṩ�Ĺ��ܻ���������������Ҫ��������������GT�ṩ�Ļ���API���п��������⹦�ܵ�GT�����������������Ӹ��ӵ�APP�������⡣

GT֧��iOS��Android�����ֻ�ƽ̨�����У�
iOS����һ��Framework��������Ƕ��APP���̣��������GT��APP����ʹ�ã�iPhone��iPadӦ�ö���֧�֡�
Android����һ����ֱ�Ӱ�װ��GT����̨APP��GT SDK��ɣ�GT����̨���Զ�����װʹ�ã�SDK��Ƕ�뱻�����Ӧ�á�������GT����̨������Ϣչʾ�Ͳ����޸ġ�


һ��GT3.1�����2.x�汾�ı仯
2.x�İ汾��������ֵ��Ҫroot�ֻ�����һ����6.x֮����ֻ���Խ��Խ�ѡ�3.1�������ڱ���Ӧ����Ƕsdk�ķ�ʽ����ȡ�����ȣ����3.1�汾��ʹ�ñ��뼯��SDK������֧�ֶ���ʹ�� ��

GT3.1������hook���������Ի�ȡ���ḻ��Ӧ����Ϣ������ҳ������ٶȣ����ٴ������ջ��IOʹ������ȵȣ�

3.1ֱ���Բ��Ա������ʽͼ��չʾ�����������ݣ�

�����˿��ٴ������ջ��ҳ������ٶȡ�ҳ�沼����Ⱦ�ٶȡ�IOʹ����������߳�CPUʱ��Ƭͳ�Ƶȶ��γ�ȵ��������ݣ�


1.�������
//https://github.com/Tencent/GT/tree/master
implementation 'com.tencent.wstt.gt:gt-sdk:3.1.0'

2.ע������
2.1.�����sdk����so�⣬Ŀǰ֧�ֵ�abi��armeabi, armeabi-v7a, arm64-v8a, ��x86�����������Ա���so�⣬������������������so�⣬��ע��ƥ����Ӧ��so��·����
    defaultConfig {
        ndk{
			//gt-sdkĿǰ֧�ֵ�abi��armeabi, armeabi-v7a, arm64-v8a, ��x86
            abiFilters "armeabi", "armeabi-v7a", "x86","arm64-v8a"
        }
    }
	
2.2.GTĿǰ���֧��Android 5.0(API level 21)��

3."��΢��" ��Ŀʾ��
    1. Application �� onCreate ������
        GT.connect(this, new AbsGTParaLoader() {
            @Override
            public void loadInParas(InParaManager im) {
                //ע���������, ����GT����̨����ʾ
                // ������Σ�����������д�������Ĭ��ֵ����ѡֵ
                im.register("pkPlan", "PKPL","plan2","originalPlan", "plan1");
                im.register("��ʱʱ��", "RTO", "5", "2", "1","3");
                im.register("segmentSize", "SS", "2048", "1024");

                //����Ĭ����ʾ��GT��������3���������
                // ����ʱĬ�Ϸŵ�GT����̨��������չʾ����Σ��糬��������ֻ��ʾǰ����
                im.defaultInParasInAC("pkPlan", "��ʱʱ��");
            }

            @Override
            public void loadOutParas(OutParaManager om) {
                //ע���������, ����GT����̨����ʾ
                // ������Σ�����������д��
                om.register("NetType", "NTPE");
                om.register("NetSpeed", "NSPD");
                om.register("SendFileSize", "SFS");
                om.register("���ͳɹ���", "SSR");
                om.register("���ճɹ���", "RSR");

                // ����ʱĬ�Ϸŵ�GT����̨��������չʾ�ĳ��Σ��糬��������ֻ��ʾǰ����
                om.defaultOutParasInAC("���ͳɹ���", "NetType", "SendFileSize");
            }
        });
	2.MainActivity �� onDestroy ������
	GT.disconnect(this);


�������ԺͲ鿴���棺
�� �������̣�
��װGT����

��GT������ѡȡ����Ӧ��(��������GT SDK�� �����ȡ������������)�����������ʼ������ʼ�Ա���Ӧ�õĲ��ԣ���ʱ������GT�д����������鿴����Ӧ��ʵʱ���ݣ�

������ɺ���GT�����е�����������ݡ���Ȼ��ѡȡ������ʽ(��3.1��ʼ������ѡ�񵼳���΢�Ż��Ǳ���Ŀ¼/sdcard/GTRData)��Ȼ��ѡ��Ҫ���������ݣ�ѡ��������ݻᱣ�浽/sdcard/GTRData����΢���С�

���������ļ�data.js���Ƶ�GT/GT_Report/dataĿ¼�£��滻ԭ�е�data.js����

˫����GT/GT_Report/result.html�����ɲ鿴����

�� ע�����
�����ڼ䲻Ҫɱ������Ӧ�ã���������Ӧ���൱�ڿ�ʼ�µĲ��ԣ�

ÿ�ε�������������5�룻

�����ܱ���Ӧ������Ӧ�õ����й��ܺ�ҳ�棻


����������
������Ӧ����Ҫ�������뽫SDK����gtrsdkģ����proguard-rules.pro�ļ��е�������ݿ�����������ʹ�õ�proguard�ļ��С�












