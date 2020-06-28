package com.heima.netpage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.tv);
		tv.setText("http://192.168.0.114:8080/day01_1/images/test.xml");
		Button btn = (Button) findViewById(R.id.btn);
		et = (EditText) findViewById(R.id.et);//获取控件
		
		//设置监听
		btn.setOnClickListener(new OnClickListener() {
			
			private String string;

			@Override
			public void onClick(View v) {
				string = et.getText().toString();
				if (string == null || !string.startsWith("http://")) {
					Toast.makeText(MainActivity.this, "请输入正确的网址", 0).show();
					return;
				}
				
				//开启线程
				new Thread(){
					public void run() {
						requestNetWork();
					}
				}.run();
			}
		});


//		 * 2. 在子线程中发起网络通信 
//		 * 3. 获取服务器返回的二进制输入流 
//		 * 4. 把流转成字符串 5.
//		 * 用Handler消息机制，把字符串显示在textview上
	}
	
	/**
	 * 在子线程中发起网络通信
	 */
	private void requestNetWork() {
		// TODO Auto-generated method stub
		try {
			// 1. 写一个URL
			URL url = new URL(et.getText().toString());
			// 2. 用这个URL打开连接
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 3. 设置请求参数
				conn.setRequestMethod("get");
				//设置连接时间
				conn.setConnectTimeout(3000);
				// 4. 获取服务器返回的响应状态码
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					//获取服务器返回的二进制输入流
					InputStream inputStream = conn.getInputStream();
					//把流转换成字符串,下面是自定义类.方法
					final String text = StringUtils.parseStream2String(inputStream);
					
					runOnUiThread(new Runnable() {
						
						@Override
						//运行在主线程
						public void run() {
							// TODO Auto-generated method stub
							tv.setText(text);
						}
					});
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(MainActivity.this, "地址不正确", 0).show();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
}
class StringUtils{

	public static String parseStream2String(InputStream inputStream) {
		// TODO Auto-generated method stub
		//获取内存输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int len = -1;
		byte[] b = new byte[1024*8];
		try {
			while ((len = inputStream.read(b)) != -1) {
				baos.write(b, 0, len);			//内存输出流特殊写法,应该是写到内存中?
			}
			baos.close();
			return baos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
