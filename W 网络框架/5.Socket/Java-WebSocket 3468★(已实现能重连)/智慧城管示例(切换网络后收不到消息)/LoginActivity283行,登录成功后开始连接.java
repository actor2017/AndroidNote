package com.kuchuanyun.wisdomcitymanagement.activity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void parseLoginJson(String response) {
        LoginInfo loginInfo = JSON.parseObject(response, LoginInfo.class);
        if (loginInfo.errCode == 0) {
            toast("登录成功!");
            aCache.put(Global.PERMS,(ArrayList<String>) loginInfo.data.perms);
            aCache.put(Global.USERID,loginInfo.data.userId);

            //开始长连接
            ChatClient.getInstance().setUserNo(loginInfo.data.no).startConnection();

            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            toast(loginInfo.errMsg);
        }
    }
}
