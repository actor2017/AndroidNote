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
		// 1. ��������
		HttpUtils httpUtils = new HttpUtils();
		// 2. �����صķ���
		httpUtils.download(
				path, 	//Ҫ������Դ��·��
				"/mnt/sdcard/hehe.exe",//�����ļ��洢��λ�� 
				false,	//�Ƿ�Ҫ�ϵ� 
				new MyDownLoadListener());//�����ļ��ļ���
	}
	
	private class MyDownLoadListener extends RequestCallBack<File> {
		//���صĹ����лص�
		//total���ļ����ܴ�С
		//current���������ļ��Ĵ�С
		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			super.onLoading(total, current, isUploading);
			pb.setMax((int)total);
			pb.setProgress((int)current);
			
			int percent = (int)(current*100/total);
			tv.setText(percent+"%");
		}
		/**
		 * ��ʼ����
		 */
		@Override
		public void onStart() {
			super.onStart();
			Toast.makeText(MainActivity.this, "��ʼ����", 0).show();
		}
		//���سɹ���ص�
		@Override
		public void onSuccess(ResponseInfo<File> info) {
			String filePath = info.result.getAbsolutePath();
			Toast.makeText(MainActivity.this, "���سɹ���"+filePath, 0).show();
		}
		//����ʧ�ܺ�ص�
		@Override
		public void onFailure(HttpException arg0, String arg1) {
			Toast.makeText(MainActivity.this, "����ʧ��", 0).show();
		}
	}

}
