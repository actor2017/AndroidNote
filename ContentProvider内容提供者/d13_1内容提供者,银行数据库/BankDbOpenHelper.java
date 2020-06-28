package com.itheima.bank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BankDbOpenHelper extends SQLiteOpenHelper {

    //d13_0内容提供者
	/**
	 * 构造
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            数据库名称
	 * @param factory
	 *            默认的游标工厂 null
	 * @param version
	 *            数据库的版本 >=1
	 */
	public BankDbOpenHelper(Context context) {
		super(context,"bank.db",null,2);//版本从1改成2,升级,onUpgrade()会调用
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table account (_id integer primary key autoincrement,name varchar(20),money varchar(20))");
	}

	//插入一条黑名单表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("create table black (_id integer primary key autoincrement,name varchar(20),money varchar(20))");
		
	}

}
