package com.itheima.multidownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private LinearLayout	llContent;
	private EditText	etThreadCount;
	private EditText	etPath;
	
	//Ĭ���߳���λ3
	int threadCount = 3;
	private String	path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etPath = (EditText) findViewById(R.id.et_path);
		etThreadCount = (EditText) findViewById(R.id.et_thread_count);
		llContent = (LinearLayout) findViewById(R.id.ll_content);
		
	}

	//����¼�,��ʼ����
	public void download(View v){
		path = etPath.getText().toString().trim();
		String count = etThreadCount.getText().toString().trim();
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(this, "��������ȷ����ַ", 0).show();
			return;
		}
		//�ж��û��Ƿ������߳�����
		if (!TextUtils.isEmpty(count)) {
			threadCount = Integer.valueOf(count);
		}
		//���֮ǰ������еĽ�����
		llContent.removeAllViews();
		
		for (int i = 0; i < threadCount; i++) {
			//�ѽ�������ӵ�linearlayout
			ProgressBar pb = (ProgressBar) View.inflate(this, R.layout.pb, null);
			llContent.addView(pb);
		}
		//�����߳�
		new Thread(){
			public void run() {
				requestNetWork();
			};
		}.start();
	}

	protected void requestNetWork() {
		try {
			// 1. ��ȡ��������Դ�Ĵ�С�������ͷ�������Դͬ����С�Ŀ��ļ�
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3000);
			int code = conn.getResponseCode();
			int filelength =0;
			if (code == 200) {
				//��ȡ��������Դ�Ĵ�С
				filelength = conn.getContentLength();
				System.out.println("��������Դ��С��"+filelength);
				//�����ͷ�������Դͬ����С�Ŀ��ļ�
				RandomAccessFile raf = new RandomAccessFile(getFilePath(), "rw");
				raf.setLength(filelength);
				raf.close();
			}
			// 2. ��������߳����ط�������Ӧ��һ����Դ
			//ÿ���߳����ص������С
			int blockSize = filelength/threadCount;
			for (int threadId = 0; threadId < threadCount; threadId++) {
				int startIndex = threadId*blockSize;
				int endIndex = (threadId+1)*blockSize-1;
				//���һ���߳���������λ��
				if (threadCount-1 == threadId) {
					endIndex = filelength-1;
				}
				new DownLoadThread(startIndex, endIndex, threadId).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�����ļ��Ĵ洢·��
	 */
	private  String getFilePath() {
		int index = path.lastIndexOf("/")+1;
		String name = path.substring(index);
		return "/mnt/sdcard/"+name;
	}
	
	private  String getTmpFilePath(int threadId) {
		return getFilePath()+threadId+".txt";
	}

	 class DownLoadThread extends Thread{
		//��ʼλ��
		int startIndex;
		//����λ��
		int endIndex;
		//�߳�id
		int threadId;
		//��һ�ζϵ��λ��
		int lastDownPos;
		//ÿ���̵߳Ľ�����
		ProgressBar mPb;
		//���ȵ����ֵ
		int max;
		//�������ĵ�ǰ����
		int progress;
		//�����ϵ�һ�����ص�λ��
		int fistIndex;
		
		public DownLoadThread(int startIndex, int endIndex, int threadId) {
			super();
			fistIndex = startIndex;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadId = threadId;
			lastDownPos = startIndex;//��ʼ��	
			mPb = (ProgressBar) llContent.getChildAt(threadId);
			max = endIndex - startIndex;
			mPb.setMax(max);//�������ֵ
			
		}
		
		@Override
		public void run() {
			super.run();
			System.out.println("�������߳� "+threadId+ " :"+startIndex+" ~ "+endIndex);
			//����洢�ϵ�λ�õ��ļ��洢
			File tempFile = new File(getTmpFilePath(threadId));
			try {
				if (tempFile.exists() && tempFile.length()>0) {
					FileInputStream fis = new FileInputStream(tempFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					String text = br.readLine();
					//�޸���ʼλ��λ��һ�ζϵ��λ��
					startIndex = Integer.valueOf(text);
					lastDownPos = startIndex;//��ζϵ�
					br.close();
					System.out.println("�ϵ���߳� "+threadId+ " :"+startIndex+" ~ "+endIndex);
				}
				
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(3000);
				//��Ҫ�����߷������ͻ���Ҫ�����ǿ���Դ
				conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
				//��ȡÿ���߳�����ɹ�����Ӧ״̬��
				int code = conn.getResponseCode();
				System.out.println("��������ɹ���"+code);
				if (code == 206) {
					InputStream is = conn.getInputStream();
					//���������
					RandomAccessFile raf = new RandomAccessFile(getFilePath(), "rw");
					int len =-1;
					byte[] buffer= new byte[1024*2];
					//��λ��ÿ���߳̿�ʼ��λ��
					raf.seek(startIndex);
					while((len=is.read(buffer)) != -1){
						raf.write(buffer,0,len);
						
						//���ϵ��λ�ø�ֵ
						lastDownPos += len;
						//�Ѷϵ��λ�ô洢��txt�ļ���
						RandomAccessFile r = new RandomAccessFile(getTmpFilePath(threadId), "rwd");
						r.write(String.valueOf(lastDownPos).getBytes());
						r.close();
						//���㵱ǰ�߳������ص��ļ���С
						progress = lastDownPos - fistIndex;
						mPb.setProgress(progress);//�����Ѿ����صĽ���
					}
					raf.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 3. ÿ���̶߳�������ˣ�������������ԴҲ���������
			System.out.println("�߳�"+threadId+" ������ˣ�");
			//ɾ���ϵ����ʱ�ļ�
			System.out.println(tempFile.delete());
		}
	}
}
