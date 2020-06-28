package com.heima.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Test1_Client {

	/**
	 * 客户端向服务器写字符串(键盘录入),服务器(多线程)将字符串反转后写回,客户端再次读取到是反转后的字符串
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner sc = new Scanner(System.in);				//创建键盘录入对象
		Socket socket = new Socket("127.0.0.1", 54321);		//创建客户端,指定ip地址和端口号
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//获取输入流
		PrintStream ps = new PrintStream(socket.getOutputStream());//获取输出流
		
		ps.println(sc.nextLine());							//将字符串写到服务器去
		System.out.println(br.readLine()); 					//将反转后的结果读出来
		
		socket.close();
	}

}
