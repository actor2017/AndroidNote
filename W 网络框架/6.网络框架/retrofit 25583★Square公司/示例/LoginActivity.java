package com.ly.hihifriend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jaeger.library.StatusBarUtil;
import com.ly.bridgeemergency.R;
import com.ly.hihifriend.application.MyApplication;
import com.ly.hihifriend.info.BaseInfo;
import com.ly.hihifriend.network.Network;
import com.ly.hihifriend.utils.Global;
import com.ly.hihifriend.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description: 登录
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/14 on 15:42
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText    etPhone;
    @BindView(R.id.et_password)
    EditText    etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ib_qq)
    ImageButton ibQq;
    @BindView(R.id.ib_wc)
    ImageButton ibWc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucentForImageView(this, 30, null);
        ButterKnife.bind(this);

        etPhone.setText(SPUtils.getString(Global.username));
        etPassword.setText(SPUtils.getString(Global.password));
        if (MyApplication.instance.isDebugMode && isEmpty(etPhone, etPassword)) {
            etPhone.setText("16884566665");
            etPassword.setText("123456");
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_login, R.id.tv_register, R.id.ib_qq, R.id.ib_wc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.btn_login://登录
                if (isNoEmpty(etPhone, etPassword)) {
                    SPUtils.putString(Global.username, getText(etPhone));
                    SPUtils.putString(Global.password, getText(etPassword));
                    Network.getLoginApi()
                            .login(getText(etPhone), getText(etPassword))
                            .enqueue(new Callback<BaseInfo>() {
                                @Override
                                public void onResponse(Call<BaseInfo> call, Response<BaseInfo> response) {
                                    startActivity(new Intent(activity, MainActivity.class));
                                }

                                @Override
                                public void onFailure(Call<BaseInfo> call, Throwable t) {

                                }
                            });

                }
                break;
            case R.id.tv_register://注册
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(Global.title, RegisterActivity.titles[0]);
                startActivity(intent, btnLogin);
                break;
            case R.id.ib_qq:
                toast("qq登录");
                break;
            case R.id.ib_wc:
                toast("微信登录");
                break;
        }
    }
}
