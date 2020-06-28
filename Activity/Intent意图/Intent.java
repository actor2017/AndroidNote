�Ĵ�������ܱ�New����
    activity	:����		�û����Կ���,ֱ�Ӳ�����ҳ��
    Activity֮��ͨ��Intent����ͨ�š�

1.ע��: 0? <= requestCode < 65536 ,���忴Դ��
startActivityForResult(Intent intent, int requestCode);

2.��ת���棬����ջ������Activity���,��2��һ��Ҫһ��ʹ��
Intent intent = new Intent(activity, MainActivity.class);
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);

3.����Ҳ����ղ���ת
Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
context.startActivity(intent);

4.��Ȼ�����¼ҳ��launchModeΪsingleTask�Ļ���ֱ�ӵ���startActivity����


//-------------------------------------------startActivity()��ͼ������������-------------
### 5 ��ͼ�����������ͣ��ص㣩
* Java�а˴�������ͼ�������
* Serializable	�����ڴ��е��������л���������
* Parcelable	�����ڴ��е��������л��������ڴ�
* Bundle		��������
* Intent		����ͼ
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("name", "����");
intent.putExtra("age", 23);
startActivity(intent);//�����Դ���Bundle

//-------------------------------------------------------startActivityForResult()--------
### 7 ����activity��ȡ����ֵ���ص㣩
* ����Ŀ��activity���ȴ�Ŀ��activity�رգ���������

1. ��һ������ķ�ʽ����Ŀ��activity
	startActivityForResult(intent, 1);//��2������:������

2. ����Ҫ���ص����ݲ��ر�
		Intent intent = new Intent();
		intent.putExtra("num", 123);
		setResult(RESULT_OK, intent);//RESULT_OK:�����
		finish();

3. ���ܷ��ص�����
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			String phoneNum = data.getIntExtra("num");
			etPhoneNum.setText(phoneNum);
		}

//---------------------------------------------------------------------onNewIntent------------
onNewIntent
��Activity����Standardģʽ�����ұ����õ�ʱ�򣬻ᴥ��onNewIntent(Intent intent) ���������
һ��������ȡ�µ�Intent���ݵ�����.
