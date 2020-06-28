package com.heima.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Demo1_Send {

	/**
	 * * 1.发送Send
	 * 创建DatagramSocket, 随机端口号
	 * 创建DatagramPacket, 指定数据, 长度, 地址, 端口
	 * 使用DatagramSocket发送DatagramPacket
	 * 关闭DatagramSocket
	 * @throws SocketException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws Exception {
		String str = "what are you 弄啥呢?";
		DatagramSocket socket = new DatagramSocket();				//创建Socket相当于创建码头
		DatagramPacket packet = new DatagramPacket(str.getBytes(),	//创建Packet相当于集装箱
				str.length(),
				InetAddress.getByName("127.0.0.1"),					//发给谁
				6666);												//端口
		socket.send(packet);										//发货,将数据发出去
		socket.close();												//关闭码头
	}
}
