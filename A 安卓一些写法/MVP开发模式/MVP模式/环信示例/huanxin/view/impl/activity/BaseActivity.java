package com.shijing.huanxin.view.impl.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Description: 类的功能描述
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/3/20 on 8:56.
 *
 * 用法:继承本类,在onCreate里面直接调用showProgressDialog(String title);就能显示进度条对话框
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    //重写onCreate,注意只有1个参数
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        //默认圆环形进度条对话框,可不设置
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    //真正的科技不会让你感觉到科技的存在
//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
//    }

    //重写并自定义startActivity方法,是否需要finish
    public void startActivity(Class clazz, boolean isFinish) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    protected void showProgressDialog(String title){
        progressDialog.setMessage(title);
        progressDialog.show();
    }

    protected void  hideProgressDialog(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
