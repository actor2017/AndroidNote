package com.actor.myandroidframework.utils.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.actor.myandroidframework.utils.ConfigUtils;

/**
 * description: SQLiteDatabase 数据库工具类, 增删改查(crud)操作
 * author     : 李大发
 * date       : 2020/3/20 on 00:36
 *
 * @version 1.0
 */
public class SQLiteUtils {

    protected static String dbName = ConfigUtils.APPLICATION.getPackageName() + "_sqlitedatabase.db";
    protected static SQLiteDatabase.CursorFactory factory;
    public static int version = 1;

    protected static MySQLiteOpenHelper sqLiteOpenHelper;

    /**
     * 设置数据库, 可不设置, 有默认值
     * @param dbName 数据库名称
     * @param factory 用于创建游标对象，默认为空
     * @param version 数据库版本, 用于升级数据库时使用, 谨慎操作
     */
    public static void setSQLite(String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        if (dbName != null) SQLiteUtils.dbName = dbName;
        SQLiteUtils.factory = factory;
        SQLiteUtils.version = version;
    }

    public static MySQLiteOpenHelper getSqLiteOpenHelper() {
        if (sqLiteOpenHelper == null) {//懒汉模式
            synchronized (SQLiteUtils.class) {
                if (sqLiteOpenHelper == null) {
                    sqLiteOpenHelper = new MySQLiteOpenHelper(ConfigUtils.APPLICATION, dbName, factory, version);
                }
            }
        }
        return sqLiteOpenHelper;
    }

    /**
     * 插入String
     */
    public boolean insertString(String key, String packageName) {
        SQLiteDatabase database = getSqLiteOpenHelper().getWritableDatabase();

        //拼接要插入的参数
        ContentValues values = new ContentValues();
        values.put("package", packageName);

        //执行插入操作, 返回新插入的行id, 如果-1表示失败
        long insert = database.insert("applock", null, values);

        //关闭数据库
        database.close();

//        if(insert==-1) {
//            return false;
//        }else {
//            return true;
//        }
        return insert != -1;
    }

    /**
     * 删除数据库
     */
    public boolean delete(String packageName) {
        //获取数据库对象
        SQLiteDatabase database = getSqLiteOpenHelper().getWritableDatabase();

        //返回值代表删除后影响的行数, 如果是0,表示没有删除任何数据
        int delete = database.delete("applock", "package=?", new String[]{packageName});

        database.close();

        return delete != 0;
    }

    //查询某个app是否已加锁
    //当程序锁页面和服务同时调用此方法时, 可能导致某一次查询失败, 程序崩溃
    //为了避免并发调用此方法, 可以加synchronized关键词, 同时只允许有一次查询

    /**
     * 查询数据库
     */
    public synchronized boolean find(String packageName) {
        SQLiteDatabase database = getSqLiteOpenHelper().getWritableDatabase();

        Cursor cursor = database.query("applock", null, "package=?", new
                String[]{packageName}, null, null, null);

        boolean exist = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //return true;
                exist = true;
            }
            cursor.close();
        }

        database.close();

        return exist;
    }
}
