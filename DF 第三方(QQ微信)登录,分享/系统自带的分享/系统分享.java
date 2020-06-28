https://www.jianshu.com/p/a950f5596a01
https://www.jianshu.com/p/ea33092b6354

//����ϵͳ��intent���򿪵���ʵ�ǿ�ܲ��е�ChooserActivity
//�ŵ㣺���ü�
//ȱ�㣺
//1����ʽ�Ĳ���  2��û�а�װ��Ӧ�ó�����ʾ����
//3.����ϵͳ�ķ����ܿ��Բ�������API���ɣ��ȽϷ��㣬���ǲ��õĵط�����û�лص�����֪���Ƿ�����ˣ������Ƿ�ɹ��ˡ�

final String TEXT = "text/plain";
final String IMAGE = "image/*";
final String IMAGE = "image/jpeg";//ͼƬ/jpeg����
final String AUDIO = "audio/*";
final String VIDEO = "video/*";
final String File = "*/*";


private void share() {
	Intent sendIntent = new Intent();
	sendIntent.setAction(Intent.ACTION_SEND);
	sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);//������

	//��������
	sendIntent.setType("text/plain");//���÷������ݵ�����
	sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");

	//����ͼƬ
	sendIntent.setType("image/jpeg");//image/*
	sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/sdcard/beauty1.jpg")));
	
	//�����ͼƬ(ACTION_SEND_MULTIPLE)
	sendIntent.setType("image/png");
	sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

    //�����ļ�
    sendIntent.setType("*/*");
	sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
	
	
	//��ʼ����
	startActivity(Intent.createChooser(sendIntent, "��ѡ����Ҫ�����Ӧ�ó���"));//��2: ����Dialog����
}

//������ݸ߰汾:����
private void shareFile(String filePath) {
	/**
	 * ��1: �����ȥ������, ����Ƿ����ļ�, �����������Ҳû��
	 * ��2: �ļ�·��
	 * ��3: FLAG_ACTIVITY_NEW_TASK, ���true: ��������Ӧapp, Ȼ���ٵ����������
	 */
	Intent sendIntent = IntentUtils.getShareImageIntent("", filePath, false);
	sendIntent.setType("*/*");
//    startActivity(sendIntent);//�������ʾ "����ʽ"
    startActivity(Intent.createChooser(sendIntent, "��ѡ����Ҫ�����Ӧ�ó���"));
}

�嵥�ļ�:(ע:��֪�����費��Ҫ����Ȩ�޵�,��Ϊ��ģ����)
<!-- �������������Ȩ�� -->
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />


//ͨ�����·�������ֻ���ȫ�������ڷ����app�İ�����Activity����
Intent share = new Intent(android.content.Intent.ACTION_SEND);
PackageManager packageManager = getPackageManager();
List<ResolveInfo> list = packageManager.queryIntentActivities(share, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
for(ResolveInfo info :list){
	MyUtils.log(""+info.activityInfo.packageName+"---"+info.activityInfo.name);
}

//androidϵͳ�������Intent.createChooser https://www.jianshu.com/p/9fd5363a9356


//ָ��Ŀ��app������QQ��΢�ž���: https://blog.csdn.net/qq_27570955/article/details/52685456
sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//΢������
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//���浽QQ�ղ�
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ�����촫
//sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//�����ҵĵ���
sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ���ѻ�QQȺ
//sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//΢������Ȧ����֧�ַ���ͼƬ

private void share2Wechat() {
	Intent sendIntent = IntentUtils.getShareImageIntent("", filePath, false);
	sendIntent.setType("*/*");
	sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
	try {
		startActivity(sendIntent);
	} catch (Exception e) {
		e.printStackTrace();
		ToastUtils.show("δ��װ΢��");
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
		ToastUtils.show("δ��װQQ");
	}
}
