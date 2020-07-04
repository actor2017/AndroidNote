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
	private static String getFilePath() {
		int index = path.lastIndexOf("/")+1;
		String name = path.substring(index);
		return "D:\\"+name;
	}
	
	private static String getTmpFilePath(int threadId) {
		return getFilePath()+threadId+".txt";
	}

	static class DownLoadThread extends Thread{
		//��ʼλ��
		int startIndex;
		//����λ��
		int endIndex;
		//�߳�id
		int threadId;
		//��һ�ζϵ��λ��
		int lastDownPos;
		
		public DownLoadThread(int startIndex, int endIndex, int threadId) {
			super();
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.threadId = threadId;
			lastDownPos = startIndex;//��ʼ��	
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
						//rws:�൱�ڻ�еӲ��,�л���,����ʵʱ�洢.	rwd:�൱�ڹ�̬Ӳ��,��ȡ�ٶȿ�
						RandomAccessFile r = new RandomAccessFile(getTmpFilePath(threadId), "rwd");
						r.write(String.valueOf(lastDownPos).getBytes());
						r.close();
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
