package com.google.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 1. 写一个类继承系统的ContentProvider
 * 
 * 内线
 */
public class BankProvider extends ContentProvider {

    private SQLiteDatabase db;
	private static final int URI_INSERT = 0;
	private static final int URI_QUERY = 1;
	private static final int URI_ACCOUNT = 2;
	private static final int URI_BLACK = 3;
	
	//3. 暗号
	private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	//添加URI匹配规则,URI:指向这个应用的某个资源(数据库,图片...)
	static{
		//URI中的 path:bao.ta.zhen.he.yao,这种写法极不规范
		matcher.addURI("tian.wang.gai.di.hu", "bao.ta.zhen.he.yao", URI_INSERT);
		
		//URI中的 path:query,这种方法不推荐
		matcher.addURI("tian.wang.gai.di.hu", "query", URI_QUERY);
		
		//URI中的 path:account 表名 （推荐使用,源码也是这么写的）
		matcher.addURI("tian.wang.gai.di.hu", "account", URI_ACCOUNT);
		
		//URI中的 path:blcak 表名 （推荐使用）
		matcher.addURI("tian.wang.gai.di.hu", "black", URI_BLACK);
	}
	/**
	 * 初始化数据库
	 */
	@Override
	public boolean onCreate() {
		BankDbOpenHelper helper = new BankDbOpenHelper(getContext());
		db = helper.getWritableDatabase();
		return false;
	}

	/**
	 * uri 	:content://tian.wang.gai.di.hu/insert(固定content开头)
	 * values:要给数据插入的值
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int code = matcher.match(uri);
		switch(code) {
			case URI_INSERT: //z账户表
			    long id = db.insert("account", null, values);//account:表名
			    return Uri.parse("id="+id);
			case URI_BLACK://黑名单
			    long id = db.insert("black", null, values);
			    return Uri.parse("id="+id);
			default:
			    throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int code = matcher.match(uri);
		switch(code) {
			case URI_QUERY:
			    Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			    return cursor;
			case URI_BLACK://黑名单
			    Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			    return cursor;
			default:
			    throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_ACCOUNT) {
			int count = db.update("account", values, selection, selectionArgs);
			return count;
		}else if (code == URI_BLACK) {//黑名单
			int count = db.update("account", values, selection, selectionArgs);
			return count;
		}else {
			throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_ACCOUNT) {
			int count = db.delete("account", selection, selectionArgs);
			return count;
		}else if (code == URI_BLACK) {//黑名单
			int count = db.delete("account", selection, selectionArgs);
			return count;
		}else {
			throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}

	//添加URI匹配规则  path:account/3 删除第3行数据
	matcher.addURI("tian.wang.gai.di.hu", "account/3", URI_ACCOUNT);
	//添加URI匹配规则  path:account/# 删除某一条数据,示例#传入id
	matcher.addURI("tian.wang.gai.di.hu", "account/#", URI_ACCOUNT_ID);
	/**
	 * 匹配单条/多条数据,这种写法麻烦...
	 * vnd.android.cursor.item 单条记录
     * vnd.android.cursor.dir/ 多条记录
	 */
	@Override
	public String getType(Uri uri) {
		return null;
	}
}
