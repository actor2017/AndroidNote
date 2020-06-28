//---------------------------------------------生命周期---------------------
activity生命周期(面试题)
创建			可见	 获取焦点	  失去焦点    不可见		销毁
onCreate()-->onStart()-->onResume()-->onPause()-->onStop()-->onDestroy()
* 打开activity:		onCreate()-->onStart()-->onResume()
* 关闭activity:		onPause()-->onStop()-->onDestroy()
* 最小化(HOME):		onPause()-->onStop()
* 最大化(再次启动应用):	onRestart()-->onStart()-->onResume()
* 强制销毁回收内存:	onSaveInstanceState(Bundle outState)
注意：当Activity在后台时候，如果系统内存不够用，后台的Activity很有可能会被强制销毁回收内存的，
那么一定会触发onSaveInstanceState(Bundle outState) 方法，我们可以把想保存的数据保存在 outState对象里,
等Activity被重新创建执行onCreate(Bundle saveInstanceState) 从参数saveInstanceState再读取保存的数据

ActivityA 打开 ActivityB:
A：onPause() 
B：onCreate() 
B：onStart() 
B：onResume() 
A：onStop()

但是，如果ActivityB的样式是Dialog样式，B没有完全遮挡A，ActivityB的生命周期跟刚才一样，ActivityA并没有执行onStop().

从ActivityB 返回 ActivityA:
B：onPause() 
A：onRestart() 
A：onStart() 
A：onResume() 
B：onStop() 
B：onDestory()