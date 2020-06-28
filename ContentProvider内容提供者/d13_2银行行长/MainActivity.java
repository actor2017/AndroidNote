package com.itheima.bankboss;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void insert(View v) {
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI,content 固定写法
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao");
		// 3. 插入数据
		ContentValues values = new ContentValues();
		values.put("name", "凤凤");
		values.put("money", 1);
		Uri id = resolver.insert(uri, values);//返回数据库id
		Toast.makeText(this, "id:" + id.toString(), 0).show();
	}
	
	/**
	 * 查询01银行数据库里的数据
	 */
	public void query(View v) {
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/query");
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String money = cursor.getString(cursor.getColumnIndex("money"));
			System.out.println("name:" + name + " money:" + money);
		}
	}

	/**
	 * 修改01银行数据库的数据
	 */
	public void update(View v) {
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/account");
		ContentValues values = new ContentValues();
		values.put("money", 100000000);
		int count = resolver.update(uri, values, "_id=?", new String[]{"1"});//修改了几条数据
		Toast.makeText(this, "count:"+count, 0).show();
	}
	/**
	 * 删除01银行数据库的数据
	 */
	public void delete(View v) {
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/account");
		int count = resolver.delete(uri, null, null);
		Toast.makeText(this, "count:"+count, 0).show();
	}

	/**
	 * 操作01银行数据库中的黑名单表
	 */
	public void insert1(View v) {
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/black");
		// 3. 插入数据
		ContentValues values = new ContentValues();
		values.put("name", "狗蛋");
		values.put("money", 1);
		Uri id = resolver.insert(uri, values);//返回数据库id
		Toast.makeText(this, "id:" + id.toString(), 0).show();
	}
}
