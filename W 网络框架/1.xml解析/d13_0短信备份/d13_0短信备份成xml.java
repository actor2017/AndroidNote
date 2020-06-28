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

//需要添加读写短信的权限
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 把系统短信备份到SD卡中
	 * 1. 读取系统短信
	 * 2. 生成xml文件
	 * 3. 存储到SD卡
	 */
	public void onekeybak(View v){
		// 1. 获取内容解析器
		ContentResolver resolver = getContentResolver();
		// 2. 指定URI
		Uri uri = Uri.parse("content://sms/");
		// 3. 查询
		Cursor cursor = resolver.query(uri, new String[]{"address","date","read","type","body"}, null,null,null);
		try {
			//生成xml文件
			XmlSerializer serializer = Xml.newSerializer();
			FileOutputStream fos = new FileOutputStream(new File("/mnt/sdcard/sms.xml"));
			serializer.setOutput(fos, "utf-8");
			//xml文档的头
			serializer.startDocument("utf-8", true);
			//跟标签
			serializer.startTag(null, "smss");
			while(cursor.moveToNext()){
				String address = cursor.getString(0);
				String date = cursor.getString(1);
				String read = cursor.getString(2);
				String type = cursor.getString(3);
				String body = cursor.getString(4);
				//每条短信
				serializer.startTag(null, "sms");

				//手机号码
				serializer.startTag(null, "address");
				serializer.text(address);
				serializer.endTag(null, "address");
				
				//时间
				serializer.startTag(null, "date");
				serializer.text(date);
				serializer.endTag(null, "date");
				//阅读状态
				serializer.startTag(null, "read");
				serializer.text(read);
				serializer.endTag(null, "read");
				//收发状态
				serializer.startTag(null, "type");
				serializer.text(type);
				serializer.endTag(null, "type");
				//短信内容
				serializer.startTag(null, "body");
				serializer.text(body);
				serializer.endTag(null, "body");

				serializer.endTag(null, "sms");
			}
			serializer.endTag(null, "smss");
			serializer.endDocument();
			
			fos.close();
			Toast.makeText(this, "备份成功 /mnt/sdcard/sms.xml 短信神器好流弊啊", 0).show();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "短信神器一般", 0).show();
		}
	}
}
