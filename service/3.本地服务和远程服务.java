### 6 本地服务和远程服务
* IPC：Inter Process Communication,进程间通信(Android体系结构-->Binder(IPC) Driver)
* AIDL：Android Interface Definition Language,安卓接口定义语言

* 本地服务：服务组件在自己应用程序中
* 远程服务：服务组件不不不在自己应用程序中(另外的应用程序)

* 远程开启|绑定远程Service,和开启|绑定本地Service一样.用的隐式意图
* 开启|绑定远程Service不需要.aidl,调用远程服务里的方法需要.aidl

### 7 本地应用调用远程服务中的方法（重点）

* 远程服务应用
	1. 把接口改成.aidl
	2. 去掉这个接口中的权限修饰符public
	3. 会在gen/package/目录下生成一个接口IRemoteService.java
	4. 让远程服务里的内部类继承上面接口的Stub类

* 本地应用
	4. 创建一个和远程应用包名一样的包
	5. 把远程服务的.aidl文件复制到这个包里
	6. 强转成远程服务应用中的接口类型 Stub.asInterface(service)


*要创建本地应用需要知道远程服务3个:
	1. action
	2. 包名
	3. aidl文件名

### 8 远程服务的使用场景
* 阅读系统源码		,很多远程服务(wifi服务,闹钟服务)
* 手机厂商			,手机厂商自己添加指纹解锁,人脸识别 等服务
* 超级大的公司写出来给其他程序员使用	支付宝

//系统源码,手机一开机就开启很服务,获取某个服务的时候,调用getSystemService(String name)
//系统通过远程服务把需要的服务返回
ContextImpl extends Context{
	
	//获取WIFI_SERVICE-->
	Context.getSystemService(String name){
		return getWifiManager();
	}
	
	private WifiManager getWifiManager(){
		synchronized (sSync){
			if(sWifiManager == null){
				IBinder b = ServiceManager.getService(WIFI_SERVICE);
				IWifiManager service = IWifiManager.Stub.asInterface(b);
				sWifiManager = new WifiManager(service, mMainThread.getHandler());
				return sWifiManager;
			}
		}
	}
	
	//闹钟
	private AlarmManager getAlarmManager(){
		...
	}
	
	//TelephoneManager,...
}