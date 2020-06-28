package cn.itcast.mobilesafe12.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.itcast.mobilesafe12.db.AppLockOpenHelper;

/**
 * Created by Kevin.
 * 程序锁数据库增删改查(crud)操作
 * <p>
 * 单例设计模式-->23个设计模式
 * <p>
 * 单例模式, 观察者模式, 工厂模式, 适配器模式
 * <p>
 * <p>
 * 1. 私有的构造方法
 * 2. 公开一个static的方法来获取实例对象
 * 3. 创建对象: 懒汉模式, 饿汉模式
 * 4. 懒汉模式: 线程安全问题
 */

public class AppLockDao {

    private final AppLockOpenHelper mHelper;

    //private static BlackNumberDao mInstance = new BlackNumberDao(); 饿汉模式
    private static AppLockDao mInstance = null;//懒汉模式

    private AppLockDao(Context ctx) {
        mHelper = new AppLockOpenHelper(ctx);
    }

    //A, B, C
    public static AppLockDao getInstance(Context ctx) {
        //懒汉模式
        //A, B, C
        if (mInstance == null) {
            //A, B, C
            synchronized (AppLockDao.class) {
                //B, C
                if (mInstance == null) {
                    //A
                    mInstance = new AppLockDao(ctx);
                }
            }
        }

        return mInstance;
    }

    /**
     * 增加数据库
     */
    public boolean add(String packageName) {
        //获取数据库对象
        SQLiteDatabase database = mHelper.getWritableDatabase();

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
        SQLiteDatabase database = mHelper.getWritableDatabase();

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
        SQLiteDatabase database = mHelper.getWritableDatabase();

        Cursor cursor = database.query("applock", null, "package=?", new
                String[]{packageName}, null, null, null);

        boolean exist = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //return true;
                exist = true;
            }
        }

        cursor.close();
        database.close();

        return exist;
    }

}
