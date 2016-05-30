package com.pdt.android_gis.util;

import java.util.HashMap;

public class RssMapColor {
	
	double key;
	int color;
	public static HashMap hm = new HashMap();
	public static HashMap hashmap = new HashMap();
	
	public RssMapColor(){
		
		for(key = 0; key>-60; key--){
			
			hm.put(key, MakerColor.color60);
			hashmap.put(key, MakerColor.COLOR60);
			
		}
		
		for(key = -60; key>-70; key--){
			
			hm.put(key, MakerColor.color70);
			hashmap.put(key, MakerColor.COLOR70);
			
		}
		
		for(key = -70; key>-80; key--){
			
			hm.put(key, MakerColor.color80);
			hashmap.put(key, MakerColor.COLOR80);
			
		}
		
		for(key = -80; key>-90; key--){
			
			hm.put(key, MakerColor.color90);
			hashmap.put(key, MakerColor.COLOR90);
			
		}
		
		for(key = -90; key>-100; key--){
			
			hm.put(key, MakerColor.color100);
			hashmap.put(key, MakerColor.COLOR100);
			
		}
		
		for(key = -100; key>-110; key--){
			
			hm.put(key, MakerColor.color110);
			hashmap.put(key, MakerColor.COLOR110);
			
		}
		
		for(key = -110; key>-120; key--){
			
			hm.put(key, MakerColor.color120);
			hashmap.put(key, MakerColor.COLOR120);
			
		}
		
		for(key = -120; key>-200; key--){
			
			hm.put(key, MakerColor.color130);
			hashmap.put(key, MakerColor.COLOR130);
			
		}
		
	}
	
	

}
