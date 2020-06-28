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
			DatagramSocket ds = new DatagramSocket();		// ��������
				
			while(true) {
				
				String nextLine = sc.nextLine() ;
				// ����DatagramPacket����
				DatagramPacket dp = new DatagramPacket(nextLine.getBytes(), nextLine.getBytes().length, InetAddress.getByName("192.168.13.255"), 9999) ;
				// ���÷���
				ds.send(dp) ;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		
	}
	
	
	

}
