package com.kuchuanyun.rxjava2sample.adapter;

/**
 * Description: Java的三种注释 Javadoc标记
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2018/1/11 on 13:46
 */

    //1.单行注释

    /*
    2.多行注释,块(block)注释
    asdf
    12232
     */

    /**
     * 3.JavaDoc标记
     * 便于javadoc程序自动生成文档
	 * <p>换行
     */

import android.support.v7.widget.RecyclerView;

/**
 * {@link JavaDoc}                                          //链接 class
 * {@link JavaDoc#test()}                                   //链接 本class方法(中间#)
 * {@link #test()}                                          //链接 本class方法(开头#)
 * {@link RecyclerView#getAdapter()}                        //链接 其他class方法(中间#)
 * {@link testClass}                                        //链接 本class内部类
 * {@link android.support.v7.widget.RecyclerView.ViewHolder}//链接 其它class内部类
 * {@link Url}												==>显示Url
 * {@link Url @Url}											==>显示@Url
 * {@linkplain retrofit2.Retrofit.Builder#baseUrl(HttpUrl)}	==>显示一长串方法(加粗)
 * {@linkplain retrofit2.Retrofit.Builder#baseUrl(HttpUrl) base URL}	==>显示base URL(加粗)
 *
 * @see JavaDoc                                             //查看 class
 * @see JavaDoc#test()                                      //查看 本class方法(中间#)
 * @see #test()                                             //查看 本class方法(开头#)
 * @see RecyclerView#getAdapter()                           //查看 其它class方法(中间#)
 * @see testClass()                                         //查看 本class内部类
 * @see android.support.v7.widget.RecyclerView.ViewHolder   //查看 其它class内部类
 *
 * @see JavaDoc 和 {@link JavaDoc} 的区别:see必须打头写,link可以在任何位置.
 *
 * @author                          //作者
 * @since JDK1.1 or 1.0.0           //指定最早出现在哪个版本,API在什么程序的什么版本后开发支持。
 * {@value VIBRATOR_SERVICE}        //对常量进行注释
 * @version 1.0.0                   //指定版本信息
 * @deprecated						//不赞成；弃用；不宜用.类、变量或方法已经不提倡使用,在将来的版本中有可能被废弃
 */
public class JavaDoc<aBc extends RecyclerView.ViewHolder> {

    /**
     * @param abc                       //参数
	 * @param <T> 						//泛型注释
     * @return                          //返回值,居然可以这么瞎写...
     * @throws                          //描述方法抛出的异常，指明抛出异常的条件
     */
    private aBc getAbc(aBc abc) throws Exception {
        return null;
    }

    private aBc test() {
        return null;
    }

    private class testClass {}//内部类
}
