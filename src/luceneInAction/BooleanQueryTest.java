package luceneInAction;
import java.io.File;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
public class BooleanQueryTest {
	public void testAnd() throws Exception {
		Query searchingBooks = new PrefixQuery(new Term("filename", "gpl"));
		// 以gpl开头的文件一共有3个，id为7-9，如果指定从8开始，就只能搜到2个文件
		Query books8_20 = NumericRangeQuery.newIntRange("id", 8, 20, true, true);
		BooleanQuery searchingBooks8_20 = new BooleanQuery();
		searchingBooks8_20.add(searchingBooks, BooleanClause.Occur.MUST);
		searchingBooks8_20.add(books8_20, BooleanClause.Occur.MUST);
		
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);
		TopDocs matches = searcher.search(searchingBooks8_20, 20);
		System.out.println("totalHits: " + matches.totalHits);
		for(ScoreDoc sd : matches.scoreDocs) {
			System.out.println(searcher.doc(sd.doc).get("filename"));
		}
		
		searcher.close();
		dir.close();
	}
		public static void main(String[] args) throws Exception {
			BooleanQueryTest bt = new BooleanQueryTest();
			bt.testAnd();
		}
	
}
