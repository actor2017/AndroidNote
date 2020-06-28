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
	
	//默认线程数位3
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

	//点击事件,开始下载
	public void download(View v){
		path = etPath.getText().toString().trim();
		String count = etThreadCount.getText().toString().trim();
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(this, "请输入正确的网址", 0).show();
			return;
		}
		//判断用户是否输入线程输入
		if (!TextUtils.isEmpty(count)) {
			threadCount = Integer.valueOf(count);
		}
		//添加之前清除所有的进度条
		llContent.removeAllViews();
		
		for (int i = 0; i < threadCount; i++) {
			//把进度条添加到linearlayout
			ProgressBar pb = (ProgressBar) View.inflate(this, R.layout.pb, null);
			llContent.addView(pb);
		}
		//开启线程
		new Thread(){
			public void run() {
				requestNetWork();
			};
		}.start();
	}

	protected void requestNetWork() {
		try {
			// 1. 获取服务器资源的大小，创建和服务器资源同样大小的空文件
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3000);
			int code = conn.getResponseCode();
			int filelength =0;
			if (code == 200) {
				//获取服务器资源的大小
				filelength = conn.getContentLength();
				System.out.println("服务器资源大小："+filelength);
				//创建和服务器资源同样大小的空文件
				RandomAccessFile raf = new RandomAccessFile(getFilePath(), "rw");
				raf.setLength(filelength);
				raf.close();
			}
			// 2. 开启多个线程下载服务器对应的一块资源
			//每个线程下载的区块大小
			int blockSize = filelength/threadCount;
			for (int threadId = 0; threadId < threadCount; threadId++) {
				int startIndex = threadId*blockSize;
				int endIndex = (threadId+1)*blockSize-1;
				//最后一个线程修正结束位置
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
	 * 获取下载文件的存储路径
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
		//开始位置
		int startIndex;
		//结束位置
		int endIndex;
		//线程id
		int threadId;
		//上一次断点的位置
		int lastDownPos;
		//每个线程的进度条
		ProgressBar mPb;
		//进度的最大值
		int max;
		//进度条的当前进度
		int progress;
		//理论上第一次下载的位置
		int fistIndex;
		
		public DownLoadThread(int startIndex, int endIndex, int threadId) {
			super();
			fistIndex = startIndex;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadId = threadId;
			lastDownPos = startIndex;//初始化	
			mPb = (ProgressBar) llContent.getChildAt(threadId);
			max = endIndex - startIndex;
			mPb.setMax(max);//设置最大值
			
		}
		
		@Override
		public void run() {
			super.run();
			System.out.println("理论上线程 "+threadId+ " :"+startIndex+" ~ "+endIndex);
			//如果存储断点位置的文件存储
			File tempFile = new File(getTmpFilePath(threadId));
			try {
				if (tempFile.exists() && tempFile.length()>0) {
					FileInputStream fis = new FileInputStream(tempFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					String text = br.readLine();
					//修改起始位置位上一次断点的位置
					startIndex = Integer.valueOf(text);
					lastDownPos = startIndex;//多次断点
					br.close();
					System.out.println("断点后线程 "+threadId+ " :"+startIndex+" ~ "+endIndex);
				}
				
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(3000);
				//重要，告诉服务器客户端要下载那块资源
				conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
				//获取每个线程请求成功的响应状态码
				int code = conn.getResponseCode();
				System.out.println("部分请求成功："+code);
				if (code == 206) {
					InputStream is = conn.getInputStream();
					//随机访问流
					RandomAccessFile raf = new RandomAccessFile(getFilePath(), "rw");
					int len =-1;
					byte[] buffer= new byte[1024*2];
					//定位到每个线程开始的位置
					raf.seek(startIndex);
					while((len=is.read(buffer)) != -1){
						raf.write(buffer,0,len);
						
						//给断点的位置赋值
						lastDownPos += len;
						//把断点的位置存储到txt文件中
						RandomAccessFile r = new RandomAccessFile(getTmpFilePath(threadId), "rwd");
						r.write(String.valueOf(lastDownPos).getBytes());
						r.close();
						//计算当前线程已下载的文件大小
						progress = lastDownPos - fistIndex;
						mPb.setProgress(progress);//设置已经下载的进度
					}
					raf.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 3. 每个线程都干完活了，整个服务器资源也就下载完毕
			System.out.println("线程"+threadId+" 干完活了！");
			//删除断点的临时文件
			System.out.println(tempFile.delete());
		}
	}
}
