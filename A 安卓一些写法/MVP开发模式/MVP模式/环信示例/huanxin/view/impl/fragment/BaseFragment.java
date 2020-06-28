package com.shijing.huanxin.view.impl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shijing.huanxin.R;

/**
 * Description: 设置标题的文字 和 重写startActivity
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/21 on 13:34.
 */

public abstract class BaseFragment extends Fragment {

    protected ImageView iv_add;

    //指的是这个fragment所包装的view对象创建完成之后会进行的方法回调
    //在onCreateView之后,会调用的一个方法,目的是得到onCreateView的返回值
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv_title = (TextView) view.findViewById(R.id.tvTitle);
        iv_add = (ImageView) view.findViewById(R.id.ivAdd);
        tv_title.setText(setTitle());
    }

    //每一个子类fragment的标题
    public abstract String setTitle();

    //重写startActivity方法
    public void startActivity(Class clazz, boolean isFinish) {
        startActivity(new Intent(getActivity(),clazz));
        if (isFinish) {
            getActivity().finish();
        }
    }
}
