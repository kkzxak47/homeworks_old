package com.zhongxun.zxanalyzer;

public class testDrive {
	public static void main(String[] args) {
//		StringBuffer sb = new StringBuffer("1234567");
//		sb.insert(sb.length()-1, "c");
//		System.out.println(sb);
		
//		System.out.println(Character.UnicodeBlock.of('/'));
//		char a = 'a';
		System.out.println(Character.UnicodeBlock.of('.'));
		System.out.println(Character.isIdeographic('.'));
		String sevenStr = new String(".");
		System.out.println( 
				(Character.isWhitespace(sevenStr.charAt(0))) || 
				((int)sevenStr.charAt(0) >= 0xff00 && (int)sevenStr.charAt(0) <= 0xffef) || 
				((int)sevenStr.charAt(0) >= 0x3000 && (int)sevenStr.charAt(0) <= 0x303f) ||
				((int)sevenStr.charAt(0) >= 0x2000 && (int)sevenStr.charAt(0) <= 0x206f) ||
				((int)sevenStr.charAt(0) >= 0x0021 && (int)sevenStr.charAt(0) <= 0x002f) ||
				((int)sevenStr.charAt(0) >= 0x003a && (int)sevenStr.charAt(0) <= 0x003f) ||
				((int)sevenStr.charAt(0) >= 0x005b && (int)sevenStr.charAt(0) <= 0x005f) ||
				((int)sevenStr.charAt(0) >= 0x007b && (int)sevenStr.charAt(0) <= 0x007f)
				);
	}
}
