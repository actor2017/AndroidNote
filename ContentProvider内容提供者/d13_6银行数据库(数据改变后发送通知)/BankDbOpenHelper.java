package com.itheima.bank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BankDbOpenHelper extends SQLiteOpenHelper {

	/**
	 * ����
	 * 
	 * @param context
	 *            ������
	 * @param name
	 *            ���ݿ�����
	 * @param factory
	 *            Ĭ�ϵ��α깤�� null
	 * @param version
	 *            ���ݿ�İ汾 >=1
	 */
	public BankDbOpenHelper(Context context) {
		super(context,"bank.db",null,2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table account (_id integer primary key autoincrement,name varchar(20),money varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("create table black (_id integer primary key autoincrement,name varchar(20),money varchar(20))");
		
	}

}
