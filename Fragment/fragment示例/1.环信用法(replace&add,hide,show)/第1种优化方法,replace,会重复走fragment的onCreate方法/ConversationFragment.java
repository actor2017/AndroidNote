package com.shijing.huanxin.view.impl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Description: 消息的fragment
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/21 on 14:56.
 */

public class ConversationFragment extends BaseFragment {

    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (tv == null) {
            tv = new TextView(getActivity());
            tv.setText("ConversationFragment消息");
        }
        return tv;
    }
}
