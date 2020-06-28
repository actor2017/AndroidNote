package com.heima.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Demo2_Server {

	/**
	 * * 2.�����
	* ����ServerSocket(��Ҫָ���˿ں�)
	* ����ServerSocket��accept()��������һ���ͻ������󣬵õ�һ��Socket
	* ����Socket��getInputStream()��getOutputStream()������ȡ�Ϳͻ���������IO��
	* ���������Զ�ȡ�ͻ��������д��������
	* ���������д�����ݵ��ͻ��˵�������
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//���̲߳�����
		ServerSocket server = new ServerSocket(12345);
		
		Socket socket = server.accept();						//���ܿͻ��˵�����
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));		//���ֽ�����װ�����ַ���
		PrintStream ps = new PrintStream(socket.getOutputStream());					//PrintStream����д�����еķ���
		
		ps.println("��ӭ��ѯ�������Ա");//�����������ln,�ͻ��˻�һֱ����������,����˺Ϳͻ��˻�һֱ�ȴ�...����
		System.out.println(br.readLine());
		ps.println("������˼,������");
		System.out.println(br.readLine());
		socket.close();
	}
}
