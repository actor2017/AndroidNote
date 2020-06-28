dialog_loading.xml代码:

<?xml version="1.0" encoding="utf-8"?>
<com.github.ybq.android.spinkit.SpinKitView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/spin_kit"
    style="@style/SpinKitView.ThreeBounce"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    app:SpinKit_Color="#666666"/>
<!--https://github.com/ybq/Android-SpinKit-->


package com.ly.changyi.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.ly.changyi.R;
import com.ly.changyi.utils.ThreadUtils;

public abstract class BaseFragment extends Fragment {

    private AlertDialog alertDialog;
    protected void showDialog() {
        if (activity == null) {
            activity = getActivity();
            if (activity == null) return;
        }
        if (alertDialog == null) {
            View view = getLayoutInflater().inflate(R.layout.dialog_loading, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setView(view);
            alertDialog = builder.create();
            //背景透明, 不然自定义view后面有一个白色框框
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.getWindow().setDimAmount(0.2f);//设置窗口后面灰色大背景的亮度[0-1]
        }
        ThreadUtils.RunOnUiThread(() -> {
            if (!alertDialog.isShowing()) alertDialog.show();
        });
    }
}
