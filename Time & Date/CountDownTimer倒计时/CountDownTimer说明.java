import android.os CountDownTimer;

CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tv_yzm, 60000, 1000);
mCountDownTimerUtils.start();

private CountDownTimer countDownTimer;

private void init() {
	//����new�������ü�ʱ...
	countDownTimer = new CountDownTimer(getCountDownSecond() * 1000, 500) {//��ʱ��,�ص����ʱ��(ҪС��1s, ���򼸰ٺ����ʱ��ֱ�ӵ���onFinish())

		@Override
		public void onTick(long millisUntilFinished) {
			tvCountDown.setText(getStringFormat("%02d�����������", millisUntilFinished / 1000));
			textView.setText((millisUntilFinished / 1000) + "�����ط�");
		}

		@Override
		public void onFinish() {
			tvCountDown.setText("0�����������");
			textView.setEnabled(true);
			textView.setText("��ȡ��֤��");
			init();//��������ü�ʱ����ʱ��(��һ������), ��Ҫ����new CountDownTimer();
		}
	}.start();//��ʼ
}

@Override
protected void onDestroy() {
	super.onDestroy();
	countDownTimer.cancel();//ȡ��
}

