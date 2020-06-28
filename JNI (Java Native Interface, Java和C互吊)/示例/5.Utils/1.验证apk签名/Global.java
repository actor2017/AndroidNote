package com.kuchuanyun.cpptest.utils;

import android.content.Context;

/**
 * Description: 类的描述
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2018/10/30 on 21:20
 */

public class Global {

    public static final String JNI_IP = "IP";
    public static final String JNI_PORT = "PORT";

    //下面是自定义字段
    public static final String EXCEPTION = "EXCEPTION";//异常字段
    public static final String EXCEPTION_PASSWORD = "12345678987654321";//查看异常的密码

    static {System.loadLibrary("native-lib");}
    public static native void jniInit(Context context, boolean isDebugMode);
    public static native String getString(String key);
}
