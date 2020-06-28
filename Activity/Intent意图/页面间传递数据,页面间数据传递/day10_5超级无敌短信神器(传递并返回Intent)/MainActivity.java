//
public class MainActivity extends Activity {

	/**
	 * 通讯录功能
	 */
	public void contacts(View v) {
		//显示意图
		Intent intent = new Intent(this, ContactsActivity.class);
		startActivityForResult(intent, 1);
	}

	/*
	 * 快速回复短信功能
	 */
	public void fast(View v) {
		Intent intent = new Intent(this, FastReceiveActivity.class);
		startActivityForResult(intent, 11);
	}

	/**
	 * 4. 接受返回的数据
	 */
	@Override							//请求码			结果码		意图返回的数据
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		//判断结果码
		switch (resultCode) {
			case 2:
				String phoneNum = data.getStringExtra("num");
				etPhoneNum.setText(phoneNum);
				break;
			case 3:
				String smsBody = data.getStringExtra("sms");
				etSmsBody.setText(smsBody);
				break;
			default:
				break;
		}
	}
}
