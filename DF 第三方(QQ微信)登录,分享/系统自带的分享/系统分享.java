https://www.jianshu.com/p/a950f5596a01
https://www.jianshu.com/p/ea33092b6354

//调用系统的intent，打开的其实是框架层中的ChooserActivity
//优点：调用简单
//缺点：
//1、样式改不了  2、没有安装的应用程序显示不了
//3.调用系统的分享功能可以不用申请API集成，比较方便，但是不好的地方就是没有回调可以知道是否分享了，分享是否成功了。

final String TEXT = "text/plain";
final String IMAGE = "image/*";
final String IMAGE = "image/jpeg";//图片/jpeg类型
final String AUDIO = "audio/*";
final String VIDEO = "video/*";
final String File = "*/*";


private void share() {
	Intent sendIntent = new Intent();
	sendIntent.setAction(Intent.ACTION_SEND);
	sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);//分享多个

	//分享文字
	sendIntent.setType("text/plain");//设置分享内容的类型
	sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");

	//分享图片
	sendIntent.setType("image/jpeg");//image/*
	sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/sdcard/beauty1.jpg")));
	
	//分享多图片(ACTION_SEND_MULTIPLE)
	sendIntent.setType("image/png");
	sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

    //分享文件
    sendIntent.setType("*/*");
	sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
	
	
	//开始分享
	startActivity(Intent.createChooser(sendIntent, "请选择需要分享的应用程序"));//参2: 分享Dialog标题
}

//★★★兼容高版本:★★★
private void shareFile(String filePath) {
	/**
	 * 参1: 分享出去的文字, 如果是分享文件, 这个参数填了也没用
	 * 参2: 文件路径
	 * 参3: FLAG_ACTIVITY_NEW_TASK, 如果true: 先跳到对应app, 然后再弹出分享界面
	 */
	Intent sendIntent = IntentUtils.getShareImageIntent("", filePath, false);
	sendIntent.setType("*/*");
//    startActivity(sendIntent);//标题会显示 "分享方式"
    startActivity(Intent.createChooser(sendIntent, "请选择需要分享的应用程序"));
}

清单文件:(注:不知道还需不需要短信权限等,因为是模拟器)
<!-- 蓝牙分享所需的权限 -->
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />


//通过以下方法获得手机上全部可用于分享的app的包名和Activity名称
Intent share = new Intent(android.content.Intent.ACTION_SEND);
PackageManager packageManager = getPackageManager();
List<ResolveInfo> list = packageManager.queryIntentActivities(share, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
for(ResolveInfo info :list){
	MyUtils.log(""+info.activityInfo.packageName+"---"+info.activityInfo.name);
}

//android系统分享分析Intent.createChooser https://www.jianshu.com/p/9fd5363a9356


//指定目标app分享。以QQ和微信举例: https://blog.csdn.net/qq_27570955/article/details/52685456
sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
//sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ好友或QQ群
//sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片

private void share2Wechat() {
	Intent sendIntent = IntentUtils.getShareImageIntent("", filePath, false);
	sendIntent.setType("*/*");
	sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
	try {
		startActivity(sendIntent);
	} catch (Exception e) {
		e.printStackTrace();
		ToastUtils.show("未安装微信");
	}
}

private void share2QQ() {
	Intent sendIntent = IntentUtils.getShareImageIntent("", filePath, false);
	sendIntent.setType("*/*");
	sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
	try {
		startActivity(sendIntent);
	} catch (Exception e) {
		e.printStackTrace();
		ToastUtils.show("未安装QQ");
	}
}
