package luceneInAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.vectorhighlight.BaseFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.FastVectorHighlighter;
import org.apache.lucene.search.vectorhighlight.FieldQuery;
import org.apache.lucene.search.vectorhighlight.FragListBuilder;
import org.apache.lucene.search.vectorhighlight.FragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.ScoreOrderFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.SimpleFragListBuilder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;


public class Searcher {
	public static void main(String[] args) throws Exception {
		// String indexDir = "index";
		// String q = "平均";
		// System.out.println("input the word to search: ");
		// Scanner sc = new Scanner(System.in);
		// String q = sc.nextLine();
		// while(!q.equals("_halt_")) {
		// // search(indexDir, q);
		// // termRangeQuery();
		// q = sc.nextLine();
		// }
		Searcher s = new Searcher("C:\\Users\\iNVAiN\\Desktop\\倒排索引\\n多网页index");
//		Searcher s = new Searcher("C:\\solr_index\\index");
		
//		TopDocs td = s.search("content", "美国");
//		System.out.println("hits: " + td.totalHits);
//		ScoreDoc[] sd = td.scoreDocs;
//		for(ScoreDoc sdoc: sd) {
//			String p = s.getDocPath(sdoc.doc);
//			System.out.println(p);
//		}
//		ScoreDoc onedoc = sd[1];
//		String text = s.getcontent(onedoc.doc);
//		System.out.println(text);
		Query query = new TermQuery(new Term("content", "美国"));
//		TokenStream tokenStream = new IKAnalyzer(true).tokenStream("content", new BufferedReader(new FileReader(new File(s.getDocPath(onedoc.doc)))));
		QueryScorer scorer = new QueryScorer(query, "content");
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		SimpleHTMLFormatter sFormatter = new SimpleHTMLFormatter("<font color=\"red\" >", "</font>");
//		Highlighter highlighter = new Highlighter(sFormatter, scorer);
//		highlighter.setTextFragmenter(fragmenter);
//		System.out.println(highlighter.getBestFragment(tokenStream, text));
		FastVectorHighlighter highlighter = getHighlighter();
		FieldQuery fieldQuery = highlighter.getFieldQuery(query);
		TopDocs docs = s.search("content", "美国", 20, 0); // hits
		System.out.println(docs.totalHits);
//		for(ScoreDoc d: docs.scoreDocs) {
//			int num = d.doc;
//			System.out.println("------");
//			System.out.println(s.getTitle(num));
//			System.out.println(s.getURL(num));
//			
//		}
		for(ScoreDoc scoreDoc : docs.scoreDocs) {
			String snippet = highlighter.getBestFragment(fieldQuery, s.getReader(), scoreDoc.doc, "content", 1000 );
			if (snippet != null) {
				System.out.println(scoreDoc.doc + ": " + snippet);
			}
		}
		
		// String[] result = s.search("law");
		// System.out.println(result.toString());

		 s.close();
		// numericRangeQuery();
		// testWildcard();
		// testFuzzy();
		//
	}

	public Searcher(String path) throws IOException {
		dir = FSDirectory.open(new File(path));
		reader = IndexReader.open(dir);
		// 实例化searcher
		issearcher = new IndexSearcher(reader);
		// IK相似度评估器
//		issearcher.setSimilarity(new IKSimilarity());
		// 实例化Analyzer
		analyzer = new IKAnalyzer(true);
	}

	Directory dir = null;
	IndexReader reader = null;
	public IndexReader getReader() {
		return reader;
	}

	public void setReader(IndexReader reader) {
		this.reader = reader;
	}

	IndexSearcher issearcher = null;
	Analyzer analyzer = null;
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public void close() throws IOException {
		reader.close();
		issearcher.close();
		dir.close();
		analyzer.close();
	}
	
	public TopDocs search(String field, String keyword, int pageSize, int lastIndex) throws Exception {

		// Query query = IKQueryParser.parse("content", q);
		// Query query = new TermQuery(new Term("content", q));
		// Query query = new FuzzyQuery(new Term("content", q));
		// Query query = IKQueryParser.parse("content", q);
		// Term t = new Term("content", "java");
		// Query query = new TermQuery(new Term(keyword));
//		IKQueryParser.setMaxWordLength(true);
		QueryParser qp = new QueryParser(Version.LUCENE_35, field, analyzer);
		qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query query = qp.parse(keyword);
		// long start = System.currentTimeMillis();
		TopDocs hits = null;
		if (lastIndex == 0)
			hits = issearcher.search(query, pageSize);
		else {
			ScoreDoc lastDoc = issearcher.search(query, lastIndex).scoreDocs[lastIndex-1];
			hits = issearcher.searchAfter(lastDoc, query, pageSize);
//			hits = issearcher.search(query, lastIndex+pageSize);
		}
		return hits;
		// long end = System.currentTimeMillis();

		// System.err.println("Found " + hits.totalHits + " document(s) (in " +
		// (end - start)
		// + " milliseconds) that matched query '" + q + "':");
		// System.err.println("the best score: " + hits.getMaxScore());
		// String[] result = new String[hits.totalHits];
		// for(int i=0;i < hits.totalHits; i++) {
		// Document doc = issearcher.doc(hits.scoreDocs[i].doc);
		// result[i] = doc.get("fullpath");
		// System.out.println(doc.get("fullpath"));
		// }
		// return result;
		// issearcher.close();
	}
	public String getDocPath (int num) throws CorruptIndexException, IOException {
		Document doc = issearcher.doc(num);
		return doc.get("fullpath");
	}
	public String getDocName (int num) throws CorruptIndexException, IOException {
		Document doc = issearcher.doc(num);
		return doc.get("filename");
	}
	/* 给本地文件使用的获取方法 */
	public String getContent (int num) throws CorruptIndexException, IOException {
		Document doc = issearcher.doc(num);
		File originalFile = new File(doc.get("fullpath"));
		char[] cbuf = new char[102400];
		
		BufferedReader br = new BufferedReader(new FileReader(originalFile));
//		br.skip(11); 
		int t = br.read(cbuf);
		return new String(cbuf);
		
	}
	// 给抓取信息使用的获取方法
	public String getWebContent (int num) throws CorruptIndexException, IOException {
		Document doc = issearcher.doc(num);
		return doc.get("content");
	}
	
	public String getURL (int num) throws CorruptIndexException, IOException {
		Document doc = issearcher.doc(num);
		return doc.get("url");
	}
	public String getTitle (int num) throws CorruptIndexException, IOException {
		Document doc = issearcher.doc(num);
		return doc.get("title");
	}
	public static FastVectorHighlighter getHighlighter()  
    {  
        FragListBuilder fragListBuilder = new SimpleFragListBuilder();  
        FragmentsBuilder fragmentsBuilder = new ScoreOrderFragmentsBuilder(  
                BaseFragmentsBuilder.COLORED_PRE_TAGS,  
                BaseFragmentsBuilder.COLORED_POST_TAGS);  
        return new FastVectorHighlighter(true, true, fragListBuilder,  
                fragmentsBuilder);  
    }  
	
	
	public void termRangeQuery() throws Exception {
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);
		TermRangeQuery query = new TermRangeQuery("filename", "d", "j", true, true);
		TopDocs matches = searcher.search(query, 100);
		System.out.println(matches.totalHits);
		for (ScoreDoc scoreDoc : matches.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("fullpath"));
		}
		// Document doc1 = searcher.doc(matches.scoreDocs[0].doc);
		// if (doc1 != null) {
		//
		// System.out.println("\nfull text of " + doc1.get("filename") + " : ");
		// System.out.println("***********");
		// BufferedReader reader = new BufferedReader(new InputStreamReader(new
		// FileInputStream(doc1.get("fullpath"))));
		// String line = null;
		// while((line = reader.readLine()) != null) {
		// System.out.println(line);
		// }
		// System.out.println("***********");
		// }
		searcher.close();
		dir.close();
		// return matches;
	}

	public void numericRangeQuery() throws Exception {
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);
		NumericRangeQuery query = NumericRangeQuery.newIntRange("id", 5, 7, true, true);
		TopDocs matches = searcher.search(query, 10);
		for (ScoreDoc scoreDoc : matches.scoreDocs) {

			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("filename"));
		}
	}

	public void testWildcard() throws Exception {
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);

		Query query = new WildcardQuery(new Term("filename", "*pl*"));
		Query query2 = new WildcardQuery(new Term("filename", "?pl*"));

		TopDocs matches = searcher.search(query, 10);
		System.out.println("total *pl* : " + matches.totalHits);
		for (ScoreDoc sd : matches.scoreDocs) {
			System.out.println(searcher.doc(sd.doc).get("filename"));
		}

		TopDocs matches2 = searcher.search(query2, 10);
		System.out.println("total ?pl* : " + matches2.totalHits);
		for (ScoreDoc sd2 : matches2.scoreDocs) {
			System.out.println(searcher.doc(sd2.doc).get("filename"));
		}
	}

	public void testFuzzy() throws Exception {
		Directory dir = FSDirectory.open(new File("index"));
		IndexSearcher searcher = new IndexSearcher(dir);
		Query query = new FuzzyQuery(new Term("filename", "gpl.txt"));
		TopDocs matches = searcher.search(query, 10);
		System.out.println("fuzzyQuery of \"gpl.txt\" " + matches.totalHits);
		// Document doc = searcher.doc(matches.scoreDocs[0].doc);
		// System.out.println(doc.get("filename"));
		for (ScoreDoc sd : matches.scoreDocs) {
			System.out.println(searcher.doc(sd.doc).get("filename"));
		}
		searcher.close();
		dir.close();
	}

}
