package com.shijing.huanxin.view.impl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.shijing.huanxin.R;
import com.shijing.huanxin.utils.ThreadUtils;
import com.shijing.huanxin.utils.ToastUtils;
import com.shijing.huanxin.view.impl.activity.LoginActivity;

/**
 * Description: "动态"的fragment
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/21 on 14:57.
 */

public class PluginFragment extends BaseFragment {

    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_plugin, null);
        Button btn_exit = (Button) view.findViewById(R.id.btn_exit);
        btn_exit.setText("退("+EMClient.getInstance().getCurrentUser()+")出");
        btn_exit.setOnClickListener(new View.OnClickListener() {//退出
            @Override
            public void onClick(View v) {
                ThreadUtils.RunOnSubThread(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(true, new EMCallBack() {//退出的异步方法

                            @Override
                            public void onSuccess() {
                                ToastUtils.show(getActivity(),"退出成功");
                                ThreadUtils.RunOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(LoginActivity.class,true);
                                    }
                                });
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int code, String message) {
                                ToastUtils.show(getActivity(),"退出失败,请检查网络!");
                            }
                        });
                    }
                });
            }
        });
        return view;
    }

    @Override
    public String setTitle() {
        return "动态";
    }
}
