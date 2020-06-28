package com.itheima.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText	etPwd;
	private EditText	etQQ;
	private CheckBox	cbRem;
	private String	pwd;
	private String	qq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 找到控件
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etQQ = (EditText) findViewById(R.id.et_qq);
	}
  
	/**
	 * 1. 获取用户输入的数据，做非空判断
	 * 2. 在子线程中发起http请求
	 * 3. 获取服务器返回的结果
	 * 		把服务器返回的二进制输入流转成字符串用土司弹出
	 */
	public void gelogin(View view) {
		System.out.println("登陆啦。。。");
		//获取用户输入的QQ号码和密码
		pwd = etPwd.getText().toString().trim();
		qq = etQQ.getText().toString().trim();
		//做非空判断
		if (TextUtils.isEmpty(qq)||TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "亲，亲输入QQ号码和密码  :( ", 0).show();
			return;
		}
		//开启线程
		new Thread(){
			public void run() {
				requestNetWork();
			};
		}.start();
		
	}

	/**
	 * 在子线程中发起http请求
	 */
	protected void requestNetWork() {
		
		try {
			String path = "http://192.168.13.66:8080/web/LoginServlet?" +
					"qq="+URLEncoder.encode(qq, "utf-8")+"&pwd="+URLEncoder.encode(pwd, "utf-8");
			
			// 1. 打开浏览器
			HttpClient client =  new DefaultHttpClient();
			// 2. 输入网址
			HttpGet httpGet = new HttpGet(path);
			// 3. 敲回车
			HttpResponse response = client.execute(httpGet);
			//获取响应行
			StatusLine statusLine = response.getStatusLine();
			//获取响应状态码
			int code = statusLine.getStatusCode();
			if (code == 200) {
				//获取响应体
				HttpEntity entity = response.getEntity();
				//获取服务器返回的二进制输入流
				InputStream is = entity.getContent();
				String text = StringUtils.parseStream2String(is);
				
				showToastInAnyThread(text);
			}else {
				showToastInAnyThread("code:"+code);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showToastInAnyThread("网络出问题了");
		}
	}
	
	public void showToastInAnyThread(final String text){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, text, 0).show();
			}
		});
	}
}
