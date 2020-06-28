package com.ly.hihifriend.utils;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;

/**
 * Description: 类的描述
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/5/17 on 17:39
 */
public class CacheUtils {

    /**
     * 获取缓存大小
     * @param context
     * @return
     */
    public static long getAllCacheSize(Context context) {
        //缓存大小
        long cacheSize = FileUtils.getDirLength(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += FileUtils.getDirLength(context.getExternalCacheDir());
        }
        return cacheSize;
    }

    public static String getAllCacheSizeString(Context context) {
        return Formatter.formatFileSize(context, getAllCacheSize(context));
    }

    /**
     * 清除所有缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        FileUtils.deleteAllInDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.deleteAllInDir(context.getExternalCacheDir());
        }
    }

    /**
     * 清除本应用内部缓存(/data/data/包名/cache)
     */
    public static boolean cleanInternalCache(Context context) {
        return FileUtils.deleteAllInDir(context.getCacheDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.deleteAllInDir(context.getExternalCacheDir());
        }
    }

    /**
     * 按名字清除本应用数据库
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除本应用所有数据库(/data/data/包名/databases),未测试
     */
    public static void cleanDatabases(Context context, String databaseName) {
        File databasePath = context.getDatabasePath(databaseName);
        FileUtils.deleteAllInDir(databasePath);
//        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     */
    public static void cleanSharedPreference(Context context) {
        File file = new File("/data/data/" + context.getPackageName() + "/shared_prefs");
        //...
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     */
    public static void cleanFiles(Context context) {
        FileUtils.deleteAllInDir(context.getFilesDir());
    }
}
