package com.heima.udp.thread;

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
			
			BufferedReader br = new BufferedReader(new FileReader("chat.info")) ;
			String line = null ;
			int count = 0 ;
			while((line = br.readLine()) != null) {
				count++ ;
			}
			
			Random random = new Random() ;
			int nextInt = random.nextInt(count) ;
			int lineNum = nextInt + 20 ;
			
			List<String> lines = new ArrayList<String>() ;
			
			if(lineNum > count) {
				
				int count2 = 0 ;
				while((line = br.readLine()) != null) {
					count2++ ;
					if(count2 == nextInt) {
						lines.add(line) ;
					}
				}
				
			}else {
				
				int count2 = 0 ;
				while((line = br.readLine()) != null) {
					count2++ ;
					if(count2 == lineNum) {
						break ;
					}
					if(count2 == nextInt) {
						lines.add(line) ;
					}
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
