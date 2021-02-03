package com.actor.myandroidframework.utils;

import android.content.SharedPreferences;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * description: https://github.com/Tencent/MMKV
 * MMKV 是腾讯基于 mmap 内存映射的 key-value 组件, 性能&可靠性远远高于SharedPreferences
 * 使用:
 *   1.添加依赖
 *     //https://github.com/Tencent/MMKV 腾讯键值存储, 性能&可靠性远高于SharedPreferences
 *     implementation 'com.tencent:mmkv-static:1.2.2'
 *   2.在Application中初始化
 *     //初始化腾讯键值 MMKV
 *     String rootDir = MMKV.initialize(this);
 *
 * @author : 李大发
 * date       : 2020/5/22 on 16:36
 * @version 1.0
 */
public class MMKVUtils {

    //腾讯键值缓存
    protected static MMKV mmkv;

    public static MMKV getMMKV() {
        if (mmkv == null) mmkv = MMKV.defaultMMKV();
        return mmkv;
    }

    /**
     * 自定义 MMKV
     */
    public static void setMMKV(MMKV mmkv) {
        if (mmkv != null) MMKVUtils.mmkv = mmkv;
    }

    /**
     * 下方是boolean方法区域
     */
    public static boolean putBoolean(String key, boolean value) {
//        SharedPreferences.Editor editor = getMMKV().putBoolean(key, value);//一样的
//        return editor.commit();
        return getMMKV().encode(key, value);
    }

    /**
     * @param defaultValue 默认值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
//        return getMMKV().getBoolean(key, defaultValue);//一样的
        return getMMKV().decodeBool(key, defaultValue);
    }

    /**
     * 下方是int方法区域
     */
    public static boolean putInt(String key, int value) {
        return getMMKV().encode(key, value);
    }

    /**
     * 获取int
     * @return 如果没获取到, 默认返回0
     */
    public static int getInt(String key) {
        return getMMKV().decodeInt(key);
    }

    /**
     * 下方是long方法区域
     */
    public static boolean putLong(String key, long value) {
        return getMMKV().encode(key, value);
    }

    public static long getLong(String key) {
        return getMMKV().decodeLong(key);
    }

    /**
     * 下方是float方法区域
     */
    public static boolean putFloat(String key, float value) {
        return getMMKV().encode(key, value);
    }

    public static float getFloat(String key) {
        return getMMKV().decodeFloat(key);
    }

    /**
     * 下方是double方法区域
     */
    public static boolean putFloat(String key, double value) {
        return getMMKV().encode(key, value);
    }

    public static double getDouble(String key) {
        return getMMKV().decodeDouble(key);
    }

    /**
     * 下方是String方法区域
     */
    public static boolean putString(String key, @Nullable String value) {
        return getMMKV().encode(key, value);
    }

    public static String getString(String key) {
        return getMMKV().decodeString(key);
    }

    public static String getStringNoNull(String key) {
        String value = getString(key);
        if (value == null) return "";
        return value;
    }

    /**
     * 下方是Set<String>方法区域
     */
    public static boolean putStringSet(String key, Set<String> value) {
        if (value == null) return false;
        return getMMKV().encode(key, value);
    }

    public static Set<String> getStringSet(String key) {
        return getMMKV().decodeStringSet(key);
    }

    /**
     * 下方是byte[]方法区域
     */
    public static boolean putBytes(String key,  byte[] value) {
        return getMMKV().encode(key, value);
    }

    public static byte[] getBytes(String key) {
        return getMMKV().decodeBytes(key);
    }

    /**
     * 下方是Parcelable方法区域
     */
    public static boolean putParcelable(String key, Parcelable value) {
        if (value == null) return false;
        return getMMKV().encode(key, value);
    }

    public static <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        return getMMKV().decodeParcelable(key, tClass);
    }

    /**
     * 移除某个键所对应的值
     */
    public static void remove(String key) {
        getMMKV().removeValueForKey(key);
    }

    /**
     * 所有key
     */
    public static String[] allKeys() {
        return getMMKV().allKeys();
    }

    /**
     * 移除所有
     */
    public static void removeAll() {
        getMMKV().clearAll();
    }

    /**
     * 从 SharedPreferences 导入
     */
    public static int importFromSharedPreferences(SharedPreferences preferences) {
        return getMMKV().importFromSharedPreferences(preferences);
    }

    /**
     * ?
     */
    public static long count() {
        return getMMKV().count();
    }

    /**
     * ?
     */
    public static String cryptKey() {
        return getMMKV().cryptKey();
    }
}
