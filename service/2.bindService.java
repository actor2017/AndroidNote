#服务
服务不能自己运行,需要通过Contex.bindService()绑定服务:
bindService(Intent service, ServiceConnection conn, int flags);//conn连接通道:调用服务里的方法
unbindService(ServiceConnection conn);

#服务的生命周期
-----
void onCreate();//初始化.多次start或bind的时候,不会多次调用onCreate(不会开启多个)
//void onStart(Intent intent, int startId);//过时,用下面代替,start专属
int onStartCommand(Intent intent, int flags, int startId);//start专属
IBinder onBind(Intent intent);
void onRebind(Intent intent);
boolean onUnbind(Intent intent);
void onConfigurationChanged(Configuration newConfig);
void onTaskRemoved(Intent rootIntent);
void onTrimMemory(int level);
void onLowMemory();
void onDestroy();

### 2 bind绑定服务的生命周期（重点）
* 完整生命周期:onCreate()-->onBind()-->onUnbind()-->onDestroy()
* 绑定服务：onCreate()-->onBind();//如果服务已经启动再次调用不会再触发这2个方法
* 解绑服务：onUnbind()-->onDestroy()

* 特点
		1. 服务只能被绑定一次，多次绑定无效
		2. 服务只能被解绑一次，多次解绑抛出异常:连接通道无效
		3. 绑定service的activity和service是同生共死
		4. 绑定服务调用服务里的方法
		5. activity销毁前记得取消绑定

* 区别
	* start开启服务可以长期运行在后台
	* bind绑定服务可以调用服务里的方法

### 3 绑定服务调用服务里的方法
* 绑定服务，服务被绑定成功就返回服务里的内部类对象，activity中的连接通道获取服务里的内部类对象，间接调用服务里的方法

1. 绑定服务.启动的服务与调用者绑定,只要调用者关闭服务就终止
bindService(Intent, ServiceConnection, BIND_AUTO_CREATE);//ServiceConnection连接通道:调用服务里的方法
第3个参数flags:BIND_AUTO_CREATE,如果存在就复用
               BIND_DEBUG_UNBIND
			   BIND_NOT_FOREGROUND
			   BIND_ABOVE_CLIENT
			   BIND_ALLOW_OOM_MANAGEMENT
			   BIND_WAIVE_PRIORITY
  unbindService(ServiceConnection conn);

2. 服务被绑定成功onBind
		public IBinder onBind(Intent intent) {
				//服务被绑定成功后调用
				Log.e(TAG, "2. 服务被绑定成功onBind");
				Log.e(TAG, "3. 返回服务里的内部类对象");
				return new ChengMiShu();
		}

3. 返回服务里的内部类对象
		public class ChengMiShu extends Binder {
			
			public void qianShouMM(int money){
				if (money > 1000) {
					methodInService();
				}else {
					Toast.makeText(MyService.this, "没钱幻想找媳妇，回去写几年代码把", 0).show();	
				}
			}
		}

4. activity和service的连接通道,连接成功后调用

	//连接通道里的方法
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		Log.i(TAG, "4. activity和service的连接通道");
		Log.i(TAG, "5. 把IBinder强转成服务里的内部类");
		mService = (ChengMiShu)service;		//把IBinder强转成服务里的内部类
	}

6. 通过服务里的内部类对象间接调用服务里的方法
	mService.qianShouMM(100000);
