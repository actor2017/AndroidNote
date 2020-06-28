package com.shijing.huanxin.view.impl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.shijing.huanxin.R;
import com.shijing.huanxin.presenter.LoginPresenter;
import com.shijing.huanxin.utils.ToastUtils;
import com.shijing.huanxin.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {

    LoginPresenter loginPresenter;//初始化p层

    @BindView(R.id.et_num)      //账号
    EditText etNum;
    @BindView(R.id.et_pwd)      //密码
    EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);//初始化p层
    }

    @OnClick({R.id.btn, R.id.tv_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:      //登录
                String username = etNum.getText().toString().trim();
                String password = etPwd.getText().toString().trim();

                //调用p层逻辑
                loginPresenter.login(username,password);

                break;
            case R.id.tv_new:   //新用户
                startActivity(RegisterActivity.class,false);
                hideProgressDialog();
                break;
        }
    }

    @Override
    public void usernameEmpty(){
        ToastUtils.show(this,"亲,请输入账号");
    }

    @Override
    public void passwordEmpty(){
        ToastUtils.show(this,"亲,请输入密码");
    }
    @Override
    public void showProgressDialog (){
        showProgressDialog("登录中...");
    }

    @Override//登录成功,跳到MianActivity
    public void switchToMainActivity() {
        hideProgressDialog();
        startActivity(MainActivity.class,true);
    }

    @Override//登录失败
    public void loginFaile(String msg){
        hideProgressDialog();
        ToastUtils.show(this,msg);
    }
}
