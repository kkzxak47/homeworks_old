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
 * 实现最大匹配算法（正向）
 * 方法是将词库存入HashSet，判断分出的词是否在HashSet内，比对成功则输出这个词，否则输出一个字。
 * 英文、数字不进行分词。
 * 通过研究Unicode的范围，将所有标点符号单独分出。
 */
public class Forward {
	public static void main(String[] args) throws IOException {
		String path1 = "src/分词词库.txt"; // 词库文件
		File file1 = new File(path1);
		FileInputStream f1 = new FileInputStream(file1);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(f1));
		HashSet<String> dict = new HashSet<String>(); // dict用来存储词库
		String line = null;
		while((line = br1.readLine()) != null) {
			dict.add(line);
		}
		br1.close(); // dict读取完毕
		
		String path2 = "src/待分词.txt"; 
		File file2 = new File(path2);
		FileInputStream f2 = new FileInputStream(file2);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(f2)); // br2用来读取待分词文件
		
		File newFile = new File("src/分词结果（正向）.txt");
		FileWriter write = new FileWriter(newFile, false);
		BufferedWriter bufferedWriter = new BufferedWriter(write); // bufferedWriter用来写入结果
		
		String toSplit = null;
		StringBuilder sb = new StringBuilder(); // sb用来暂存每一句的分词结果
		final int MAXLEN = 7; // 每次最多取7个字
		while ((toSplit = br2.readLine()) != null) { // 每次读取一句
			int tracker = toSplit.length(); // tracker用来跟踪每一句余下的字数
			int n = 0; // n用来记录每次分出的词的长度
			int start = 0; // start用来记录在一句中当前所处的位置
			String sevenStr = null; // sevenStr用来存储每次取出的7个字
			boolean engFlag = true; // 状态flag，是否在字母、数字中
			
			while(tracker > MAXLEN) { // 当余下的字数仍大于7个的处理流程
				sevenStr = toSplit.substring(start, start + MAXLEN);
				n = forwardSplitWord(sevenStr, sb, dict);
				if (8 == n) { // forwardSplitWord函数返回8代表此次分出的不是汉字，也不是标点
					engFlag = true;
					n -= 7;
				}
				else if (9 == n) { //返回9表示分出的是标点
					if (true == engFlag)
						sb.insert(sb.length()-2, "/");
					engFlag = false;
					n -= 8;
				}
				else {
					if (true == engFlag)
						sb.insert(sb.length()-n-1, "/");
					engFlag = false;
				}
				start += n;
				tracker -= n;
			}
			
			sevenStr = toSplit.substring(start);
			
			while(tracker > 0) { // 当余下的字数不足7个
				n = forwardSplitWord(sevenStr, sb, dict);
				if (8 == n) {
					engFlag = true;
					n -= 7;
				}
				else if (9 == n) {
					if (true == engFlag)
						sb.insert(sb.length()-2, "/");
					engFlag = false;
					n -= 8;
				}
				else {
					if (true == engFlag)
						sb.insert(sb.length()-n-1, "/");
					engFlag = false;
				}
				sevenStr = sevenStr.substring(n);
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
	// 正向匹配，递归函数
	public static int forwardSplitWord(String sevenStr, StringBuilder sb, HashSet<String> dict) throws IOException {
		
		if(sevenStr.length() == 1) {
			if (Character.isIdeographic(sevenStr.charAt(0))) { // 如果是汉字
				sb.append(sevenStr + "/");
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
					sb.append(sevenStr + "/");
					return 9;
				}
				else { // 不是汉字，也不是标点
					sb.append(sevenStr);
					return 8;
				}
			}
		}
		else {
			if (dict.contains(sevenStr)) {
				sb.append(sevenStr + "/");
				return sevenStr.length();
			}
			else {
				String tempStr = sevenStr.substring(0, sevenStr.length() - 1);
				return forwardSplitWord(tempStr, sb, dict);
			}
		}
	}
}

/* * 刷该流的缓冲。
 * bufferedWriter.flush(); 
* 关键的一行代码。如果没有加这行代码。数据只是保存在缓冲区中。没有写进文件。
 * 加了这行才能将数据写入目的地。 * */
