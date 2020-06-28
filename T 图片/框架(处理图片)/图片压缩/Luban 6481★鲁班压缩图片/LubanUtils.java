package com.ly.hihifriend.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Description: 鲁班压缩 https://github.com/Curzibn/Luban
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/13 on 16:46
 * @version 1.0
 */
public class LubanUtils {

    /**
     * @param context
     * @param t 图片
     * @param <T> String, File, Uri or Bitmap
     */
    public static <T> void compress(Context context, T t, OnCompressListener listener) {
        List<T> list = new ArrayList<>(1);
        list.add(t);
        compress(context, list, listener);
    }

    /**
     * @param context
     * @param pics 图片集合
     * @param <T> String, File, Uri or Bitmap
     */
    public static <T> void compress(Context context, List<T> pics, OnCompressListener listener) {
        Luban.with(context)
                .load(pics)
                .ignoreBy(999)
//                .putGear(1)//什么都没做...
                .setFocusAlpha(true)//设置是否保留透明通道
                .setTargetDir(FileUtils.getCacheDir().getAbsolutePath())//缓存压缩图片路径
                .setRenameListener(null)//压缩前重命名接口
                .filter(new CompressionPredicate() {//设置开启压缩条件
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(listener)
//                .get()//List<File>
//                .get("path")//File
                .launch();
    }
}
