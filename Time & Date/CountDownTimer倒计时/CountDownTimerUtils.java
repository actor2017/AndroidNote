/**
 * Created by Xia_�� on 2017/5/7.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //���ò��ɵ��
        mTextView.setText(millisUntilFinished / 1000 + "�������·���");  //���õ���ʱʱ��
        mTextView.setBackgroundResource(R.drawable.bg_identify_code_press); //���ð�ťΪ��
        SpannableString spannableString = new SpannableString(mTextView.getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("���»�ȡ��֤��");
        mTextView.setClickable(true);//���»�õ��
        mTextView.setBackgroundResource(R.drawable.bg_identify_code_normal);
    }
}