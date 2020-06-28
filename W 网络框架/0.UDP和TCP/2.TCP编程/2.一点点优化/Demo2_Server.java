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
	 * * 2.服务端
	* 创建ServerSocket(需要指定端口号)
	* 调用ServerSocket的accept()方法接收一个客户端请求，得到一个Socket
	* 调用Socket的getInputStream()和getOutputStream()方法获取和客户端相连的IO流
	* 输入流可以读取客户端输出流写出的数据
	* 输出流可以写出数据到客户端的输入流
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//单线程不合理
		ServerSocket server = new ServerSocket(12345);
		
		Socket socket = server.accept();						//接受客户端的请求
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));		//将字节流包装成了字符流
		PrintStream ps = new PrintStream(socket.getOutputStream());					//PrintStream中有写出换行的方法
		
		ps.println("欢迎咨询黑马程序员");//★★★如果不加ln,客户端会一直读不到换行,服务端和客户端会一直等待...★★★
		System.out.println(br.readLine());
		ps.println("不好意思,爆满了");
		System.out.println(br.readLine());
		socket.close();
	}
}
