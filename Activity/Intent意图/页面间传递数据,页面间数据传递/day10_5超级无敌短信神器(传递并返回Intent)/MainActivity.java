//
public class MainActivity extends Activity {

	/**
	 * ͨѶ¼����
	 */
	public void contacts(View v) {
		//��ʾ��ͼ
		Intent intent = new Intent(this, ContactsActivity.class);
		startActivityForResult(intent, 1);
	}

	/*
	 * ���ٻظ����Ź���
	 */
	public void fast(View v) {
		Intent intent = new Intent(this, FastReceiveActivity.class);
		startActivityForResult(intent, 11);
	}

	/**
	 * 4. ���ܷ��ص�����
	 */
	@Override							//������			�����		��ͼ���ص�����
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		//�жϽ����
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
