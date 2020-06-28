service:服务
服务不能自己运行,需要通过Contex.startService()启动服务:
startService(intent);//保证服务长期运行在后台,
stopService(intent);

Service中方法:
void startForeground(int id, Notification notification);//开启前台进程
void stopForeground(boolean removeNotification);//停止前台进程
...

理解为一个没有界面的activity(例:定义了播放音乐的方法,实现了播放音乐的接口(只暴露程序员想暴露的方法)
在后台默默地做一些事情,长期运行在后台.为什么要用服务？长期在后台做一些事情

* 编写步骤
	1. 写一个类继承系统的Services
	2. 在清单文件中注册

### 2 线程进程和应用程序之间的关系
* 启动应用程序，系统为其创建一个Linux进程，在里面运行一个主线程，默认情况下，应用程序里所有的组件都运行在这个进程的主线程中。
* 进程		：主要用来运行davlik虚拟机，四大组件都运行在虚拟机中
* 应用程序	：包含有四大组件中的一个或者多个组件
* 线程		：运行在进程中，如果进程结束了，线程也就结束了

### 3 进程的生命周期及其优先级
1. 前台进程：Foreground process														
	* 应用程序可见，应用可以操作
	* activity执行了onResume
	
2. 可视进程：Visible process
	* 应用可见，但是应用不可以操作
	* activity执行了onPause

3. 服务进程：Service process

4. 后台进程：Background process
	* 应用不可见，不可操作
	* activity执行了onStop

5. 空进程：Empty process
	* 应用程序中没有任何活动的组件


#服务的启动停止及生命周期
-----
### 4 start开启服务的生命周期(重点)
* 完整生命周期：onCreate()-->onStartCommand(替换onStart)-->onDestroy()
* 开启服务：onCreate()-->onStartCommand()
* 停止服务：onDestroy()

void onCreate();//初始化.多次start或bind的时候,不会多次调用onCreate(不会开启多个)
//void onStart(Intent intent, int startId);//过时,用下面代替
int onStartCommand(Intent intent, int flags, int startId);//每次start时候都调用onStartCommand
IBinder onBind(Intent intent);	//bind专属
void onRebind(Intent intent);	//bind专属
boolean onUnbind(Intent intent);//bind专属
void onConfigurationChanged(Configuration newConfig);
void onTaskRemoved(Intent rootIntent);
void onTrimMemory(int level);
void onLowMemory();
void onDestroy();

3.其余方法
stopSelf();//停止自己

* 特点：
	* 服务可以被多次开启，每次开启都调用onStartCommand()★★★★★★
	* 服务只能被停止一次，多次停止无效
	* 长期运行在后台
