package com.heima.udp.thread2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class SendThread extends Thread {
	
	@Override
	public void run() {
	
		try {
			
			Scanner sc = new Scanner(System.in) ;
			DatagramSocket ds = new DatagramSocket();		// 创建对象
				
			while(true) {
				
				String nextLine = sc.nextLine() ;
				// 创建DatagramPacket对象
				DatagramPacket dp = new DatagramPacket(nextLine.getBytes(), nextLine.getBytes().length, InetAddress.getByName("192.168.13.255"), 9999) ;
				// 调用方法
				ds.send(dp) ;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		
	}
	
	
	

}
