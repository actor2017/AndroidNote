package com.example.upload;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText	etPath;
	private TextView	tvProgress;
	private ProgressBar	pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etPath = (EditText) findViewById(R.id.et_path);
		tvProgress = (TextView) findViewById(R.id.tv_progress);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
	}

	/**
	 * 1. ��ȡ�û��������ַ�����ǿ��ж�
	 * 2. ��Ҫ�ϴ����ļ��ύ����������
	 * 		2.1 ����asynchttpclient�ͻ��˶���
	 * 		2.1 ����post�����ϴ�
	 */
	public void upload(View v){
		String path = etPath.getText().toString().trim();
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(this, "��������ȷ����ַ", 0).show();
			return ;
		}
		// ����asynchttpclient�ͻ��˶���
		AsyncHttpClient client = new AsyncHttpClient();
		// ����post�����ϴ�
		RequestParams params = new RequestParams();
		
		File file = new File("/mnt/sdcard/aa.mp4");
		try {
			params.put("file", file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		client.post(path, params, new MyUploadListener());
	}
	
	private class MyUploadListener extends AsyncHttpResponseHandler {
		/*
		 * �ϴ��˶����ļ�
		 * bytesWritten���Ѿ��ϴ����ļ�
		 * totalSize   ���ļ����ܴ�С
		 */
		@Override
		public void onProgress(int bytesWritten, int totalSize) {
			super.onProgress(bytesWritten, totalSize);
			//���ý����������ֵ
			pb.setMax(totalSize);
			//�������ϴ��ļ��Ľ���
			pb.setProgress(bytesWritten);
			
			//�����ϴ����ȵİٷֱ�
			int percent = bytesWritten*100/totalSize;
			tvProgress.setText(percent+"%");
		}
		/**
		 * ��ʼ�ϴ��ļ�
		 */
		@Override
		public void onStart() {
			super.onStart();
			Toast.makeText(MainActivity.this, "��磬�ҿ�ʼ�ɻ��ˣ�", 0).show();
		}
		/**
		 * �ϴ����
		 */
		@Override
		public void onFinish() {
			super.onFinish();
			Toast.makeText(MainActivity.this, "��磬�Ҹ�����ˣ�������", 0).show();
		}
		/**
		 * �ϴ��ɹ�
		 */
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			Toast.makeText(MainActivity.this, "�ϴ��ɹ��", 0).show();
		}
		/**
		 * ʧ��
		 */
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			Toast.makeText(MainActivity.this, "statusCode��"+statusCode, 0).show();
		}
	}
	
}
