四大组件不能被New对象
    activity	:界面		用户可以看见,直接操作的页面
    Activity之间通过Intent进行通信。

1.注意: 0? <= requestCode < 65536 ,具体看源码
startActivityForResult(Intent intent, int requestCode);

2.跳转界面，并将栈中所有Activity清空,这2个一定要一起使用
Intent intent = new Intent(activity, MainActivity.class);
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);

3.这样也能清空并跳转
Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
context.startActivity(intent);

4.当然如果登录页的launchMode为singleTask的话，直接调用startActivity就行


//-------------------------------------------startActivity()意图传递数据类型-------------
### 5 意图传递数据类型（重点）
* Java中八大基本类型及其数组
* Serializable	：把内存中的数据序列化到磁盘上
* Parcelable	：把内存中的数据序列化到公共内存
* Bundle		：数据捆
* Intent		：意图
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("name", "张三");
intent.putExtra("age", 23);
startActivity(intent);//还可以传递Bundle

//-------------------------------------------------------startActivityForResult()--------
### 7 开启activity获取返回值（重点）
* 开启目标activity，等待目标activity关闭，返回数据

1. 用一种特殊的方式开启目标activity
	startActivityForResult(intent, 1);//第2个参数:请求码

2. 设置要返回的数据并关闭
		Intent intent = new Intent();
		intent.putExtra("num", 123);
		setResult(RESULT_OK, intent);//RESULT_OK:结果码
		finish();

3. 接受返回的数据
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			String phoneNum = data.getIntExtra("num");
			etPhoneNum.setText(phoneNum);
		}

//---------------------------------------------------------------------onNewIntent------------
onNewIntent
当Activity不是Standard模式，并且被复用的时候，会触发onNewIntent(Intent intent) 这个方法，
一般用来获取新的Intent传递的数据.
