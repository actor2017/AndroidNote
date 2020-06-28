#Activity界面跳转、数据传递和生命周期

#四大组件
* activity		：界面，用户可以看见，直接操作的页面
* BroadcastReceiver	：广播接收者
* service		：服务，简单的理解为一个没有界面的activity
* ContentProvider	：内容提供者，把应用程序的私有数据暴露出去

#Activity的创建和跳转
-----
### 1 AndroidManifest清单文件详解
* Application标签的label表示应用程序列表里的名称
* activity标签的label表示应用桌面图标的名称
*  创建应用的桌面图标

	   		<!-- 应用程序的入口
            	 创建应用的桌面图标
             -->
            <intent-filter>	//意图过滤器,第二页面可以不写,但是就看不见第二页面
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

* category：类别 LAUNCHER APP_MAPS MUSIC HOME  DEFULT	//种类,分类,好像写在<intent-filter>里面?

* 一个应用程序可以有多个桌面图标
	* 常见的应用：通讯录和拨号器
//---------------------------------------------------------------------------
### 2 意图设置动作激活一个新的界面
	Java代码:
	Button btn2 = (Button) findViewById(R.id.btn2);
	//设置监听
	btn2.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//1.获取意图
			Intent intent = new Intent();
			//2.设置动作,没有用系统动作,随意写
			intent.setAction("secondintent");
			//3.设置数据,可写可不写,随意写,但是如果写了,配置文件里也要必须写scheme(计划)
			intent.setData(Uri.parse("suibian://asdfsdfasdfasdfasdfsadfasdf"));
			//4.开启activity页面
			startActivity(intent);
		}
	});

	清单文件:AndroidManifest.xml
        <!-- 重写activity,重写name,lable(app的桌面名字),重写intent-filter意图过滤器 -->

        <activity
            android:name="com.heima.intents.SecondActivity"//★★★★★注意:包不要写错了,老师调试了很久★★★★★
            android:label="day10_03_意图设置动作激活界面2" >
            <intent-filter>
              			<!-- 这儿就是设置的意图的动作 -->
                <action android:name="secondintent" />
				<!-- 这儿必须重写成DEFAULT -->
                <category android:name="android.intent.category.DEFAULT" />
		<!-- Java代码中写了,这里就必须写scheme:计划 -->
                <data android:scheme="suibian"/>
            </intent-filter>
        </activity>
//---------------------------------------------------------------------------------
### 3 隐式意图和显式意图（重点）

intent主要包括隐式意图和显式意图。显式意图通常主要是启动本应用中的Activity之间的数据，而隐式意图则常见于启动系统中的某些特定的动作，比如打电话，发短信，或者是跨应用的Activity启动（如在QQ点击链接地址启动一个浏览器Activity）。

显式意图：
调用Intent.setComponent()、Intent.setClass()、Intent.setClassName()方法明确指定了组件名的Intent为显式意图，显式意图明确指定了Intent应该传递给哪个组件。

隐式意图：
没有明确指定组件名的Intent为隐式意图。 Android系统会根据隐式意图中设置的动作(action)、类别(category)、数据（URI和数据类型）找到最合适的组件来处理这个意图。
//------------------------------------------------------------------
* 隐式意图
	* 原理：解析xml文件,通过反射创建要开启的activity的对象
	* 缺点：代码书写复杂，效率低
	* 优点：即可以开启自己应用也可以开启其他应用中的activity组件

* 显示意图
	* 原理：直接指定类的字节码，通过反射创建activity的对象
	* 编写步骤

			// 1. 指定类的字节码
			Intent intent = new Intent(this, ThirdActivity.class);
			// 2. 开启activity
			startActivity(intent);

	优点：代码书写简单，效率高
	缺点：只能开启自己应用中的activity界面
//-------------------------------------------------------------------
### 4 设计意图的目的
* 组件之间解耦
* 作用：开启组件，传递数据

#Activity跳转时传递数据
//----------------------------------------------------------★★★★---------
### 5 意图传递数据（重点）
* Java中八大基本类型及其数组
* Serializable	：把内存中的数据序列化到磁盘上
* Parcelable	：把内存中的数据序列化到公共内存
* Bundle	：数据捆
* Intent	：意图

//-------------------------------------------------------------------
### 6 URI介绍
* URI：统一资源标识符 renyi://192.168.13.66:8080/image/logo.png
* URL：统一资源定位符 http://www.baidu.com:80/image/index.html

* 组成
	* scheme	：约束
	* host		：主机
	* port		：端口号
	* path		：路径

#Activity销毁时传递数据
//-------------------------------------------------------------------
### 7 开启activity获取返回值（重点）
* 开启目标activity，等待目标activity关闭，返回数据

1. 用一种特殊的方式开启目标activity
	startActivityForResult(intent, 1);

2. 设置要返回的数据

		String phoneNum = data[position];
		Intent intent = new Intent();
		intent.putExtra("num", phoneNum);
		setResult(2, intent);

3.关闭目标activity

		finish();

4. 接受返回的数据
	
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			String phoneNum = data.getStringExtra("num");
			etPhoneNum.setText(phoneNum);
		}
//-------------------------------------------------------------------
### 8 请求码和结果码的作用（重点）
* 请求码：开启多个activity时，区分数据是从那个页面返回的
* 结果码：开启多个activity时，区分数据是从那个页面返回的

#Activity生命周期
//-------------------------------------------------------------------
### 9 activity的生命周期（重点）
* 被出生()-->哭()-->幼年()-->少年()-->中年-->老年()-->翘辫子

* 打开activity：onCreate()-->onStart()-->onResume()
* 关闭activity：onPause()-->onStop()-->onDestroy()
* 最小化：onPause()-->onStop()
* 最大化：onRestart()-->onStart()-->onResume()
//-------------------------------------------------------------------
### 10 读文档查看activity的生命周期的分类

1. 整个生命周期
	onCreate()-->onStart()-->onResume()-->onPause()-->onStop()-->onDestroy()

2. 可视生命周期
		     onStart()-->onResume()-->onPause()-->onStop()

3. 前台生命周期
				 onResume()-->onPause()

//-------------------------------------------------------------------
### 11 横竖屏切换activity的生命周期
* 先销毁activity，重新创建一个新的activity

1.* 固定activity的屏幕朝向
<activity
            android:screenOrientation="sensor"//自适应
            android:screenOrientation="landscape"//横屏
            android:screenOrientation="portrait"//竖屏,默认

2.* 使activity不敏感屏幕朝向的变化(不会销毁再创建activity)(现在一般都用这种)
	*  android:configChanges="orientation|keyboard|screenSize"

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
	* 开启目标activity，系统创建一个activity的对象，使其位于应用任务栈的栈顶，点击返回键，移除任务栈栈顶的activity

	* 应用场景：默认的，可以不写

* singleTop:单一顶部模式
	* 开启目标activity，系统回去任务栈的栈顶查找有没有这个activity，如果有，就复用栈顶的activity；如果没有，则在栈顶创建一个新的activity实例对象。
	
	* 常见的应用：浏览器的书签

* singleTask：单一任务模式
	* 开启目标activity，系统回去整个任务栈查找，如果有，则移除这个activity上面的所有activity实例对象，使其位于任务栈的栈顶；如果没有则在栈顶创建这个activity的实例对象。
	* 常见应用：系统浏览器

* singleInstance：单一实例模式
	* 在整个手机中只用一个实例
	* 开启目标activity，系统会为这个activity单独创建一个任务栈，整个手机中只有一个这个的实例对象。点击返回键，先清空一个任务栈，然后在清空另外一个任务栈
	
	* 常见的应用：手机的来电界面



