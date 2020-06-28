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
		// �ҵ��ؼ�
		etPwd = (EditText) findViewById(R.id.et_pwd);
		etQQ = (EditText) findViewById(R.id.et_qq);
	}
  
	/**
	 * 1. ��ȡ�û���������ݣ����ǿ��ж�
	 * 2. �����߳��з���http����
	 * 3. ��ȡ���������صĽ��
	 * 		�ѷ��������صĶ�����������ת���ַ�������˾����
	 */
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
		//�����߳�
		new Thread(){
			public void run() {
				requestNetWork();
			};
		}.start();
		
	}

	/**
	 * �����߳��з���http����
	 */
	protected void requestNetWork() {
		
		try {
			String path = "http://192.168.13.66:8080/web/LoginServlet";
			
			// 1. �������
			HttpClient client =  new DefaultHttpClient();
			
			// 2. ������ַ
			HttpPost httpPost = new HttpPost(path);
			
			//�����ϼ�
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("qq", qq));
			parameters.add(new BasicNameValuePair("pwd",pwd));
			
			//����URLform������
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters,"utf-8");
			//���õ�����Э����
			httpPost.setEntity(urlEncodedFormEntity);
			
			// 3. �ûس�
			HttpResponse response = client.execute(httpPost);
			//��ȡ��Ӧ��
			StatusLine statusLine = response.getStatusLine();
			//��ȡ��Ӧ״̬��
			int code = statusLine.getStatusCode();
			if (code == 200) {
				//��ȡ��Ӧ��
				HttpEntity entity = response.getEntity();
				//��ȡ���������صĶ�����������
				InputStream is = entity.getContent();
				String text = StringUtils.parseStream2String(is);
				
				showToastInAnyThread(text);
			}else {
				showToastInAnyThread("code:"+code);
			}
		} catch (Exception e) {
			e.printStackTrace();
			showToastInAnyThread("�����������");
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
