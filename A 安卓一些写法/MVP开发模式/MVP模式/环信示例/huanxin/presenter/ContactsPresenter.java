package com.shijing.huanxin.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.shijing.huanxin.utils.ThreadUtils;
import com.shijing.huanxin.view.IContactsView;

import java.util.List;

/**
 * Description: 连续人的p层
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/22 on 23:56.
 */

public class ContactsPresenter {

    private IContactsView iContactsView;

    public ContactsPresenter(IContactsView iContactsView) {
        this.iContactsView = iContactsView;
    }

    //缓存
    //SP  存储一些简单的配置信息
    //Sqlite 关系型数据库
    //文件  json数据   新闻详情、商品详情    //GreenDao
    public void updateContacts() {
        //1.先从本地服务器获取好友
        //
        ThreadUtils.RunOnSubThread(new Runnable() {             //子线程
            @Override
            public void run() {
                try {
                    final List<String> allContactsFromServer = EMClient.getInstance().contactManager()
                            .getAllContactsFromServer();
                    ThreadUtils.RunOnUiThread(new Runnable() {  //主线程
                        @Override
                        public void run() {
                            iContactsView.UpdateContacts(allContactsFromServer);
                            //3、将本地的缓存数据更新
                            //ContactDao.getInstance().insertFriends(currentUser,
                            // allContactsFromServer);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
