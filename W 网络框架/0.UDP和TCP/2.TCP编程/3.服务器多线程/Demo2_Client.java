package com.heima.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Demo2_Client {

	/**
	 * * 1.客户端
		* 创建Socket连接服务端(指定ip地址,端口号)通过ip地址找对应的服务器
		* 调用Socket的getInputStream()和getOutputStream()方法获取和服务端相连的IO流
		* 输入流可以读取服务端输出流写出的数据
		* 输出流可以写出数据到服务端的输入流
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 12345);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));		//将字节流包装成了字符流
		PrintStream ps = new PrintStream(socket.getOutputStream());					//PrintStream中有写出换行的方法
		
		System.out.println(br.readLine());
		ps.println("我想报名黑马程序员");
		System.out.println(br.readLine());
		ps.println("大哭!!!能不能给次机会");
		
		socket.close();
	}

}
