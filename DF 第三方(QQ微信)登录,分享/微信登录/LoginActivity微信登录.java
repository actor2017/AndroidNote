
public class LoginActivity extends BaseActivity {

    private Tencent        tencent;
    private UserInfo       mInfo;
    private String         tencentAppId = "1108291678";//222222
    private BaseUiListener iUiListener;
    private IWXAPI         iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);

        iwxapi = WXAPIFactory.createWXAPI(this, Global.WX_APPID, true);
        iwxapi.registerApp(Global.WX_APPID);// 将应用的appId注册到微信

        tencent = Tencent.createInstance(tencentAppId, getApplicationContext());
        iUiListener = new BaseUiListener() {
            @Override
            public void doComplete(JSONObject responseNotNull) {
                //保存token & openid, 保存后tencent.isSessionValid()=true
                tencent.initSessionCache(responseNotNull);
                logError(responseNotNull.toString());
                String openid = null;
                try {
                    openid = responseNotNull.getString(Global.openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (openid == null) {
                    toast("获取openid为空");
                    return;
                }
                SPUtils.putString(Global.openid, openid);//保存openid
                updateUserInfo();
            }
        };
    }

    @OnClick({R.id.iv_back, R.id.btn_login, R.id.tv_forget_password, R.id.tv_register, R.id.ib_qq, R.id.ib_wc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.btn_login://登录
//                TUIKit.login();
                if (isNoEmpty(etPhone, etPassword)) {
                    SPUtils.putString(Global.mobile, getText(etPhone));
                    SPUtils.putString(Global.password, getText(etPassword));
                    // F IXME: 2019/3/15 删除下面一行代码
//                    if (true) {
//                        startActivity(new Intent(activity, MainActivity.class));
//                        return;
//                    }
                    phoneLogin();
                }
                break;
            case R.id.tv_forget_password://忘记密码
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(Global.title, RegisterActivity.titles[0]);
                startActivity(intent, btnLogin);
                break;
            case R.id.tv_register://注册
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(Global.title, RegisterActivity.titles[1]);
                startActivity(intent, btnLogin);
                break;
            case R.id.ib_qq://qq登录
                logError("tencent.isSessionValid()" + String.valueOf(tencent.isSessionValid()));
                logError("tencent.checkSessionValid(tencentAppId):" + String.valueOf(tencent.checkSessionValid(tencentAppId)));

                //校验登录态,如果缓存的登录态有效，可以直接使用缓存而不需要再次拉起手Q
                boolean isValid = tencent.checkSessionValid(tencentAppId);
                if (isValid) {
                    JSONObject jsonObject = tencent.loadSession(tencentAppId);
                    if (jsonObject != null && jsonObject.length() > 0) {
                        iUiListener.doComplete(jsonObject);
                    } else throw new RuntimeException("获取的jsonobject为空!");
                } else {
                    /**
                     * param2:应用需要获得哪些接口的权限，由“，”分隔。例如：
                     *        SCOPE = “get_simple_userinfo,add_topic”；所有权限用“all”
                     * param3:没有装手q时支持二维码登录，一般用于电视等设备
                     */
                    tencent.login(this, "all", iUiListener, false);
                }
                break;
            case R.id.ib_wc://微信登录
                if (iwxapi.isWXAppInstalled()) {
                    final SendAuth.Req req = new SendAuth.Req();
                    /**
                     * 应用授权作用域，如获取用户个人信息则填写snsapi_userinfo
                     * "snsapi_userinfo,snsapi_friend,snsapi_message,snsapi_contact"
                     */
                    req.scope = "snsapi_userinfo";
                    /**
                     * 不是必须:
                     * 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击
                     * （跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
                     */
                    req.state = "hihi-----微信登录";
                    iwxapi.sendReq(req);
                } else toast("您手机尚未安装微信，请安装后再登录");
                break;
        }
    }

    /**
     * 微信登录回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxLoginResult(MessageEvent<String> messageEvent) {
        if (messageEvent.code == Global.MSG_EVT_WX_LOGIN) {
            String code = messageEvent.msg;
            /**
             * 调用后台接口获取token
             * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN
             */
            Network.getLoginWeChatApi()//这儿我们用的后台api
                    .login(code)
                    .enqueue(new Callback<LoginWeChatInfo>() {
                        @Override
                        public void onResponse(Call<LoginWeChatInfo> call, Response<LoginWeChatInfo> response) {
                            LoginWeChatInfo body = response.body();
                            if (body != null) {
                                //{"code":1101,"msg":"微信注册","data":{"openid":"o3S9G50O5Sjt_Cd3Te6V_mR9pgvw","unionid":"ol1gB1GZL42jQ9dQ5QiOLE23VHsY"}}
                                if (body.code == 1101) {//1101微信注册
                                    LoginWeChatInfo.DataBean data = body.data;
                                    if (data != null) {
                                        SPUtils.putString(Global.openid, data.openid);
                                        SPUtils.putString(Global.unionid, data.unionid);
                                        Intent intent = new Intent(activity, RegisterQqWxActivity.class);
                                        intent.putExtra(Global.isRegisterQq, false);
                                        startActivity(intent, btnLogin);
                                    }
                                } else if (body.code == 1000) {//成功
                                    toast(body.msg);
                                } else toast(body.msg);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginWeChatInfo> call, Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        }
    }

    private void updateUserInfo() {
        if (tencent.isSessionValid()) {//已经登录并保存了token & openid
            mInfo = new UserInfo(this, tencent.getQQToken());
            mInfo.getUserInfo(new BaseUiListener() {
                @Override
                public void doComplete(JSONObject responseNotNull) {
                    logError("updateUserInfo:" + responseNotNull.toString());
                    try {
                        String nickname = responseNotNull.getString(Global.nickname);//昵称
                        String portraitUrl = responseNotNull.getString("figureurl_qq");//头像
                        SPUtils.putString(Global.nickname, nickname);
                        SPUtils.putString(Global.portraitUrl, portraitUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    qqLogin();
                }
            });
        } else {//token & openid未保存成功啊?
            throw new RuntimeException("token & openid未保存成功啊?");
        }
    }

    //注：在某些低端机上调用登录后，由于内存紧张导致APP被系统回收，登录成功后无法成功回传数据, 解决办法如下:
    //在调用login的Activity或者Fragment重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
