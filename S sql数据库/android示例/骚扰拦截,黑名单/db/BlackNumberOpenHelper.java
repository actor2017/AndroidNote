package com.heima.mobilesafe_work.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevin.
 * 创建黑名单数据库,创建在data/data/包名/databases/
 */

public class BlackNumberOpenHelper extends SQLiteOpenHelper {

    //有参构造
    public BlackNumberOpenHelper(Context context) {
        //blacknumber.db:数据库表名
        super(context, "blacknumber.db", null, 1);//1:版本号
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表: _id, number: 电话号码,  mode: 拦截模式, 0(拦截电话),1(拦截短信),2(拦截全部)
        String sql = "create table blacknumber(_id integer primary key autoincrement, number " +
                "varchar(30), mode integer)";

        //执行sql语句
        db.execSQL(sql);
    }

    //升级的时候回调
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
