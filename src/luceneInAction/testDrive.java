package luceneInAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
//import org.wltea.analyzer.core.IKSegmenter;
//import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class testDrive {
	public static void main(String[] args) throws IOException {
		 Analyzer analyzer = new IKAnalyzer(true);
//		 String text="����java���Կ����������������ķִʹ��߰�";  
//		 StringReader reader=new StringReader(text);
		 
		 File f = new File("src/robotsЭ��.txt");
		 BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//		 TokenStream ts=analyzer.tokenStream("", reader);
//		 CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
//		 while(ts.incrementToken()){  
//	            System.out.print(term.toString()+"|");  
//	        }
//	     reader.close();  
//	     System.out.println();
//		 IKSegmenter ik=new IKSegmenter(reader, true);
//		 Lexeme lex=null;  
//         while((lex=ik.next())!=null){  
//             System.out.print(lex.getLexemeText()+"|");  
//         }
	}
}
