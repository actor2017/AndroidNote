package com.shijing.huanxin.presenter;

import android.text.TextUtils;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.shijing.huanxin.utils.ThreadUtils;
import com.shijing.huanxin.view.ILoginView;

/**
 * Description: 类的功能描述//Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/20 on 20:41.
 */

public class LoginPresenter {
    private final ILoginView iLoginView;//拥有v层的实例对象(接口)

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    //登录
    public void login(String username,String password){
        if (TextUtils.isEmpty(username)) {
            iLoginView.usernameEmpty();     //通过接口调用v层的方法
            return;
        }
        if (TextUtils.isEmpty(password)) {
            iLoginView.passwordEmpty();     //通过接口调用v层的方法
            return;
        }
        iLoginView.showProgressDialog();//显示进度条
        //登录环信
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                ThreadUtils.RunOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //登录主界面
                        iLoginView.switchToMainActivity();
                    }
                });
            }

            @Override
            public void onError(int i, final String s) {
                ThreadUtils.RunOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //提示出错信息
                        iLoginView.loginFaile(s);
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
