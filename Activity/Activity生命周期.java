### 9 activity的生命周期（重点）
* 被出生()-->哭()-->幼年()-->少年()-->中年-->老年()-->翘辫子

* 打开	     activity：onCreate()-->onStart()-->onResume()
* 关闭,返回键activity：onPause()-->onStop()-->onDestroy()
* 最小化,Home键	     ：onPause()-->onStop()
* 最大化,Home后再启动：onRestart()-->onStart()-->onResume()

其余方法:
public boolean onTouchEvent(MotionEvent event);//触摸事件

注意：当Activity在后台时候，如果系统内存不够用，后台的Activity很有可能会被强制销毁回收内存的，★★★
那么一定会触发onSaveInstanceState(Bundle outState) 方法,我们可以把想保存的数据保存在 outState对象里,
等Activity被重新创建执行onCreate(Bundle saveInstanceState) 从参数saveInstanceState再读取保存的数据

ActivityA 打开 ActivityB
A：onPause()	B：onCreate()	B：onStart()	B：onResume()	A：onStop()

* 但是，如果ActivityB的样式是 透明 or Dialog样式，B没有完全遮挡A，ActivityB的生命周期跟刚才一样，
但是ActivityA并没有执行onStop()

从ActivityB 返回 ActivityA接着上面的，如果在处于ActivityB 界面按下back键，返回 ActivityA，
生命周期如下：
B：onPause() 
A：onRestart() 
A：onStart() 
A：onResume() 
B：onStop() 
B：onDestory()

//-------------------------------------------------------------------
### 10 读文档查看activity的生命周期的分类

1. 整个生命周期	    1.可视后	2.获取焦点后  3.失去焦点  4.不可视  5.销毁后调用
	onCreate()-->onStart()-->onResume()-->onPause()-->onStop()-->onDestroy()

2. 可视生命周期			6.重启后调用
		     onStart()-->onResume()-->onPause()-->onStop()

3. 前台生命周期
				 onResume()-->onPause()

4.最大化  7.重新开始
	onRestart()-->onStart()-->onResume()
//-------------------------------------------------------------------
### 11 横竖屏切换activity的生命周期
* 先销毁activity，重新创建一个新的activity

#任务栈和Activity的启动模式
//-------------------------------------------------------------------
### 12 任务栈的概念	
* 任务：打开的activity界面
* 栈	  ：先进后出FILO
* 作用：管理打开的activity界面的，用于维护用户的体验
//-------------------------------------------------------------------
### 13 Activity的启动模式		//launchMode启动模式
* 作用：影响activity在任务栈中的顺序

* standard：标准默认
	* 开启目标activity,系统创建一个activity的对象,使其位于应用任务栈的栈顶,点击返回键,移除任务栈栈顶的activity

	* 应用场景：默认的，可以不写

* singleTop:单一顶部模式
	* 开启目标activity,系统会去任务栈的栈顶查找有没有这个activity,如果有,就复用栈顶的activity;如果没有,则在栈顶创建一个新的activity实例对象。
	
	* 常见的应用：浏览器的书签

* singleTask：单一任务模式
	* 如果当前待开启的Activity已经存在,无论在栈中任何位置,都会将此位置上面的Activity全部移除,然后复用该Activity.
	如果有,则移除这个activity上面的所有activity实例对象,使其位于任务栈的栈顶;如果没有则在栈顶创建这个activity的实例对象。
	* 常见应用：系统浏览器

* singleInstance：单一实例模式
	* 一个任务栈中只有一个Activity，并保证不再有其他,在整个手机中只有一个实例
	* 开启目标activity,系统会为这个activity单独创建一个任务栈,整个手机中只有一个这样的实例对象。点击返回键,先清空一个任务栈,然后在清空另外一个任务栈。
	
	* 常见的应用：手机来电界面

onNewIntent
当Activity不是Standard模式,并且被复用的时候,会触发onNewIntent(Intent intent)这个方法,一般用来获取新的Intent传递的数据.

我们一般会把MainAcitivy设置为SingleTask,除了保证MainActivity的唯一，还可以利用singleTask的特性做一些清理工作。自动管理栈，销毁无用的Acitivity.

Intent Flags
记住一点：Activity都是运行在任务栈里面，但如果要从广播接受者BordercastReceiver或者服务Service去启动一个Activity，必须为当前Activity创建一个新的任务栈才能正常显示
public class MyReceiver extends BroadcastReceiver{
   public void onReceive(Context context, Intent intent) {
      Intent intent=new Intent(context,DemoActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//创建新的任务栈
      context.startActivity(intent);
 }
//============================================================================
package com.itheima.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	/**
	 * activity被创建后调用的第一个方法
	 * 适合做一些初始化的事情
	 * 初始化控件、设置activity的布局文件
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.e(TAG, "onCreate");
	}

	/**
	 * activity可视后调用
	 */
	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "onStart");
	}
	/**
	 * activity重启后调用
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(TAG, "onRestart");
	}
	
	//触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
	
	/**
	 * activity获取焦点后调用
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume");
	}
	/**
	 * 失去焦点前调用
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause");
	}
	/**
	 * activity不可视前调用
	 */
	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop");
	}
	/**
	 * activity销毁前调用
	 * 适合做收尾的工作
	 * 保存数据
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
}
