package com.shijing.huanxin.view.impl.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.shijing.huanxin.R;
import com.shijing.huanxin.presenter.RegisterPresenter;
import com.shijing.huanxin.utils.ToastUtils;
import com.shijing.huanxin.view.IRegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements IRegisterView {

    RegisterPresenter registerPresenter;
    @BindView(R.id.et_num)  //账号
            EditText etNum;
    @BindView(R.id.et_pwd)  //密码
            EditText etPwd;
    @BindView(R.id.et_pwd2) //再次输入密码
            EditText etPwd2;

    String username;
    String password;
    String password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerPresenter = new RegisterPresenter(this);
    }

    /**
     * 环信:注册用户名会自动转为小写字母，所以建议用户名均以小写注册。
     * （强烈建议开发者通过后台调用 REST 接口去注册环信 ID，客户端注册方法不提倡使用。）
     */
    @OnClick(R.id.btn)  //登录按钮
    public void onClick() {
        username = etNum.getText().toString();
        password = etPwd.getText().toString();
        password2 = etPwd2.getText().toString();
        registerPresenter.register(username,password,password2);//调用p层逻辑
    }

    @Override
    public void errorMsg(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog("注册中...");
    }

    @Override
    public void hideProgressDialog(boolean isFinish) {
        hideProgressDialog();
        if (isFinish) {
            finish();
        }
    }
}
