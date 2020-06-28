package com.itheima.download;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText	etPath;
	private ProgressBar	pb;
	private TextView	tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etPath = (EditText) findViewById(R.id.et_path);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		tv = (TextView) findViewById(R.id.tv);	
	}

	public void start(View v){
		String path = etPath.getText().toString().trim();
		// 1. 创建对象
		HttpUtils httpUtils = new HttpUtils();
		// 2. 调下载的方法
		httpUtils.download(
				path, 	//要下载资源的路径
				"/mnt/sdcard/hehe.exe",//下载文件存储的位置 
				false,	//是否要断点 
				new MyDownLoadListener());//下载文件的监听
	}
	
	private class MyDownLoadListener extends RequestCallBack<File> {
		//下载的过程中回调
		//total：文件的总大小
		//current：已下载文件的大小
		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			super.onLoading(total, current, isUploading);
			pb.setMax((int)total);
			pb.setProgress((int)current);
			
			int percent = (int)(current*100/total);
			tv.setText(percent+"%");
		}
		/**
		 * 开始下载
		 */
		@Override
		public void onStart() {
			super.onStart();
			Toast.makeText(MainActivity.this, "开始下载", 0).show();
		}
		//下载成功后回调
		@Override
		public void onSuccess(ResponseInfo<File> info) {
			String filePath = info.result.getAbsolutePath();
			Toast.makeText(MainActivity.this, "下载成功："+filePath, 0).show();
		}
		//下载失败后回调
		@Override
		public void onFailure(HttpException arg0, String arg1) {
			Toast.makeText(MainActivity.this, "下载失败", 0).show();
		}
	}

}
