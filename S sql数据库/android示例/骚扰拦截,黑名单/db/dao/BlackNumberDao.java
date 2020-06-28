package cn.itcast.mobilesafe12.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cn.itcast.mobilesafe12.bean.BlackNumberInfo;
import cn.itcast.mobilesafe12.db.BlackNumberOpenHelper;

/**
 * Created by Kevin.
 * 黑名单数据库增删改查(crud)操作
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

public class BlackNumberDao {

    private final BlackNumberOpenHelper mHelper;

    //private static BlackNumberDao mInstance = new BlackNumberDao(); 饿汉模式
    private static BlackNumberDao mInstance = null;//懒汉模式

    private BlackNumberDao(Context ctx) {
        mHelper = new BlackNumberOpenHelper(ctx);
    }

    //A, B, C
    public static BlackNumberDao getInstance(Context ctx) {
        //懒汉模式
        //A, B, C
        if (mInstance == null) {
            //A, B, C
            synchronized (BlackNumberDao.class) {
                //B, C
                if (mInstance == null) {
                    //A
                    mInstance = new BlackNumberDao(ctx);
                }
            }
        }

        return mInstance;
    }

    public boolean add(String number, int mode) {
        //获取数据库对象
        SQLiteDatabase database = mHelper.getWritableDatabase();

        //拼接要插入的参数
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("mode", mode);

        //执行插入操作, 返回新插入的行id, 如果-1表示失败
        long insert = database.insert("blacknumber", null, values);

        //关闭数据库
        database.close();

//        if(insert==-1) {
//            return false;
//        }else {
//            return true;
//        }
        return insert != -1;
    }

    public boolean delete(String number) {
        //获取数据库对象
        SQLiteDatabase database = mHelper.getWritableDatabase();

        //返回值代表删除后影响的行数, 如果是0,表示没有删除任何数据
        int delete = database.delete("blacknumber", "number=?", new String[]{number});

        database.close();

        return delete != 0;
    }

    public boolean update(String number, int mode) {
        SQLiteDatabase database = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("mode", mode);

        //返回值代表删除后影响的行数, 如果是0,表示没有更新任何数据
        int update = database.update("blacknumber", values, "number=?", new String[]{number});

        database.close();

        return update != 0;
    }

    //查询某个号码是否是黑名单
    public boolean find(String number) {
        SQLiteDatabase database = mHelper.getWritableDatabase();

        Cursor cursor = database.query("blacknumber", null, "number=?", new
                String[]{number}, null, null, null);

        boolean exist = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //查到了记录, 说明就是黑名单
                //return true;
                exist = true;
            }
        }

        cursor.close();
        database.close();

        return exist;
    }

    //查询某个号码的拦截模式
    public int findMode(String number) {
        SQLiteDatabase database = mHelper.getWritableDatabase();

        Cursor cursor = database.query("blacknumber", new String[]{"mode"}, "number=?", new
                String[]{number}, null, null, null);

        int mode = -1;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //查到了记录, 说明就是黑名单
                //return true;
                mode = cursor.getInt(0);
            }
        }

        cursor.close();
        database.close();

        return mode;
    }

    //查询所有黑名单集合
    public ArrayList<BlackNumberInfo> findAll() {
        SQLiteDatabase database = mHelper.getWritableDatabase();

        Cursor cursor = database.query("blacknumber", new String[]{"number", "mode"}, null,
                null, null, null, null);

        ArrayList<BlackNumberInfo> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                BlackNumberInfo info = new BlackNumberInfo();

                String number = cursor.getString(0);
                int mode = cursor.getInt(1);

                info.number = number;
                info.mode = mode;

                list.add(info);
            }

            cursor.close();
        }

        database.close();

        return list;
    }

    //分页查询
    //index: 起始条目的位置, 从哪一条数据开始查
    //select * from blacknumber limit 0,20
    public ArrayList<BlackNumberInfo> findPart(int index) {
        SQLiteDatabase database = mHelper.getWritableDatabase();

        //每一页20条数据
        //limit 0, 20; 0表示从第0条开始查, 20表示查询20条数据
        //order by _id desc: 根据_id逆序排列
        Cursor cursor = database.rawQuery("select number,mode from blacknumber order by _id desc " +
                "limit ?,20", new
                String[]{index + ""});

        ArrayList<BlackNumberInfo> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                BlackNumberInfo info = new BlackNumberInfo();

                String number = cursor.getString(0);
                int mode = cursor.getInt(1);

                info.number = number;
                info.mode = mode;

                list.add(info);
            }

            cursor.close();
        }

        database.close();

        return list;
    }

    //获取总数
    public int getTotalCount() {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select count(*) from blacknumber", null);
        //select count(*) from blacknumber
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }

            cursor.close();
        }

        database.close();

        return count;
    }

}
