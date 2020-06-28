package com.kuchuanyun.cpptest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class BankProvider extends ContentProvider {
    public BankProvider() {
    }

    /**
     * 运行在主线程
     * 一般用于创建数据库或者升级等操作，只有在 ContentResolver 访问的时候，才会触发onCreate方法
     * @return ContentProvider初始化成功/失败
     */
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    /**
     * 运行在Binder线程
     * 插入一条新数据
     * @param uri 根据uri插入到具体哪张数据表
     * @param values  ContentValues底层是HashMap<String, Object>(8)
     *                key：表示列名，value：表示行名，如果value为空，在表中则是空行，无内容
     * @return 添加成功后，返回这条新数据的uri
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 运行在Binder线程
     * 删除数据
     * @param uri  根据uri删除哪张表的数据
     * @param selection 根据条件删除具体哪行数据
     * @param selectionArgs 与selection类似
     * @return  返回被删除的行数。
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 运行在Binder线程
     * 更新数据
     * @param uri 根据Uri修改具体哪张表的数据
     * @param values 与insert的ContentValues一样，key是列名，若传入的value为空，则会删除原来的数据置空。
     * @param selection 选择符合该条件的行数据进行修改
     * @param selectionArgs 与selection类似
     * @return 更新的行数
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 运行在Binder线程
     * 查询数据
     * @param uri 根据uri查询具体哪张数据表
     * @param projection 确定查询表中哪些列 ，传null则返回所有的列
     * @param selection 确定查询哪行，传null这返回所有的行
     * @param selectionArgs 与selection类似
     * @param sortOrder 用于对查询结果进行排序，传null则使用默认的排序方式，也可以是无序的。
     * @return 查询的返回值，是个Cursor对象，在取完数据需进行关闭。否则会内存泄漏
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 返回指定内容URL的MIME类型
     * @param uri 具体Url
     * @return MIME类型 比如图片、视频等等，可直接返回null
     */
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
