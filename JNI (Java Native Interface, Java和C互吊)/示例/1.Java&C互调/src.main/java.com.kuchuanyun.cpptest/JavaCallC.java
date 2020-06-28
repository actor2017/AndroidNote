package com.kuchuanyun.cpptest;

/**
 * Description: Java调C
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2018/10/15 on 18:04
 */

public class JavaCallC {

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    //这是生成的Activity,可以随便写一个方法,然后Alt+Enter
    public static native int add(int x, int y);

    public static native void add10(int[] arr);//给数组每项加10
}
