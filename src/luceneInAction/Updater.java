package luceneInAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Updater {
	public static void main(String[] args) throws IOException {
		String indexDir = "index";
		String dataDir = "txts";
		
		Directory dir = FSDirectory.open(new File(indexDir));
		Analyzer analyzer = new IKAnalyzer(true);
		//IndexWriterConfig
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_30, analyzer);
		IndexWriter writer = new IndexWriter(dir, iwc);
		
		//删除前
		System.out.println(writer.maxDoc());
		System.out.println(writer.numDocs());
		//删除doc
//		Term t = new Term("filename", "class_field.txt");
//		writer.deleteDocuments(t);
		
		File f = new File("txts/class_field.txt");
		
		Document doc = new Document();
		doc.add(new Field("contents", new BufferedReader(new FileReader(f))));
		doc.add(new Field("fullpath", "C:\\Users\\iNVAiN\\workspace\\luceneInAction_zx\\txts\\class_field.txt", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("filename", "class_field_modified", Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		writer.updateDocument(new Term("filename","class_field.txt"), doc);
		//危险！清空索引
//		writer.deleteAll();
		//提交修改
		writer.commit();
//		writer.optimize();
		//删除后
		System.out.println(writer.maxDoc());
		System.out.println(writer.numDocs());
		
		writer.close();
	}
	
}
