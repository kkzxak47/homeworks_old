package com.zhongxun.zxanalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.io.InputStreamReader;
import java.io.FileInputStream;


/**
 * 
 * @author iNVAiN
 * 实现最大匹配算法（逆向）
 *
 */
public class Backward {
	public static void main(String[] args) throws IOException {
		String path1 = "src/分词词库.txt";
		File file1 = new File(path1);
		FileInputStream f1 = new FileInputStream(file1);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(f1));
		HashSet<String> dict = new HashSet<String>();
		String line = null;
		while((line = br1.readLine()) != null) {
			dict.add(line);
		}
		br1.close();
		
		String path2 = "src/测试文件：3d打印机.txt";
		File file2 = new File(path2);
		FileInputStream f2 = new FileInputStream(file2);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(f2));
		
		File newFile = new File("src/3d打印机-分词结果（逆向）.txt");
		FileWriter write = new FileWriter(newFile, false);
		BufferedWriter bufferedWriter = new BufferedWriter(write);
		
		String toSplit = null;
		StringBuilder sb = new StringBuilder();
		final int MAXLEN = 7;
		while ((toSplit = br2.readLine()) != null) {
			int tracker = toSplit.length();
			int n = 0;
			String sevenStr = null;
			boolean engFlag = true; // 状态flag，是否在字母、数字中
			
			while(tracker > MAXLEN) {
				sevenStr = toSplit.substring(tracker - MAXLEN, tracker);
				n = backwardSplitWord(sevenStr, sb, dict);
				if (8 == n) { // forwardSplitWord函数返回8代表此次分出的不是汉字，也不是标点
					engFlag = true;
					n -= 7;
				}
				else if (9 == n) { //返回9表示分出的是标点
					if (true == engFlag)
						sb.insert(0, "/");
					engFlag = false;
					n -= 8;
				}
				else {
					if (true == engFlag)
						sb.insert(0, "/");
					engFlag = false;
				}
				tracker -= n;
			}
			
			sevenStr = toSplit.substring(0, tracker);
			while(tracker > 0) {
				n = backwardSplitWord(sevenStr, sb, dict);
				if (8 == n) {
					engFlag = true;
					n -= 7;
				}
				else if (9 == n) {
					if (true == engFlag)
						sb.insert(0, "/");
					engFlag = false;
					n -= 8;
				}
				else {
					if (true == engFlag)
						sb.insert(0, "/");
					engFlag = false;
				}
				sevenStr = sevenStr.substring(0, tracker - n);
				tracker -= n;
			}
			
			bufferedWriter.write(sb.toString());
			sb.delete(0, sb.length());
			bufferedWriter.newLine();
		}
		
		br2.close();
		bufferedWriter.flush();
		bufferedWriter.close();
		write.close();

	}
	
	
	public static int backwardSplitWord(String sevenStr, StringBuilder sb, HashSet<String> dict) throws IOException {
		if(sevenStr.length() == 1) {
			if (Character.isIdeographic(sevenStr.charAt(0))) { // 如果是汉字
				sb.insert(0, "/" + sevenStr);
				return 1;
			}
			else{ // 如果是标点
				if ( (Character.isWhitespace(sevenStr.charAt(0))) || 
					 ( (int)sevenStr.charAt(0) >= 0xff00 && (int)sevenStr.charAt(0) <= 0xffef ) || 
					 ( (int)sevenStr.charAt(0) >= 0x3000 && (int)sevenStr.charAt(0) <= 0x303f ) ||
					 ( (int)sevenStr.charAt(0) >= 0x2000 && (int)sevenStr.charAt(0) <= 0x206f ) ||
					 ( (int)sevenStr.charAt(0) >= 0x0021 && (int)sevenStr.charAt(0) <= 0x002f ) ||
					 ( (int)sevenStr.charAt(0) >= 0x003a && (int)sevenStr.charAt(0) <= 0x003f ) ||
					 ( (int)sevenStr.charAt(0) >= 0x005b && (int)sevenStr.charAt(0) <= 0x005f ) ||
					 ( (int)sevenStr.charAt(0) >= 0x007b && (int)sevenStr.charAt(0) <= 0x007f ) ) 
				{
					sb.insert(0, "/" + sevenStr);
					return 9;
				}
				else { // 不是汉字，也不是标点
					sb.insert(0, sevenStr);
					return 8;
				}
			}
		}
		else {
			if (dict.contains(sevenStr)) {
				sb.insert(0, "/" + sevenStr);
				return sevenStr.length();
			}
			else {
				String tempStr = sevenStr.substring(1, sevenStr.length());
				return backwardSplitWord(tempStr, sb, dict);
			}
		}
	}
}

/* * 刷该流的缓冲。
 * bufferedWriter.flush(); 
* 关键的一行代码。如果没有加这行代码。数据只是保存在缓冲区中。没有写进文件。
 * 加了这行才能将数据写入目的地。 * */
