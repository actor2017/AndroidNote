package com.kuchuan.wisdompolice.activity;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //警用端登录
    private void parseJson(String response) {
        LoginZHMPGson loginResult = new Gson().fromJson(response, LoginZHMPGson.class);
        if (loginResult.errCode == 0) {
			startService(new Intent(this,MobileImSdkService.class));
            
            aCache.put(Global.token, loginResult.data.token);
            aCache.put(Global.name,loginResult.data.name);//姓名
            toast("登陆成功!");
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            toast(loginResult.errMsg);
        }
    }
}
