package com.itheima.sms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

//��Ҫ��Ӷ�д���ŵ�Ȩ��
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * ��ϵͳ���ű��ݵ�SD����
	 * 1. ��ȡϵͳ����
	 * 2. ����xml�ļ�
	 * 3. �洢��SD��
	 */
	public void onekeybak(View v){
		// 1. ��ȡ���ݽ�����
		ContentResolver resolver = getContentResolver();
		// 2. ָ��URI
		Uri uri = Uri.parse("content://sms/");
		// 3. ��ѯ
		Cursor cursor = resolver.query(uri, new String[]{"address","date","read","type","body"}, null,null,null);
		try {
			//����xml�ļ�
			XmlSerializer serializer = Xml.newSerializer();
			FileOutputStream fos = new FileOutputStream(new File("/mnt/sdcard/sms.xml"));
			serializer.setOutput(fos, "utf-8");
			//xml�ĵ���ͷ
			serializer.startDocument("utf-8", true);
			//����ǩ
			serializer.startTag(null, "smss");
			while(cursor.moveToNext()){
				String address = cursor.getString(0);
				String date = cursor.getString(1);
				String read = cursor.getString(2);
				String type = cursor.getString(3);
				String body = cursor.getString(4);
				//ÿ������
				serializer.startTag(null, "sms");

				//�ֻ�����
				serializer.startTag(null, "address");
				serializer.text(address);
				serializer.endTag(null, "address");
				
				//ʱ��
				serializer.startTag(null, "date");
				serializer.text(date);
				serializer.endTag(null, "date");
				//�Ķ�״̬
				serializer.startTag(null, "read");
				serializer.text(read);
				serializer.endTag(null, "read");
				//�շ�״̬
				serializer.startTag(null, "type");
				serializer.text(type);
				serializer.endTag(null, "type");
				//��������
				serializer.startTag(null, "body");
				serializer.text(body);
				serializer.endTag(null, "body");

				serializer.endTag(null, "sms");
			}
			serializer.endTag(null, "smss");
			serializer.endDocument();
			
			fos.close();
			Toast.makeText(this, "���ݳɹ� /mnt/sdcard/sms.xml �������������װ�", 0).show();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "��������һ��", 0).show();
		}
	}
}
