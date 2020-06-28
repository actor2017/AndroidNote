package com.heima.mobilesafe_work.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Description: 本类是查询小米地址数据库的,使用前需{先判空}再把手机号码传入本类,返回地址值
 * 归属地查询数据库
 *
 * 1.和activity同层目录新建文件夹db.dao
 * 2.在这个文件夹下新建AddressDao.class
 *
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/1/19 on 11:15.
 */

public class AddressDao {
    public static String getAddress(Context con, String phoneNum) {

        //直接打开本地数据库文件的方法
        //参1: 数据库文件的本地路径; 参2: null; 参3:以只读方式打开
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(con.getFilesDir()
                .getAbsolutePath() + "/address.db", null, SQLiteDatabase.OPEN_READONLY);

        //如果不是以0开头,且位数在1-8位
        if (!phoneNum.startsWith("0")) {
            switch (phoneNum.length()) {
                case 3:
                    return "报警电话";
                case 4:
                    return "模拟器电话";
                case 5:
                    return "客服电话";
                case 7:
                case 8:
                    return "本地电话";
            }
        }
        //手机号码
        if (phoneNum.matches("^1[3-8]\\d{9}$")) {
            //                             rawQuery(String sql, String[] selectionArgs)
            Cursor cursor = sqLiteDatabase.rawQuery("select location from data2 where id=(select " +
                    "outkey from data1 where id=?)", new String[]{phoneNum.substring(0, 7)});
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    //cursor.close();不能先关了游标再返回,会报错
                    String address =cursor.getString(0);
                    cursor.close();
                    return address;
                }
            }
        }
        //长途电话,10-12位????????????????????????????????????????????????????????????????????????
        else if (phoneNum.matches("^0[1-9]\\d{8,10}")) {
            //先查询4位区号(数据库中没0开头),例select location from data2 where area=999
            Cursor cursor = sqLiteDatabase.rawQuery("select location from data2 where area=?",
                    new String[]{phoneNum.substring(1, 4)});
            if (cursor != null) {
                //找到了
                if (cursor.moveToFirst()) {
                    String string = cursor.getString(0);
                    cursor.close();
                    return string.substring(0,string.length()-2)+"电话";//不返回运营商,例:北京电话(电信)
                }
            }
            //如果4位没找到,就找3位的区号
            Cursor cursor3 = sqLiteDatabase.rawQuery("select location from data2 where area=?",
                    new String[]{phoneNum.substring(1, 3)});
            if (cursor3 != null) {
                //找到了
                if (cursor3.moveToFirst()) {
                    String string = cursor3.getString(0);
                    cursor3.close();
                    return string.substring(0,string.length()-2)+"电话";//不返回运营商,例:北京电话(电信)
                }
            }
        }
        return "未知号码";
    }
}
