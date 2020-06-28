import android.os CountDownTimer;

CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tv_yzm, 60000, 1000);
mCountDownTimerUtils.start();

private CountDownTimer countDownTimer;

private void init() {
	//必须new才能重置计时...
	countDownTimer = new CountDownTimer(getCountDownSecond() * 1000, 500) {//总时间,回调间隔时间(要小于1s, 否则几百毫秒的时候直接调用onFinish())

		@Override
		public void onTick(long millisUntilFinished) {
			tvCountDown.setText(getStringFormat("%02d秒后重置密码", millisUntilFinished / 1000));
			textView.setText((millisUntilFinished / 1000) + "秒后可重发");
		}

		@Override
		public void onFinish() {
			tvCountDown.setText("0秒后重置密码");
			textView.setEnabled(true);
			textView.setText("获取验证码");
			init();//如果想重置计时的总时间(第一个参数), 需要重新new CountDownTimer();
		}
	}.start();//开始
}

@Override
protected void onDestroy() {
	super.onDestroy();
	countDownTimer.cancel();//取消
}

