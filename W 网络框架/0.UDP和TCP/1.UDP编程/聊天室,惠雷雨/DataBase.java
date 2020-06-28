package com.heima.udp.thread;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
	
	private static List<String> list = new ArrayList<String>() ;
	
	public  static List<String> getDataBase() {
		return list ;
	}
	
	public static void addData(String ip) {
		list.add(ip) ;
	}

}
