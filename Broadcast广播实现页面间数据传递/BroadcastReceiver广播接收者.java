BroadcastReceiver:广播接收者:可以不在清单文件中注册,其余都要在清单文件中注册

* Android系统广播：通过广播群发系统的公共事件
* 广播接收者：BroadcastReceiver，接受系统的公共事件的

* 系统常见的广播接收者：SD插拔、短信到来、外拨电话、开机启动(BOOT_COMPLETED)、
  锁屏解锁、应用程序卸载安装,电量变化,NETWORK_CHANGED,蓝牙,时间改变等广播...

* 广播接收者没有用户界面,但可以启动一个activity或serice 来响应它们收到的信息，
或者用NotificationManager来通知用户。(闪动背灯、震动、播放声音)等。
一般来说是在状态栏上放一个持久的图标，用户可以打开它并获取消息。

<receiver android:enabled=["true" | "false"]
android:exported=["true" | "false"]//此broadcastReceiver能否接收其他App的发出的广播,如果有intent-filter，默认值为true，否则为false
android:icon="drawable resource"
android:label="string resource"
android:name="com.google.receiver.xxxReceiver"
android:permission="string"//如果设置，具有相应权限的广播发送方发送的广播才能被此broadcastReceiver所接收
android:process="string" >//broadcastReceiver运行所处的进程。默认为app的进程。可以指定独立的进程（Android四大基本组件都可以通过此属性指定自己的独立进程）
. . .
</receiver>

注意：
1.生命周期只有十秒左右,如果在onReceive()内做超过十秒内的事情，
  就会报ANR(Application No Response) 程序无响应的错误信息，
  它的生命周期为从回调onReceive()方法开始到该方法返回结果后结束
  如果需要完成一项比较耗时的工作,应该通过发送Intent给Service,由Service来完成.这里不能使用子线程来解决


9 广播接收者的特点和版本差异   
* 特点：只要广播接收者安装到手机上，不管应用有没有启动，都可以接受其注册的广播
* 版本库差异：系统发出广播,如果用户强行停止了接收者,在低版本上可以接受广播.高版本就无法接受广播了,重启一次应用才能重新接受


11 有序广播和无序广播(重点)
无序广播:接收者只要注册了这个动作，都能接受到广播，接收者接受广播没有先后顺序
有序广播:接收者按照优先级从高到低一级一级地接受，类似中央的红头文件
	* 优先级 1000 ~ -1000,<intent-filter android:priority="">不写默认0
	* 高优先级的接收者可以拦截终止广播:abortBroadcast();
	* 高优先级的接收者可以修改广播中的数据:setResultData("给每个码农补贴￥1000.00元");
	* 可以指定一个最终的广播接收者:发送者可以指定,不要在清单文件中注册


	其实可以给有广播的priority设置成int最大值2147483647,但是好像不行要代码动态注册一下?
	IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");   
    filter.setPriority(2147483647);
    receiver=new BroadReceiver();
    registerReceiver(receiver,filter);
	

区别:
	* 无序广播:不能终止广播,不能修改广播里的数据
	* 有序广播:能终止广播,能修改广播里的数据,能指定一个最终的广播接收者


//自定义广播
//1.Activity中发送广播
Intent intent = new Intent();
intent.setAction("com.google.broadcast.EAT");// 2. 设置动作
intent.setData(Uri.parse("buy://打瓶酱油"));//设置数据，可写可不写
sendBroadcast(intent);// 3. 激活组件
sendOrderedBroadcast(intent, receiverPermission);//发送有序广播.receiverPermission:发送广播的权限(自定义权限<uses-permission)

//普通广播接收:
写一个继承BroadCastReceiver的类,重写onReceive()方法,广播接收器仅在它执行这个方法时处于活跃状态。
当onReceive()返回后，它即为失活状态,注意:为了保证用户交互过程的流畅,一些费时的操作要放到线程里,
如类名SMSBroadcastReceiver.

public class JjjReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if ("com.google.broadcast.EAT".equals(intent.getAction())) {
			//intent.getData()获取数据:buy://打瓶酱油
			System.out.println("麻麻，我回来了。。。"+intent.getData().toString());
			//最后输出数据:麻麻，我回来了。。。buy://打瓶酱油
		}
	}
}

//清单文件
<receiver android:name="com.google.broadcast.JjjReceiver">
    <intent-filter >
		<!-- 1.可写多个action -->
		<action android:name="com.google.broadcast.EAT"/>
		
        <!-- 2.发广播的时候写了数据，这里收的时候就必须写,否则接收不到 -->
        <data android:scheme="buy"/>
	</intent-filter>
</receiver>

//==========================================================4.系统广播==========================
https://www.cnblogs.com/lwbqqyumidi/p/4168017.html

//==========================================================4.异步广播==========================
Sticky Broadcast：粘性广播(在 android 5.0/api 21中deprecated,不再推荐使用，相应的还有粘性有序广播，同样已经deprecated)
//异步广播
Context.sendStickyBroadcast(Intent myIntent);

//该方法具有有序广播的特性也有异步广播的特性；
sendStickyOrderedBroadcast(intent, resultReceiver, scheduler,  initialCode, initialData, initialExtras);
发送异步广播要:<uses-permission android:name="android.permission.BROADCAST_STICKY" />权限

接收并处理完Intent后，广播依然存在，直到你调用removeStickyBroadcast(intent)主动把它去掉

//=====================================================5.自定义权限广播==========================
https://blog.csdn.net/shineflowers/article/details/40426361    //<uses-permission


https://www.cnblogs.com/lwbqqyumidi/p/4168017.html
https://www.cnblogs.com/macroxu-1982/p/3648516.html
1 首先根据广播应用内接收和应用外接收，分两个类进行管理
[1]  LocalBroadcastManager,应用内广播管理类
[2]  BroadcastManager  广播管理类(部分应用内，应用外)