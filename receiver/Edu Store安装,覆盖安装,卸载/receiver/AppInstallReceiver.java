package com.kuchuan.education.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.kuchuan.education.bean.UpdateDownloadStatus;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: app安装的receiver
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技 www.kuchuanyun.com
 * Author     : 李小松
 * Date       : 2017/8/31 on 10:52.
 */

public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        PackageManager pm = context.getPackageManager();
        //安装成功(如果是第一次安装,根据打印日志:安装)
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            EventBus.getDefault().post(new UpdateDownloadStatus(packageName));
        //替换成功(如果是覆盖安装,根据打印日志:卸载-->安装-->替换)
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            EventBus.getDefault().post(new UpdateDownloadStatus(packageName));
        //卸载成功(如果是卸载:根据打印日志:卸载)
        } else if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            EventBus.getDefault().post(new UpdateDownloadStatus(packageName));
        }
    }
}
