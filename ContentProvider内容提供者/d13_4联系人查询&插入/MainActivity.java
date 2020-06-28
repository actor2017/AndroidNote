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
	 * ��ѯͨѶ¼ ˼·�� 1. ��ѯraw_contacts�����ж��ٸ���ϵ�� 2. ��ѯdata����ÿ����ϵ�˵����� 3.
	 * ����mimetype������������
	 */
	public void read(View v) {
		// ��ѯraw_contacts�����ж��ٸ���ϵ��
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts/");
		// 3. ��ѯͨѶ¼
		Cursor rawCursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);
		// ��ȡ�ж��ٸ���ϵ��
		while (rawCursor.moveToNext()) {
			String id = rawCursor.getString(0);
			Uri dataUri = Uri.parse("content://com.android.contacts/data/");
			// ��ѯdata����ÿ����ϵ�˵�����
			Cursor dataCursor = resolver
					.query(dataUri, new String[] { "data1", "mimetype" },
							"raw_contact_id=?", new String[] { id }, null);

			// �鿴data�������е��ֶ�
			/*
			 * Cursor c = resolver.query(dataUri, null,null,null, null);
			 * String[] columnNames = c.getColumnNames(); for(String
			 * col:columnNames){ System.out.println(col); }
			 */

			// ��ȡÿ����ϵ�˵�������Ϣ
			while (dataCursor.moveToNext()) {
				String data1 = dataCursor.getString(0);
				// System.out.println(data1);
				// ����mimetype������������
				String type = dataCursor.getString(1);
				// System.out.println(type);
				if ("vnd.android.cursor.item/phone_v2".equals(type)) {
					System.out.println("�ֻ��ţ�" + data1);
				} else if ("vnd.android.cursor.item/email_v2".equals(type)) {
					System.out.println("���䣺" + data1);
				} else if ("vnd.android.cursor.item/name".equals(type)) {
					System.out.println("������" + data1);
				}
				System.out.println("============");
			}
		}
	}

	/**
	 * 1. ��ѯraw_contacts�����ж��ٸ���ϵ�ˣ���ȡ����id 
	 * 2. �����id+1 
	 * 3. ���µ�id����raw_contacts���� 
	 * 4.����id��data�������ϵ�˵�����
	 */
	public void add(View v) {
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts/");
		Cursor rawCursor = resolver.query(uri, new String[] { "_id" },//_id:�������ݱ�ɾ��,����null
				null, null, null);
		// ��ȡ����id
		boolean last = rawCursor.moveToLast();
		int id = 0;// ��ϵ�˵�idĬ����û����ϵ��
		if (last) {
			id = rawCursor.getInt(0);
		}
		// �����id+1
		id++;
		// ���µ�id����raw_contacts����
		ContentValues values = new ContentValues();
		values.put("contact_id", id);
		resolver.insert(uri, values);

		// ����id��data�������ϵ�˵�����
		Uri dataUri = Uri.parse("content://com.android.contacts/data/");

		// ����һ����ϵ����Ϣ�������ֶ�

		// ����
		values.clear();// ���
		values.put("data1", "�仨");
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("raw_contact_id", id);
		resolver.insert(dataUri, values);

		// ����
		values.clear();// ���
		values.put("data1", "huahua@itcast.cn");
		values.put("raw_contact_id", id);
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		resolver.insert(dataUri, values);

		// �ֻ�����
		values.clear();// ���
		values.put("data1", "13213562354");
		values.put("raw_contact_id", id);
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		Uri u = resolver.insert(dataUri, values);//����3������,�������һ������id
		
		Toast.makeText(this, u.toString(), 0).show();//12
	}
}
