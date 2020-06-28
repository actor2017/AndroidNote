package com.shijing.huanxin.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.shijing.huanxin.view.impl.activity.MainActivity;
import com.shijing.huanxin.view.impl.fragment.FragmentFactory;

import java.util.ArrayList;

/**
 * Description: 类的功能描述//Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/20 on 23:30.
 */

public class MainPresenter {
    ArrayList<Fragment> fragmentsList = FragmentFactory.getFragment();
    //切换到某一个fragment
    public void switchToPosition(MainActivity mainActivity, int position) {
        /**
         * 切换到某一个fragment,为了避免Fragment的onCreateView重复执行的方法
         * 1、将每一个Fragment的view对象变成成员变量  在onCreateView中进行非空的判断
         * 2、不使用replace  而是用add 和hide来操作Fragment
         */
        FragmentManager supportFragmentManager = mainActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < fragmentsList.size(); i++) {
            if (i == position) {
                fragmentTransaction.show(fragmentsList.get(i));
            } else {
                fragmentTransaction.hide(fragmentsList.get(i));
            }
        }
        fragmentTransaction.commit();
    }
}
