package com.pdt.android_gis.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 
 * 
 * @author Chasel.li
 * 
 */
public class PropertiesUtil {

	/**
	 * 
	 * @return
	 */
	public static String getUrl() {
		Properties props = new Properties();
		try {
			props.load(PropertiesUtil.class.getResourceAsStream("/assets/demo.properties"));
		} catch (IOException e) {
			return "PropertiesUtil IOException:" + e.getMessage();
		}
		String result = props.getProperty("server");
		return result;
	}

}

