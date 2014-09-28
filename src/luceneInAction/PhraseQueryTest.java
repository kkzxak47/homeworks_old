package luceneInAction;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class PhraseQueryTest {
	
	public static void main(String[] args) throws Exception {
		Directory dir = new RAMDirectory();
		IndexSearcher searcher;
		IndexWriter writer = new IndexWriter(dir, new WhitespaceAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
		Document doc = new Document();
		doc.add(new Field("field", "the quick brown fox jumped over the lazy dog", Field.Store.YES, Field.Index.ANALYZED));
		writer.addDocument(doc);
		writer.close();
		
		searcher = new IndexSearcher(dir);
		String[] phrase = new String[] {"quick", "fox"};
		PhraseQuery query = new PhraseQuery();
		query.setSlop(1);
		query.add(new Term("field", phrase[0]));
		query.add(new Term("field", phrase[1]));
		TopDocs matches = searcher.search(query, 10);
		System.out.println(matches.totalHits);
		searcher.close();
		dir.close();
	}
}
