package com.pdt.android_gis.util;

/**
 * 在Logcat中打印Log工具
 * @author Chasel.li
 *
 */
public class LogUtil {

	public static String makeLogTag(Class<?> cls) {
		return "demo_" + cls.getSimpleName();
	}
}
