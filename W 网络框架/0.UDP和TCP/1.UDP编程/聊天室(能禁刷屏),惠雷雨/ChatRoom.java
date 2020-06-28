package com.heima.udp.thread2;

import java.util.Timer;

public class ChatRoom {
	
	public static void main(String[] args) {
		
		new SendThread().start() ;
		new ReceiveThread().start() ;
		Timer timer = new Timer() ;
		timer.schedule(new ReaderChatInfoTimerTask(), 5000, 3000) ;
		
	}

}
