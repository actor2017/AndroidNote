package com.shijing.huanxin.presenter;

import com.hyphenate.chat.EMClient;
import com.shijing.huanxin.view.ISplashView;

/**
 * Description: splash的p层
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/20 on 20:26.
 */

public class SplashPresenter {

    private final ISplashView splashView;//保存v层示例对象(接口)

    public SplashPresenter(ISplashView splashView) {
        this.splashView = splashView;
    }

    public void checkLogin() {
        //判断是否已经登陆过
        final boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
        //判断是否已经连接(不知道是指的什么鬼连接,没文档没注释,固定写法)
        final boolean connected = EMClient.getInstance().isConnected();

        if (loggedInBefore && connected) {//如果登录过且连接(可能是固定写法,不知道connected什么鬼)
            splashView.switchToMainActivity();
        } else {
            splashView.switchToLoginActivity();
        }
    }
}
