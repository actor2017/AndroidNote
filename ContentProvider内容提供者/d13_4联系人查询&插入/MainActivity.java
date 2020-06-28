package com.itheima.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//<uses-permission android:name="android.permission.READ_CONTACTS" />
//<uses-permission android:name="android.permission.WRITE_CONTACTS" />
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 查询通讯录 思路： 1. 查询raw_contacts表里有多少个联系人 2. 查询data表中每个联系人的数据 3.
	 * 根据mimetype区分数据类型
	 */
	public void read(View v) {
		// 查询raw_contacts表里有多少个联系人
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts/");
		// 3. 查询通讯录
		Cursor rawCursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);
		// 获取有多少个联系人
		while (rawCursor.moveToNext()) {
			String id = rawCursor.getString(0);
			Uri dataUri = Uri.parse("content://com.android.contacts/data/");
			// 查询data表中每个联系人的数据
			Cursor dataCursor = resolver
					.query(dataUri, new String[] { "data1", "mimetype" },
							"raw_contact_id=?", new String[] { id }, null);

			// 查看data表里所有的字段
			/*
			 * Cursor c = resolver.query(dataUri, null,null,null, null);
			 * String[] columnNames = c.getColumnNames(); for(String
			 * col:columnNames){ System.out.println(col); }
			 */

			// 获取每个联系人的所有信息
			while (dataCursor.moveToNext()) {
				String data1 = dataCursor.getString(0);
				// System.out.println(data1);
				// 根据mimetype区分数据类型
				String type = dataCursor.getString(1);
				// System.out.println(type);
				if ("vnd.android.cursor.item/phone_v2".equals(type)) {
					System.out.println("手机号：" + data1);
				} else if ("vnd.android.cursor.item/email_v2".equals(type)) {
					System.out.println("邮箱：" + data1);
				} else if ("vnd.android.cursor.item/name".equals(type)) {
					System.out.println("姓名：" + data1);
				}
				System.out.println("============");
			}
		}
	}

	/**
	 * 1. 查询raw_contacts表里有多少个联系人，获取最大的id 
	 * 2. 给这个id+1 
	 * 3. 把新的id插入raw_contacts表里 
	 * 4.根据id给data表插入联系人的数据
	 */
	public void add(View v) {
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts/");
		Cursor rawCursor = resolver.query(uri, new String[] { "_id" },//_id:避免数据被删除,返回null
				null, null, null);
		// 获取最大的id
		boolean last = rawCursor.moveToLast();
		int id = 0;// 联系人的id默认是没有联系人
		if (last) {
			id = rawCursor.getInt(0);
		}
		// 给这个id+1
		id++;
		// 把新的id插入raw_contacts表里
		ContentValues values = new ContentValues();
		values.put("contact_id", id);
		resolver.insert(uri, values);

		// 根据id给data表插入联系人的数据
		Uri dataUri = Uri.parse("content://com.android.contacts/data/");

		// 插入一个联系人信息的三个字段

		// 姓名
		values.clear();// 清空
		values.put("data1", "翠花");
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("raw_contact_id", id);
		resolver.insert(dataUri, values);

		// 邮箱
		values.clear();// 清空
		values.put("data1", "huahua@itcast.cn");
		values.put("raw_contact_id", id);
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		resolver.insert(dataUri, values);

		// 手机号码
		values.clear();// 清空
		values.put("data1", "13213562354");
		values.put("raw_contact_id", id);
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		Uri u = resolver.insert(dataUri, values);//插入3条数据,返回最后一条数据id
		
		Toast.makeText(this, u.toString(), 0).show();//12
	}
}
