package org.haw.bwl2.praktikum.util;

public class StringUtils {
	private StringUtils() {}
	
	public static boolean isNullOrEmpty(String string) {
		return string==null || string.trim().isEmpty();
	}
}
