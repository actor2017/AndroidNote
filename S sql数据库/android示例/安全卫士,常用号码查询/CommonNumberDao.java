package cn.itcast.mobilesafe12.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Kevin.
 *
 * 常用号码数据库封装
 */

public class CommonNumberDao {

    public static ArrayList<GroupInfo> getCommonNumber(Context ctx) {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(ctx.getFilesDir()
                        .getAbsolutePath() + "/commonnum.db", null,
                SQLiteDatabase.OPEN_READONLY);

        //classlist:表名,列出了组布局
        Cursor cursor = database.query("classlist", new String[]{"name", "idx"}, null, null,
                null, null, null);

        ArrayList<GroupInfo> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                GroupInfo info = new GroupInfo();
                String name = cursor.getString(0);
                String idx = cursor.getString(1);

                info.name = name;
                info.idx = idx;
                info.childList = getChildNumber(idx, database);

                list.add(info);
            }
            cursor.close();
        }
        database.close();
        return list;
    }

    //返回某个组的所有电话号码
    private static ArrayList<ChildInfo> getChildNumber(String idx, SQLiteDatabase database) {

        Cursor cursor = database.query("table" + idx, new String[]{"number", "name"}, null, null,
                null, null, null);

        ArrayList<ChildInfo> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ChildInfo info = new ChildInfo();
                String number = cursor.getString(0);
                String name = cursor.getString(1);

                info.name = name;
                info.number = number;

                list.add(info);
            }
            cursor.close();
        }

        //不能在此处关闭数据库, 否则导致后续查询会失败
        //database.close();

        return list;
    }

   public static class GroupInfo {
        public String name;
        public String idx;
        public ArrayList<ChildInfo> childList;//当前组的所有号码
    }

    public static class ChildInfo {
        public String name;
        public String number;
    }
}
