package com.heima.udp.thread2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceiveThread extends Thread {

	@Override
	public void run() {
		
		try {
			
			DatagramSocket ds = new DatagramSocket(9999) ;
			
			while(true) {
				
				DatagramPacket dp = new DatagramPacket(new byte[1024], 1024) ;
				ds.receive(dp) ;
				byte[] data = dp.getData() ;
				int length = dp.getLength() ;
				String ip = dp.getAddress().getHostAddress() ;
				writChatInfo(data , length , ip) ;
				DataBase.lineNumber++ ;
				List<String> dataBase = DataBase.getDataBase() ;
				if(!dataBase.contains(ip)) {
					System.out.println(ip + "˵: " + new String(data , 0 , length));
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace() ;
		}
		
	}
	
	public void writChatInfo(byte[] bytes , int length , String ip) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("chat.info" , true)) ;
		Date date = new Date() ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		bw.write(ip + "˵: " + new String(bytes, 0 ,length ) + "\t" + sdf.format(date)) ;
		bw.newLine() ;
		bw.flush() ;
		bw.close() ;
	}
}
