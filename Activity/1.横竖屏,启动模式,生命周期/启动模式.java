//------------------------------------------------------------------启动模式-------------

### 13 Activity的启动模式		//launchMode启动模式
* 作用：影响activity在任务栈中的顺序
例:<activity
            android:launchMode="singleInstance"

1.* standard：标准默认
	* 开启目标activity，系统创建一个activity的对象，使其位于应用任务栈的栈顶，
	  点击返回键，移除任务栈栈顶的activity

	* 应用场景：默认的，可以不写

2.* singleTop:单一顶部模式
	* 开启目标activity，系统会去任务栈的栈顶查找有没有这个activity，如果有，
	  就复用栈顶的activity；如果没有，则在栈顶创建一个新的activity实例对象。
	
	* 常见的应用：浏览器的书签
点击启动:onCreate-->onStart-->onResume,未退出页面再进这个页面-->onPause-->onNewIntent-->onResume

3.* singleTask：单一任务模式
	* 开启目标activity，系统会去整个任务栈查找，如果有，则移除这个activity上面
	  的所有activity实例对象，使其位于任务栈的栈顶；如果没有则在栈顶创建这个activity的实例对象。

	* 常见应用：系统浏览器

4.* singleInstance：单一实例模式
	* 在整个手机中只用一个实例
	* 开启目标activity，系统会为这个activity单独创建一个任务栈，整个手机中只有
	  一个这个的实例对象。点击返回键，先清空一个任务栈(这个单独/其它任务站所有activity)，然后在清空另外一个任务栈(其它所有activity/这个单独 任务站)
	
	* 常见的应用：手机的来电界面
点击启动:onCreate-->onStart-->onResume,未退出页面再进这个页面-->onPause-->onResume(智慧城管的直播测试界面)


★我们一般会把MainActivity设置为SingleTask,除了保证MainActivity的唯一，
还可以利用singleTask的特性做一些清理工作。自动管理栈，销毁无用的Acitivity.
