package com.kuchuan.education.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * apk util
 * <br/>
 * Apk文件操作相关工具类
 *
 * @author wlf(Andy)
 * @email 411086563@qq.com
 */
public class ApkUtil {

    /**
     * get UnInstallApkPackageName 获取包名
     *
     * @param context Context
     * @param apkPath apkPath
     * @return apk PackageName
     */
    public static String getUnInstallApkPackageName(Context context, String apkPath) {
        PackageInfo info = context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            if (appInfo != null) {
                return appInfo.packageName;
            }
        }
        return null;
    }

    /**
     * get unInstallApkName 获取apk名称
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static String getUnInstallApkName(Context context, String apkPath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = packageManager.getPackageArchiveInfo(apkPath, PackageManager
                .GET_ACTIVITIES);
        if (pi != null) {
            ApplicationInfo appInfo = pi.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadLabel(packageManager).toString();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取应用程序图片Drawable
     *
     * @param apkPath
     * @return
     */
    public static Drawable getUnInstallApkIcon(Context context, String apkPath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = packageManager.getPackageArchiveInfo(apkPath, PackageManager
                .GET_ACTIVITIES);
        if (pi != null) {
            ApplicationInfo appInfo = pi.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(packageManager);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get unInstallApkName 获取apk版本名称
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static String getUnInstallApkVersionName(Context context, String apkPath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = packageManager.getPackageArchiveInfo(apkPath, PackageManager
                .GET_ACTIVITIES);
        if (pi != null) {
            return pi.versionName == null ? null : pi.versionName;
        }
        return null;
    }

    /**
     * install an apk bu apkPath
     *
     * @param context Context
     * @param apkPath apkPath
     */
    public static void installApk(final Context context, final String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            return;
        }
        final String authorities = "com.kuchuan.education.view.impl.activity.LoginActivity";
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!SilentInstall.install(getUnInstallApkPackageName(context,apkPath),apkPath)) {//静默安装
                    ThreadUtils.RunOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            File apkFile = new File(apkPath);
                            Uri apkUri;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// 判断版本大于等于7.0
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//对目标应用临时授权该Uri所代表的文件
                                apkUri = FileProvider.getUriForFile(context, authorities, apkFile);//authorities:即是在清单文件中配置的authorities
                            } else {
                                apkUri = Uri.fromFile(apkFile);
                            }
                            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            context.startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * install an apk bu apkPath
     *
     * @param activity Context没有startActivityForResult方法
     * @param apkFile 安装文件,和apkPath中至少有一个不能为空
     * @param apkPath apk路径,和apkFile中至少有一个不能为空
     * @param mustInstall 是否要强制安装?,如果要强制安装,要自己重写onActivityResult方法
     * @param requestCode 如果要强制安装的请求码
     * @param authorities 在清单文件中配置的authorities,如果在Android 7.0版本上,必须传入这个.
     *                    例:com.kuchuan.wisdompolice.activity.LoginActivity
     */
    public static void installApk(@NonNull Activity activity, File apkFile, String apkPath, boolean mustInstall, int requestCode, @Nullable String authorities) {
        if (apkFile == null && TextUtils.isEmpty(apkPath)) {
            return;
        }
        if (apkFile == null) {
            apkFile = new File(apkPath);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        /**
         * 大坑:
         * 1.如果没有在Activity环境下启动Activity,必须设置这个
         * 2.如果在Activity环境中启动Activity,特别是StartActivityForResult的时候,千万不能写这句,否则会一直提示安装!!!
         */
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// 判断版本大于等于7.0
            if (TextUtils.isEmpty(authorities)){
//                throw new NullPointerException("在android7.0,安装apk的authorities参数不能为空!");
                return;
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//对目标应用临时授权该Uri所代表的文件
            apkUri = FileProvider.getUriForFile(activity, authorities, apkFile);//authorities:即是在清单文件中配置的authorities
        } else {
            apkUri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        if (mustInstall) {
            activity.startActivityForResult(intent,requestCode);
        } else {
            activity.startActivity(intent);
        }
        /**
         * 下面的也可以安装apk
         * //Intent, action: 隐式意图
         * Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.addCategory(Intent.CATEGORY_DEFAULT);
         intent.setDataAndType(Uri.fromFile(new File(downloadFileInfo.getFilePath())),
         "application/vnd.android.package-archive");
         startActivity(intent);
         */
    }

    /**
     * check whether app installed 获取apk是否安装,true:已安装,false:未安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkAppInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取已经安装apk的版本号
     * @param packageName 包名
     * @return 版本号
     */
    public static Integer getInstalledApkVersionNo(Context context,String packageName){
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //打开APK
    public static void openAPK(Context context,String packagename) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packagename);
        context.startActivity(intent);
    }
}
