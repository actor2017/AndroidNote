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
		et = (EditText) findViewById(R.id.et);//��ȡ�ؼ�
		
		//���ü���
		btn.setOnClickListener(new OnClickListener() {
			
			private String string;

			@Override
			public void onClick(View v) {
				string = et.getText().toString();
				if (string == null || !string.startsWith("http://")) {
					Toast.makeText(MainActivity.this, "��������ȷ����ַ", 0).show();
					return;
				}
				
				//�����߳�
				new Thread(){
					public void run() {
						requestNetWork();
					}
				}.run();
			}
		});


//		 * 2. �����߳��з�������ͨ�� 
//		 * 3. ��ȡ���������صĶ����������� 
//		 * 4. ����ת���ַ��� 5.
//		 * ��Handler��Ϣ���ƣ����ַ�����ʾ��textview��
	}
	
	/**
	 * �����߳��з�������ͨ��
	 */
	private void requestNetWork() {
		// TODO Auto-generated method stub
		try {
			// 1. дһ��URL
			URL url = new URL(et.getText().toString());
			// 2. �����URL������
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 3. �����������
				conn.setRequestMethod("get");
				//��������ʱ��
				conn.setConnectTimeout(3000);
				// 4. ��ȡ���������ص���Ӧ״̬��
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					//��ȡ���������صĶ�����������
					InputStream inputStream = conn.getInputStream();
					//����ת�����ַ���,�������Զ�����.����
					final String text = StringUtils.parseStream2String(inputStream);
					
					runOnUiThread(new Runnable() {
						
						@Override
						//���������߳�
						public void run() {
							// TODO Auto-generated method stub
							tv.setText(text);
						}
					});
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(MainActivity.this, "��ַ����ȷ", 0).show();
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
		//��ȡ�ڴ������
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int len = -1;
		byte[] b = new byte[1024*8];
		try {
			while ((len = inputStream.read(b)) != -1) {
				baos.write(b, 0, len);			//�ڴ����������д��,Ӧ����д���ڴ���?
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
