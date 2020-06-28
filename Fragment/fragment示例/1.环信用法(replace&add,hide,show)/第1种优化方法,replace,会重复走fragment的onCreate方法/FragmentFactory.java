package com.shijing.huanxin.view.impl.fragment;

import android.support.v4.app.Fragment;

import java.util.TreeMap;

/**
 * Description: fragment工厂
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/21 on 13:36.
 */
public class FragmentFactory {

    private static TreeMap<Integer,Fragment> treeMap = new TreeMap<>();

    public static Fragment getFragment(int position) {
        Fragment fragment = treeMap.get(position);
        if (fragment == null) {
            switch (position) {
            case 0:
                fragment = new ConversationFragment();//消息
                break;
            case 1:
                fragment = new ContactsFragment();//联系人
                break;
            case 2:
                fragment = new PluginFragment();//动态
                break;
            }
            treeMap.put(position,fragment);
        }
        return fragment;
    }
}
