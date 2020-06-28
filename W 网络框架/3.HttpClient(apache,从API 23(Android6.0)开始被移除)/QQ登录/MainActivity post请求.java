package com.itheima.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
			String path = "http://192.168.13.66:8080/web/LoginServlet";
			
			// 1. 打开浏览器
			HttpClient client =  new DefaultHttpClient();
			
			// 2. 输入网址
			HttpPost httpPost = new HttpPost(path);
			
			//创建合集
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("qq", qq));
			parameters.add(new BasicNameValuePair("pwd",pwd));
			
			//经过URLform表单编码
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters,"utf-8");
			//设置到请求协议中
			httpPost.setEntity(urlEncodedFormEntity);
			
			// 3. 敲回车
			HttpResponse response = client.execute(httpPost);
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
