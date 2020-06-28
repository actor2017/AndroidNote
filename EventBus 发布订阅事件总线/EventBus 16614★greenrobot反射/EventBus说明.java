https://github.com/greenrobot/EventBus
https://www.jianshu.com/p/c4d106419146

1.原理:
HashMap<Class,Method> map = new HashMap<>();
eventbus 内部维护一个map，键是发送和接收消息的class对象，值是接收消息的方法对象Method,当调用register方法，就会扫描当前类， 找到需要接收消息的方法，把接收消息方法作为值  接收的消息class作为键  保存在map集合中，
当另外一个类发送消息的时候，可以根据发送消息的class从map可以找到需要接收该消息的方法method，然后通过反射调用该方法即可。

greenrobot公司出品,还有一个产品:greenDao


为什么默认不用强制使用反射，因为用反射来获取类和方法的信息比较耗性能，EventBus 出了一个 APT 自动生成代码插件，将项目中带有 @Subscribe 注解的地方保存到 MyEventBusIndex 中，这样我们就不需要通过反射获取了，这样是可以极大的提升执行效率

找被注解的类和方法，大致有两种方式，一种是通过自动生成的索引类 MyEventBusIndex（由 APT 自动生成的）获取到被注解的方法，这种方式比较高效，另一种是直接通过反射直接获取方法，这种效率会比较低下，所以 EventBus 默认会采用第一种方式，如果没有找到 MyEventBusIndex 类，则使用反射的方式获取

2.添加依赖:
implementation 'org.greenrobot:eventbus:3.1.1'
implementation 'org.greenrobot:eventbus:3.2.0'//已适配androidx

3.注解说明
public @interface Subscribe {
	
	/**
	 * ThreadMode.POSTING: 默认将该方法和消息发送方在同一个线程中执行
	 * ThreadMode.MAIN：表示会在UI线程中执行
	 * ThreadMode.MAIN_ORDERED：UI线程, 与MAIN区别: 事件将始终排队等待交付。这确保post调用是非阻塞的。
	 * ThreadMode.BACKGROUND：若当前线程非UI线程则在当前线程中执行，否则加入后台任务队列，使用线程池调用
	 * ThreadMode.ASYNC：加入后台任务队列，使用线程池调用. 当处理事件的Method是耗时的，需要使用此模式。尽量避免同时触发大量的耗时较长的异步操作，EventBus使用线程池高效的复用已经完成异步操作的线程。
	 *
	 * 开辟新独立线程，用来执行耗时操作，例如网络访问
	 */
    ThreadMode threadMode() default ThreadMode.POSTING;

    /**
	 * 如果为真，则传递最近的粘性事件(用{@link EventBus#postSticky(Object)}到此订阅方法(如果事件可用)。
     * If true, delivers the most recent sticky event (posted with
     * {@link EventBus#postSticky(Object)}) to this subscriber (if event available).
     */
    boolean sticky() default false;

    /**
	 * 当多个订阅者对同一种事件进行订阅时,则优先级高（priority 设置的值越大）,会先接收事件进行处理
	 *
     * 订阅者优先级，以影响事件交付的顺序。
     * 在同一个交付线程内({@link ThreadMode})，优先级更高的订阅者将在之前接收事件
     * 其他优先级较低的。默认优先级为0。
	 * 注意:优先级不影响顺序使用不同的{@link ThreadMode}的订阅者之间的传递!
	 *
	 * Subscriber priority to influence the order of event delivery.
     * Within the same delivery thread ({@link ThreadMode}), higher priority subscribers will receive events before
     * others with a lower priority. The default priority is 0. Note: the priority does *NOT* affect the order of
     * delivery among subscribers with different {@link ThreadMode}s! */
    int priority() default 0;
}

4.方法说明
4.1.注册, 在Activity的onCreate / Fragment的onViewCreated 中注册EventBus(控件都!=null后注册)
EventBus.getDefault().register(this);//注册，其实就是将当前这个对象放到一个集合中保存起来

4.2.注销, 在Activity的onDestroy / Fragment的onDestroyView中注销EventBus(super.onDestroy之前)
EventBus.getDefault().unregister(this);//注销，其实就是将当前这个对象从集合中移除

4.3.发布事件
EventBus.getDefault().post(new ContactsUpdateBean(true));

4.4.发送粘性事件(第2个页面还未打开, 打开后才会接收到事件)//postSticky会缓存最新的event事件，不管接收方是否消亡，载入时都会检测最新状态  
EventBus.getDefault().postSticky(new EventBusStickyMessage("我是主页面发送过来的粘性事件"));

4.5.移除事件
EventBus.getDefault().cancelEventDelivery(event);//收到消息(event)后, 取消事件, 让事件不再往下传(高优先级可先接收到事件)

4.6.移除粘性事件
boolean b = EventBus.getDefault().removeStickyEvent(event);//移除指定的粘性订阅事件
Event event = EventBus.getDefault().removeStickyEvent(Event.class);
EventBus.getDefault().removeAllStickyEvents();				//移除所有粘性订阅事件

8.其它方法
Event event = EventBus.getDefault().getStickyEvent(Event.class);//获取粘性事件
boolean b = EventBus.getDefault().hasSubscriberForEvent(Event.class);//是否已经订阅
boolean registered = EventBus.getDefault().isRegistered(this);//是否已经注册
EventBus.clearCaches();						//清空 订阅方法 & 事件类型
Logger logger = EventBus.getDefault().getLogger();
String s = EventBus.getDefault().toString();

9.自定义配置
EventBus.builder()
        .addIndex()//添加所以, 需要gradle额外配置, 见百度
        .ignoreGeneratedIndex(false)//是否忽略APT注解器生成的MyEventBusIndex, 如果true, 使用反射获取方法
        .throwSubscriberException(BuildConfig.DEBUG)//是否抛异常
        .installDefaultEventBus();

10.添加混淆
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