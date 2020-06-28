package com.shijing.huanxin.view.impl.fragment;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Description: fragment工厂
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/21 on 13:36.
 */
public class FragmentFactory {

	private FragmentFactory(){}
    private static ArrayList<Fragment> arrayList;

    public static ArrayList<Fragment> getFragment() {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            arrayList.add(new ConversationFragment());
            arrayList.add(new ContactsFragment());
            arrayList.add(new PluginFragment());
        }
        return arrayList;
    }
}
