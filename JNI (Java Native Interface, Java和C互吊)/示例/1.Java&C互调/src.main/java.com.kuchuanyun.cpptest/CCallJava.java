package com.kuchuanyun.cpptest;

import com.kuchuanyun.cpptest.utils.ToastUtils;

/**
 * Description: C调用Java,主要是利用反射，这样就能调用Java代码了
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2017/1/8 on 20:17
 */

public class CCallJava {

    static {
        System.loadLibrary("native-lib");//★★★注意,这儿不能写:c-call-java★★★
    }

    /**
     * 这个方法调用C,然后C调用下面的方法{@link #calledByC(String)}
     */
    public static native void callVoid();

    /**
     * 这个方法调用C,然后C调用下面的<b>静态</b>方法{@link #StaticMethodCalledByC(int)}
     */
    public static native void staticMethodCalledVoid();

    //供C语言调用
    public void calledByC(String msg) {
        System.out.println("calledByC: msg=" + msg);
        ToastUtils.show("calledByC: msg=" + msg);
    }

    /**
     * C调用静态方法
     */
    public static void StaticMethodCalledByC(int randValue){
        System.out.println("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
        ToastUtils.show("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
    }
}
