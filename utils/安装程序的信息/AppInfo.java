package com.actor.androiddevelophelper.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by Kevin.
 * <p>
 * 应用信息封装
 */

/**
 * 如果要系列化对象:
 * 1.对象和字段都要implements Serializable
 * 2.Drawable没有implements Serializable,不能存储到本地
 * Bitmap implements Parcelable,在这儿也不能存储, 可以转换成了byte[]
 *
 * @version 1.0
 */
public class AppInfo {
    public String   apkMd5Sign;     //MD5签名(去掉:就是证书签名)
    public String   apkSha1Sign;    //Sha1签名
    public String   apkSha256Sign;  //Sha256签名
    public String   appName;        //应用名
    public String   apkSourceDir;   //apk安装路径
    //private byte[] drawableIcon;  //存储的时候可用byte的形式存储icon
    //public Bitmap icon;           //Bitmap
    public Drawable icon;           //图标
    public boolean  isSdcard;       //是否安装在sdcard
    public boolean  isSystemApp;    //是否是用户应用
    public String   packageName;    //包名
    public long     size;           //大小, 可转换成xxMB, android.text.format.Formatter.formatFileSize(Context, long);
    public int      uid;            //同一个app装在不同的手机上的uid不一样,手机里每个app的uid也不一样
    public int      versionCode;    //版本号
    public String   versionName;    //版本名称
}
