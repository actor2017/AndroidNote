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
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI,content �̶�д��
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao");
		// 3. ��������
		ContentValues values = new ContentValues();
		values.put("name", "���");
		values.put("money", 1);
		Uri id = resolver.insert(uri, values);//�������ݿ�id
		Toast.makeText(this, "id:" + id.toString(), 0).show();
	}
	
	/**
	 * ��ѯ01�������ݿ��������
	 */
	public void query(View v) {
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/query");
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String money = cursor.getString(cursor.getColumnIndex("money"));
			System.out.println("name:" + name + " money:" + money);
		}
	}

	/**
	 * �޸�01�������ݿ������
	 */
	public void update(View v) {
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/account");
		ContentValues values = new ContentValues();
		values.put("money", 100000000);
		int count = resolver.update(uri, values, "_id=?", new String[]{"1"});//�޸��˼�������
		Toast.makeText(this, "count:"+count, 0).show();
	}
	/**
	 * ɾ��01�������ݿ������
	 */
	public void delete(View v) {
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/account");
		int count = resolver.delete(uri, null, null);
		Toast.makeText(this, "count:"+count, 0).show();
	}

	/**
	 * ����01�������ݿ��еĺ�������
	 */
	public void insert1(View v) {
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/black");
		// 3. ��������
		ContentValues values = new ContentValues();
		values.put("name", "����");
		values.put("money", 1);
		Uri id = resolver.insert(uri, values);//�������ݿ�id
		Toast.makeText(this, "id:" + id.toString(), 0).show();
	}
}
