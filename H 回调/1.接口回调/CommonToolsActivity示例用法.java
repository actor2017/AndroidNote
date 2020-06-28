
public class CommonToolsActivity extends AppCompatActivity {

    //还原短信
    private void restoreSms() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SmsUtils.SmsRestore(CommonToolsActivity.this, SmsUtils.defaultbackupPath
                            (CommonToolsActivity.this), new SmsUtils.OnSmsCallback() {

                        int smscount = 0;
                        @Override
                        public void getSmsCount(int smscount) {
                            this.smscount = smscount;
                            progressDialog.setMax(smscount);
                        }

                        @Override
                        public void getSmsProgress(int smsprogress) {
                            progressDialog.setProgress(smsprogress);
                            if (smsprogress == smscount) {
                                progressDialog.dismiss();
                                ToastUtils.showDdfault(CommonToolsActivity.this, "还原完成!");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showDdfault(CommonToolsActivity.this,"还原失败!");
                }
            }
        }).start();
    }
}
