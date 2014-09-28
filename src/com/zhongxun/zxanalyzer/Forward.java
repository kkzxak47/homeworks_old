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
 * ʵ�����ƥ���㷨������
 * �����ǽ��ʿ����HashSet���жϷֳ��Ĵ��Ƿ���HashSet�ڣ��ȶԳɹ����������ʣ��������һ���֡�
 * Ӣ�ġ����ֲ����зִʡ�
 * ͨ���о�Unicode�ķ�Χ�������б����ŵ����ֳ���
 */
public class Forward {
	public static void main(String[] args) throws IOException {
		String path1 = "src/�ִʴʿ�.txt"; // �ʿ��ļ�
		File file1 = new File(path1);
		FileInputStream f1 = new FileInputStream(file1);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(f1));
		HashSet<String> dict = new HashSet<String>(); // dict�����洢�ʿ�
		String line = null;
		while((line = br1.readLine()) != null) {
			dict.add(line);
		}
		br1.close(); // dict��ȡ���
		
		String path2 = "src/���ִ�.txt"; 
		File file2 = new File(path2);
		FileInputStream f2 = new FileInputStream(file2);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(f2)); // br2������ȡ���ִ��ļ�
		
		File newFile = new File("src/�ִʽ��������.txt");
		FileWriter write = new FileWriter(newFile, false);
		BufferedWriter bufferedWriter = new BufferedWriter(write); // bufferedWriter����д����
		
		String toSplit = null;
		StringBuilder sb = new StringBuilder(); // sb�����ݴ�ÿһ��ķִʽ��
		final int MAXLEN = 7; // ÿ�����ȡ7����
		while ((toSplit = br2.readLine()) != null) { // ÿ�ζ�ȡһ��
			int tracker = toSplit.length(); // tracker��������ÿһ�����µ�����
			int n = 0; // n������¼ÿ�ηֳ��Ĵʵĳ���
			int start = 0; // start������¼��һ���е�ǰ������λ��
			String sevenStr = null; // sevenStr�����洢ÿ��ȡ����7����
			boolean engFlag = true; // ״̬flag���Ƿ�����ĸ��������
			
			while(tracker > MAXLEN) { // �����µ������Դ���7���Ĵ�������
				sevenStr = toSplit.substring(start, start + MAXLEN);
				n = forwardSplitWord(sevenStr, sb, dict);
				if (8 == n) { // forwardSplitWord��������8����˴ηֳ��Ĳ��Ǻ��֣�Ҳ���Ǳ��
					engFlag = true;
					n -= 7;
				}
				else if (9 == n) { //����9��ʾ�ֳ����Ǳ��
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
			
			while(tracker > 0) { // �����µ���������7��
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
	// ����ƥ�䣬�ݹ麯��
	public static int forwardSplitWord(String sevenStr, StringBuilder sb, HashSet<String> dict) throws IOException {
		
		if(sevenStr.length() == 1) {
			if (Character.isIdeographic(sevenStr.charAt(0))) { // ����Ǻ���
				sb.append(sevenStr + "/");
				return 1;
			}
			else{ // ����Ǳ��
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
				else { // ���Ǻ��֣�Ҳ���Ǳ��
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

/* * ˢ�����Ļ��塣
 * bufferedWriter.flush(); 
* �ؼ���һ�д��롣���û�м����д��롣����ֻ�Ǳ����ڻ������С�û��д���ļ���
 * �������в��ܽ�����д��Ŀ�ĵء� * */
