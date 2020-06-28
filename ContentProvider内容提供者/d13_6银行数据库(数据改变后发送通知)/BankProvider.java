package com.itheima.bank;

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
 * 双面间谍
 * //发通知，银行数据变化了,只有这1行代码改变
 * getContext().getContentResolver().notifyChange(uri, null);//null:通知所有观察者
 */
public class BankProvider extends ContentProvider {

	private static final int	URI_SUCC	= 0;
	private static final int	URI_SUCC_QUERY	= 1;
	private static final int	URI_SUCC_ACCOUNT	= 2;
	private static final int	URI_SUCC_BLACK	= 3;
	
	//3. 暗号
	static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	private SQLiteDatabase	db;
	static{
		//添加 匹配规则
		matcher.addURI("tian.wang.gai.di.hu", "bao.ta.zhen.he.yao", URI_SUCC);
		//添加URI匹配规则  path:query方法名
		matcher.addURI("tian.wang.gai.di.hu", "query", URI_SUCC_QUERY);
		
		//添加URI匹配规则  path:account 表名 （推荐使用）
		matcher.addURI("tian.wang.gai.di.hu", "account", URI_SUCC_ACCOUNT);
		//添加URI匹配规则  path:blcak 表名 （推荐使用）
		matcher.addURI("tian.wang.gai.di.hu", "black", URI_SUCC_BLACK);
		
		//添加URI匹配规则  path:account/3 表名 （推荐使用）
		matcher.addURI("tian.wang.gai.di.hu", "account/3", URI_SUCC_ACCOUNT);
		//添加URI匹配规则  path:account/# 表名 （推荐使用）
		matcher.addURI("tian.wang.gai.di.hu", "account/#", URI_SUCC_ACCOUNT);
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

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int code = matcher.match(uri);
		if (code == URI_SUCC_QUERY) {
			Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			//发通知，银行数据变化了
			getContext().getContentResolver().notifyChange(uri, null);
			return cursor;
		}else if (code == URI_SUCC_BLACK) {//黑名单
			Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
			return cursor;
		}else {
			throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}

	/**
	 * 4. 实现增删改查方法
	 * uri 		:    content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao
	 * values	:	 要给数据插入的值
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int code = matcher.match(uri);
		if(code == URI_SUCC){//账户表
			long id = db.insert("account", null, values);
			
			//发通知，银行数据变化了
			getContext().getContentResolver().notifyChange(uri, null);
			
			return Uri.parse("id="+id);
		}else if (code == URI_SUCC_BLACK) {//黑名单
			long id = db.insert("black", null, values);
			return Uri.parse("id="+id);
		}else {
			throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_SUCC_ACCOUNT) {
			int id = db.delete("account", selection, selectionArgs);
			//发通知，银行数据变化了
			getContext().getContentResolver().notifyChange(uri, null);
			return id;
		}else if (code == URI_SUCC_BLACK) {//黑名单
			int id = db.delete("account", selection, selectionArgs);
			return id;
		}else {
			throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int code = matcher.match(uri);
		if (code == URI_SUCC_ACCOUNT) {
			int id = db.update("account", values, selection, selectionArgs);
			//发通知，银行数据变化了
			getContext().getContentResolver().notifyChange(uri, null);
			return id;
		}else if (code == URI_SUCC_BLACK) {//黑名单
			int id = db.update("account", values, selection, selectionArgs);
			return id;
		}else {
			throw new IllegalArgumentException("根据相关法律规定，您无权操作银行数据库！");
		}
	}

	/**
	 * vnd.android.cursor.item 单条记录
		vnd.android.cursor.dir/ 多条记录
	 */
	@Override
	public String getType(Uri uri) {
		return null;
	}
}
