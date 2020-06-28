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
import com.loopj.android.http.RequestParams;

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
		// �ҵ��ؼ�
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etQQ = (EditText) findViewById(R.id.et_qq);
		
	}
  
	public void gelogin(View view) {
		System.out.println("��½��������");
		//��ȡ�û������QQ���������
		pwd = etPwd.getText().toString().trim();
		qq = etQQ.getText().toString().trim();
		//���ǿ��ж�
		if (TextUtils.isEmpty(qq)||TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "�ף�������QQ���������  :( ", 0).show();
			return;
		}
		String path = "http://192.168.13.66:8080/web/LoginServlet";
		//1. ����asynchttpclicent�ͻ��˶���
		AsyncHttpClient client = new AsyncHttpClient();
		//2. ����post�ķ�������post����
		RequestParams params = new RequestParams();
		params.put("qq", qq);
		params.put("pwd", pwd);
		client.post(path, params, new MyNetWorkListener());
		
	}
	/**
	 * ����ͨѶ����ļ���
	 */
	private class MyNetWorkListener extends AsyncHttpResponseHandler {
		//���ɹ�ʱ�ص�
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			Toast.makeText(MainActivity.this, new String(responseBody), 0).show();
		}

		//��ʧ��ʱ�ص�
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			Toast.makeText(MainActivity.this, "code:"+statusCode, 0).show();
		}
	}
}
