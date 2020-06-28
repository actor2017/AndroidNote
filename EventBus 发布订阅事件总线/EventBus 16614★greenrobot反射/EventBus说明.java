https://github.com/greenrobot/EventBus
https://www.jianshu.com/p/c4d106419146

1.ԭ��:
HashMap<Class,Method> map = new HashMap<>();
eventbus �ڲ�ά��һ��map�����Ƿ��ͺͽ�����Ϣ��class����ֵ�ǽ�����Ϣ�ķ�������Method,������register�������ͻ�ɨ�赱ǰ�࣬ �ҵ���Ҫ������Ϣ�ķ������ѽ�����Ϣ������Ϊֵ  ���յ���Ϣclass��Ϊ��  ������map�����У�
������һ���෢����Ϣ��ʱ�򣬿��Ը��ݷ�����Ϣ��class��map�����ҵ���Ҫ���ո���Ϣ�ķ���method��Ȼ��ͨ��������ø÷������ɡ�

greenrobot��˾��Ʒ,����һ����Ʒ:greenDao


ΪʲôĬ�ϲ���ǿ��ʹ�÷��䣬��Ϊ�÷�������ȡ��ͷ�������Ϣ�ȽϺ����ܣ�EventBus ����һ�� APT �Զ����ɴ�����������Ŀ�д��� @Subscribe ע��ĵط����浽 MyEventBusIndex �У��������ǾͲ���Ҫͨ�������ȡ�ˣ������ǿ��Լ��������ִ��Ч��

�ұ�ע�����ͷ��������������ַ�ʽ��һ����ͨ���Զ����ɵ������� MyEventBusIndex���� APT �Զ����ɵģ���ȡ����ע��ķ��������ַ�ʽ�Ƚϸ�Ч����һ����ֱ��ͨ������ֱ�ӻ�ȡ����������Ч�ʻ�Ƚϵ��£����� EventBus Ĭ�ϻ���õ�һ�ַ�ʽ�����û���ҵ� MyEventBusIndex �࣬��ʹ�÷���ķ�ʽ��ȡ

2.�������:
implementation 'org.greenrobot:eventbus:3.1.1'
implementation 'org.greenrobot:eventbus:3.2.0'//������androidx

3.ע��˵��
public @interface Subscribe {
	
	/**
	 * ThreadMode.POSTING: Ĭ�Ͻ��÷�������Ϣ���ͷ���ͬһ���߳���ִ��
	 * ThreadMode.MAIN����ʾ����UI�߳���ִ��
	 * ThreadMode.MAIN_ORDERED��UI�߳�, ��MAIN����: �¼���ʼ���Ŷӵȴ���������ȷ��post�����Ƿ������ġ�
	 * ThreadMode.BACKGROUND������ǰ�̷߳�UI�߳����ڵ�ǰ�߳���ִ�У���������̨������У�ʹ���̳߳ص���
	 * ThreadMode.ASYNC�������̨������У�ʹ���̳߳ص���. �������¼���Method�Ǻ�ʱ�ģ���Ҫʹ�ô�ģʽ����������ͬʱ���������ĺ�ʱ�ϳ����첽������EventBusʹ���̳߳ظ�Ч�ĸ����Ѿ�����첽�������̡߳�
	 *
	 * �����¶����̣߳�����ִ�к�ʱ�����������������
	 */
    ThreadMode threadMode() default ThreadMode.POSTING;

    /**
	 * ���Ϊ�棬�򴫵������ճ���¼�(��{@link EventBus#postSticky(Object)}���˶��ķ���(����¼�����)��
     * If true, delivers the most recent sticky event (posted with
     * {@link EventBus#postSticky(Object)}) to this subscriber (if event available).
     */
    boolean sticky() default false;

    /**
	 * ����������߶�ͬһ���¼����ж���ʱ,�����ȼ��ߣ�priority ���õ�ֵԽ��,���Ƚ����¼����д���
	 *
     * ���������ȼ�����Ӱ���¼�������˳��
     * ��ͬһ�������߳���({@link ThreadMode})�����ȼ����ߵĶ����߽���֮ǰ�����¼�
     * �������ȼ��ϵ͵ġ�Ĭ�����ȼ�Ϊ0��
	 * ע��:���ȼ���Ӱ��˳��ʹ�ò�ͬ��{@link ThreadMode}�Ķ�����֮��Ĵ���!
	 *
	 * Subscriber priority to influence the order of event delivery.
     * Within the same delivery thread ({@link ThreadMode}), higher priority subscribers will receive events before
     * others with a lower priority. The default priority is 0. Note: the priority does *NOT* affect the order of
     * delivery among subscribers with different {@link ThreadMode}s! */
    int priority() default 0;
}

4.����˵��
4.1.ע��, ��Activity��onCreate / Fragment��onViewCreated ��ע��EventBus(�ؼ���!=null��ע��)
EventBus.getDefault().register(this);//ע�ᣬ��ʵ���ǽ���ǰ�������ŵ�һ�������б�������

4.2.ע��, ��Activity��onDestroy / Fragment��onDestroyView��ע��EventBus(super.onDestroy֮ǰ)
EventBus.getDefault().unregister(this);//ע������ʵ���ǽ���ǰ�������Ӽ������Ƴ�

4.3.�����¼�
EventBus.getDefault().post(new ContactsUpdateBean(true));

4.4.����ճ���¼�(��2��ҳ�滹δ��, �򿪺�Ż���յ��¼�)//postSticky�Ỻ�����µ�event�¼������ܽ��շ��Ƿ�����������ʱ����������״̬  
EventBus.getDefault().postSticky(new EventBusStickyMessage("������ҳ�淢�͹�����ճ���¼�"));

4.5.�Ƴ��¼�
EventBus.getDefault().cancelEventDelivery(event);//�յ���Ϣ(event)��, ȡ���¼�, ���¼��������´�(�����ȼ����Ƚ��յ��¼�)

4.6.�Ƴ�ճ���¼�
boolean b = EventBus.getDefault().removeStickyEvent(event);//�Ƴ�ָ����ճ�Զ����¼�
Event event = EventBus.getDefault().removeStickyEvent(Event.class);
EventBus.getDefault().removeAllStickyEvents();				//�Ƴ�����ճ�Զ����¼�

8.��������
Event event = EventBus.getDefault().getStickyEvent(Event.class);//��ȡճ���¼�
boolean b = EventBus.getDefault().hasSubscriberForEvent(Event.class);//�Ƿ��Ѿ�����
boolean registered = EventBus.getDefault().isRegistered(this);//�Ƿ��Ѿ�ע��
EventBus.clearCaches();						//��� ���ķ��� & �¼�����
Logger logger = EventBus.getDefault().getLogger();
String s = EventBus.getDefault().toString();

9.�Զ�������
EventBus.builder()
        .addIndex()//�������, ��Ҫgradle��������, ���ٶ�
        .ignoreGeneratedIndex(false)//�Ƿ����APTע�������ɵ�MyEventBusIndex, ���true, ʹ�÷����ȡ����
        .throwSubscriberException(BuildConfig.DEBUG)//�Ƿ����쳣
        .installDefaultEventBus();

10.��ӻ���
##--------------------------EventBus-----------------------------
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}
##--------------------------EventBus-----------------------------