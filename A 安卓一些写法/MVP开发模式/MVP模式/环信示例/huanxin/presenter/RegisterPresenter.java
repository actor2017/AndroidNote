package com.shijing.huanxin.presenter;

import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.shijing.huanxin.bean.User;
import com.shijing.huanxin.utils.ThreadUtils;
import com.shijing.huanxin.view.IRegisterView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Description: 类的功能描述//Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/20 on 23:00.
 */

public class RegisterPresenter {
    private final IRegisterView iRegisterView;

    public RegisterPresenter(IRegisterView iRegisterView) {
        this.iRegisterView = iRegisterView;
    }

    public void register(final String username, final String password, String password2) {
        if (TextUtils.isEmpty(username)) {
            iRegisterView.errorMsg("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            iRegisterView.errorMsg("密码不能为空");
            return;
        }
        //用户名必须以小写字母开头  3到20位  ^[a-zA-Z]\w{2,19}$
        if (!username.matches("^[a-z]{3,20}$")) {
            iRegisterView.errorMsg("账号必须全部小写字母,并且3-20位");
            return;
        }
        if (!password.equals(password2)) {
            iRegisterView.errorMsg("两次输入的密码不一致");
            return;
        }
        //密码必须都是数字  3~20位  ^[0-9]{2,19}$
        if (!password.matches("^[0-9]{2,19}$")) {
            iRegisterView.errorMsg("密码必须都是数字,3-20位");
            return;
        }

        iRegisterView.showProgressDialog();

        //将用户的数据先保存在自己的服务器  移动云平台  BMob (假定认为是我们自己的服务器)
        //如果注册在BMob成功了之后，将数据存储在环信的服务器上
        //将数据存储在环信的服务器上成功了之后，代表bMob和环信都有同一个用户信息。
        //如果在环信上注册失败了呢？将之前在BMob上存储的数据删除

        //注册失败会抛出HyphenateException
        User user = new User(username, password);//bmob
        user.signUp(new SaveListener<User>() {  //bmob添加数据
            @Override
            public void done(final User user, final BmobException e) {
                if (e == null) {                //添加数据成功
                    ThreadUtils.RunOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            try {                           //环信注册,同步方法
                                EMClient.getInstance().createAccount(username, password);
                                iRegisterView.errorMsg("环信注册成功!");
                                ThreadUtils.RunOnUiThread(new Runnable() {//主线程
                                    @Override
                                    public void run() {
                                        iRegisterView.hideProgressDialog(true);//隐藏
                                    }
                                });
                            } catch (final HyphenateException e1) {//环信失败,删除bmob
                                e1.printStackTrace();
                                ThreadUtils.RunOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        iRegisterView.hideProgressDialog(false);
                                        iRegisterView.errorMsg("环信注册失败!" + e1.getMessage());
                                    }
                                });
                                user.delete(new UpdateListener() {      //子线程
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            iRegisterView.errorMsg("Bmob删除成功!");
                                        } else {
                                            iRegisterView.errorMsg("Bmob删除失败!");
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {                        //添加数据失败
                    iRegisterView.hideProgressDialog(false);
                    iRegisterView.errorMsg(e.getMessage());
                }
            }
        });
    }
}
