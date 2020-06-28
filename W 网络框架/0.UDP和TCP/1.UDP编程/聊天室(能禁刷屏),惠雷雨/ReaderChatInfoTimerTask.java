package com.heima.udp.thread2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TimerTask;

public class ReaderChatInfoTimerTask extends TimerTask {

	@Override
	public void run() {
		
		try {
			
			
			int lineNumber = DataBase.lineNumber ;
			BufferedReader br = new BufferedReader(new FileReader("chat.info")) ;
			String line = null ;
			List<String> lines = new ArrayList<String>() ;
			
			if(lineNumber < 20) {
				int count = 0 ;
				while((line = br.readLine()) != null) {
					if(count == lineNumber) {
						break ;
					}
					lines.add(line) ;
					count++ ;
				}
			}else {
				int startLineNumber = lineNumber - 20 ;
				int count = 0 ;
				while((line = br.readLine()) != null) {
					if(count == lineNumber) {
						break ;
					}
					
					if(count >= startLineNumber) {
						lines.add(line) ;
					}
					count++ ;
				}
			}
			
			// ´´½¨HashMap
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>() ;
			for(String s : lines) {
				String ip = s.substring(0, s.indexOf("Ëµ")) ;
				if(ip != null) {
					if(hashMap.containsKey(ip)) {
						hashMap.put(ip, hashMap.get(ip) + 1) ;
					}else {
						hashMap.put(ip, 1) ;
					}
				}
			}
			
			// HashMap<String, Integer> hashMap = new HashMap<String, Integer>() ;
			Set<Entry<String,Integer>> entrySet = hashMap.entrySet() ;
			for(Entry<String,Integer> en : entrySet) {
				Integer value = en.getValue() ;
				if(value > 5) {
					DataBase.getDataBase().add(en.getKey()) ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
