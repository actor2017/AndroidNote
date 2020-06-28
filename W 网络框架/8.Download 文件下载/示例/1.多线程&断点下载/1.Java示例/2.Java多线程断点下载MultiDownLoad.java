package com.itheima.multidownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MultiDownLoad {

	static String path = "http://192.168.13.63:8080/download/feiq.exe";
	static int threadCount = 3;
	
	public static void main(String[] args) {
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
	private static String getFilePath() {
		int index = path.lastIndexOf("/")+1;
		String name = path.substring(index);
		return "D:\\"+name;
	}
	
	private static String getTmpFilePath(int threadId) {
		return getFilePath()+threadId+".txt";
	}

	static class DownLoadThread extends Thread{
		//开始位置
		int startIndex;
		//结束位置
		int endIndex;
		//线程id
		int threadId;
		//上一次断点的位置
		int lastDownPos;
		
		public DownLoadThread(int startIndex, int endIndex, int threadId) {
			super();
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadId = threadId;
			lastDownPos = startIndex;//初始化	
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
						//rws:相当于机械硬盘,有缓存,不会实时存储.	rwd:相当于固态硬盘,读取速度快
						RandomAccessFile r = new RandomAccessFile(getTmpFilePath(threadId), "rwd");
						r.write(String.valueOf(lastDownPos).getBytes());
						r.close();
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
