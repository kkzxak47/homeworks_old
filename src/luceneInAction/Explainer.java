package luceneInAction;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Explainer {
	public static void main(String[] args) throws IOException, ParseException {
		String indexDir = "index";
		String  queryExpression = "law";
		
		Directory directory = FSDirectory.open(new File(indexDir));
		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new IKAnalyzer(true));
		Query query = parser.parse(queryExpression);
		
		System.out.println("Query: " + queryExpression);
		
		IndexSearcher searcher = new IndexSearcher(directory);
		TopDocs topDocs = searcher.search(query, 10);
		
		for(ScoreDoc match : topDocs.scoreDocs) {
			Explanation explanation = searcher.explain(query, match.doc);
			
			System.out.println("-------");
			Document doc = searcher.doc(match.doc);
			System.out.println(doc.get("filename"));
			System.out.println(explanation.toString());
		}
		searcher.close();
		directory.close();
	}
}
