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
	 * 1. 获取用户输入的网址，做非空判断
	 * 2. 把要上传的文件提交到服务器端
	 * 		2.1 创建asynchttpclient客户端对象
	 * 		2.1 调用post方法上传
	 */
	public void upload(View v){
		String path = etPath.getText().toString().trim();
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(this, "请输入正确的网址", 0).show();
			return ;
		}
		// 创建asynchttpclient客户端对象
		AsyncHttpClient client = new AsyncHttpClient();
		// 调用post方法上传
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
		 * 上传了多少文件
		 * bytesWritten：已经上传的文件
		 * totalSize   ：文件的总大小
		 */
		@Override
		public void onProgress(int bytesWritten, int totalSize) {
			super.onProgress(bytesWritten, totalSize);
			//设置进度条的最大值
			pb.setMax(totalSize);
			//设置已上传文件的进度
			pb.setProgress(bytesWritten);
			
			//设置上传进度的百分比
			int percent = bytesWritten*100/totalSize;
			tvProgress.setText(percent+"%");
		}
		/**
		 * 开始上传文件
		 */
		@Override
		public void onStart() {
			super.onStart();
			Toast.makeText(MainActivity.this, "哥哥，我开始干活了！", 0).show();
		}
		/**
		 * 上传完毕
		 */
		@Override
		public void onFinish() {
			super.onFinish();
			Toast.makeText(MainActivity.this, "哥哥，我干完活了，哈哈哈", 0).show();
		}
		/**
		 * 上传成功
		 */
		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
			Toast.makeText(MainActivity.this, "上传成功喽", 0).show();
		}
		/**
		 * 失败
		 */
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			Toast.makeText(MainActivity.this, "statusCode："+statusCode, 0).show();
		}
	}
	
}
