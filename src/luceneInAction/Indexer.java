package luceneInAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Indexer {
	public static void main(String[] args) throws Exception {
//		if (args.length != 2) {
//			throw new IllegalArgumentException("Usage: java " + Indexer.class.getName() + " <index dir> <data dir>");
//		}
//		String indexDir = args[0];
		// create index in this dir
		String indexDir = "C:\\Users\\iNVAiN\\Desktop\\µ¹ÅÅË÷Òý\\n¶àÍøÒ³index";
//		String dataDir = args[1];
		// index *.txt from this dir
		String dataDir = "C:\\Users\\iNVAiN\\Desktop\\µ¹ÅÅË÷Òý\\n¶àÍøÒ³";

		long start = System.currentTimeMillis();
		Indexer indexer = new Indexer(indexDir);
		int numIndexed;
		try {
			numIndexed = indexer.index(dataDir, null);
		} finally {
			indexer.close();
		}
		long end = System.currentTimeMillis();

		System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
	}

	private IndexWriter writer;
	int indexCounter = 0;
	
	public Indexer(String indexDir) throws IOException {
		Directory dir = FSDirectory.open(new File(indexDir));
		Analyzer analyzer = new IKAnalyzer(true);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		writer = new IndexWriter(dir, iwc);
//		writer.setInfoStream(System.out);
	}

	public void close() throws IOException {
		writer.close();
	}

	public int index(String dataDir, FileFilter filter) throws Exception {
		File[] files = new File(dataDir).listFiles();

		for (File f : files) {
			if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() && (filter == null || filter.accept(f))) {
				indexCounter++;
				indexFile(f);
//				showFile(f);
			}
		}
		return writer.numDocs();
	}

	private static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
			return path.getName().toLowerCase().endsWith(".txt");
		}
	}

	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
		
		doc.add(new Field("content", new BufferedReader(new FileReader(f)), Field.TermVector.WITH_POSITIONS_OFFSETS));
		doc.add(new Field("filename", f.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new NumericField("id", Field.Store.YES, true).setIntValue(indexCounter) );
		return doc;
	}
	private void indexFile(File f) throws Exception {
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);
	}
	private void showFile(File f) throws Exception {
		System.out.println("Showing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		System.out.println(doc.getFields());
	}
}
