package com.shijing.huanxin.view;

/**
 * Description: 类的功能描述//Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/20 on 20:40.
 */

public interface ILoginView {
    void usernameEmpty();
    void passwordEmpty();
    void showProgressDialog();
    void switchToMainActivity();
    void loginFaile(String msg);
}
