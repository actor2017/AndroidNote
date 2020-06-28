package com.itheima.qq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
		String path = "http://192.168.13.66:8080/web/LoginServlet?" +
				"qq="+qq+"&pwd="+pwd;
		//1. 创建asynchttpclicent客户端对象
		AsyncHttpClient client = new AsyncHttpClient();
		//2. 调用get的方法发起get请求
		client.get(path, new MyNetWorkListener());
		
	}

	private class MyNetWorkListener extends AsyncHttpResponseHandler {
		//当成功时回调
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			Toast.makeText(MainActivity.this, new String(responseBody), 0).show();
		}

		//当失败时回调
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			Toast.makeText(MainActivity.this, "code:"+statusCode, 0).show();
		}
	}
}
