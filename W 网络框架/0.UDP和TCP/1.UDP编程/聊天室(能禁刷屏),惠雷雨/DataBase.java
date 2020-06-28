package com.heima.udp.thread2;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
	
	private static List<String> list = new ArrayList<String>() ;
	public static int lineNumber = 0 ;
	
	public  static List<String> getDataBase() {
		return list ;
	}
	
	public static void addData(String ip) {
		list.add(ip) ;
	}

}
