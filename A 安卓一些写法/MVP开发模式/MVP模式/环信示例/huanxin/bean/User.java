package com.shijing.huanxin.bean;

import cn.bmob.v3.BmobUser;

/**
 * Description: 用户的bean类
 * 没有按照文档写继承BmobObject ,其实里面有一个BmobUser,老师写法
 */
public class User extends BmobUser {
    public User(String username,String passward){
        this.setUsername(username);
        this.setPassword(passward);
    }
}
