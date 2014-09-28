package com.zhongxun.luceneUtil;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;


public class AdvancedSearcher {
	private Directory directory;
	private IndexReader reader;
	private IndexSearcher searcher;
	private Analyzer analyzer;

	public AdvancedSearcher(String p) throws IOException {
		File file = new File(p);
		directory = FSDirectory.open(file);
		reader = IndexReader.open(directory);
		searcher = new IndexSearcher(reader);
		analyzer = new IKAnalyzer(true);
	}

	public TopDocs searchByTerm(String field, String name, int num) {
		TopDocs tds = null;
		try {
			Query query = new TermQuery(new Term(field, name));
			tds = searcher.search(query, num);
			System.out.println("一共查询了：" + tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("id") + "---->"
						+ doc.get("filename") + "[" + doc.get("path") + "]"
						+ "-->" + doc.get("id") + "," + doc.get("score") + ","
						+ doc.get("date"));
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;
	}

	public TopDocs searchTermByRange(String field, String start, String end,
			int num) {
		TopDocs tds = null;
		try {
			Query query = new TermRangeQuery(field, start, end, true, true);
			tds = searcher.search(query, num);
			System.out.println("一共查询了：" + tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
//				System.out.println(doc.get("id") + "---->" + doc.get("name")
//						+ "[" + doc.get("emails") + "]" + "-->" + doc.get("id")
//						+ "," + doc.get("attachs") + "," + doc.get("date"));
				System.out.println(doc.get("filename"));
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;
	}

	public TopDocs searchByNumericRange(String field, int start, int end, int num) {
		TopDocs tds = null;
		try {
			Query query = NumericRangeQuery.newIntRange(field, start, end,
					true, true);
			tds = searcher.search(query, num);
			System.out.println("一共查询了：" + tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
//				System.out.println(doc.get("id") + "---->" + doc.get("name")
//						+ "[" + doc.get("emails") + "]" + "-->" + doc.get("id")
//						+ "," + doc.get("attachs") + "," + doc.get("date"));
				System.out.println(doc.get("filename"));
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;

	}

	public TopDocs searchByPrefix(String field, String value, int num) {
		TopDocs tds = null;
		try {
			Query query = new PrefixQuery(new Term(field, value));
			tds = searcher.search(query, num);
			System.out.println("一共查询了：" + tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("id") + "---->" + doc.get("name")
						+ "[" + doc.get("emails") + "]" + "-->" + doc.get("id")
						+ "," + doc.get("attachs") + "," + doc.get("date"));
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;
	}

	public TopDocs searchByWildCard(String field, String value, int num) {
		TopDocs tds = null;
		try {
			// 在传入的value中可以使用通配符：?和*号，?表示匹配一个字符*号表示匹配多个字符
			Query query = new WildcardQuery(new Term(field, value));
			tds = searcher.search(query, num);
			System.out.println("一共查询了：" + tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("id") + "---->" + doc.get("name")
						+ "[" + doc.get("emails") + "]" + "-->" + doc.get("id")
						+ "," + doc.get("attachs") + "," + doc.get("date"));
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;

	}

	public TopDocs searchByBoolean(String field1, String field2, String name1,
			String name2, int num) {
		TopDocs tds = null;
		try {
			BooleanQuery query = new BooleanQuery();
			/*
			 * BooleanQuery可以连接多个子查询 Occur.MUST表示必须出现 Occur.SHOULD表示可以出现
			 * Occur.MUST_NOT表示不能出现
			 */
			query.add(new TermQuery(new Term(field1, name1)), Occur.MUST);
			query.add(new TermQuery(new Term(field2, name2)), Occur.MUST);

			tds = searcher.search(query, num);
			System.out.println("一共查询了：" + tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("id") + "---->" + doc.get("name")
						+ "[" + doc.get("emails") + "]" + "-->" + doc.get("id")
						+ "," + doc.get("attachs") + "," + doc.get("date"));
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;

	}

	public TopDocs searchByFuzzy(String field, String name, int num) {
		TopDocs tds = null;
		try {
			FuzzyQuery query = new FuzzyQuery(new Term(field, name), 0.1f, 0);
			System.out.println(query.getPrefixLength());
			System.out.println(query.getMinSimilarity());

			tds = searcher.search(query, num);
			System.out.println("一共查询了：" + tds.totalHits);
			for (ScoreDoc sd : tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("id") + "---->" + doc.get("name")
						+ "[" + doc.get("emails") + "]" + "-->" + doc.get("id")
						+ "," + doc.get("attachs") + "," + doc.get("date"));
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tds;

	}

	public static void main(String args[]) throws IOException {
		AdvancedSearcher as = new AdvancedSearcher("D:/lucene/localindex");
		// as.searchByTerm("content","东", 10);
		 as.searchByNumericRange("date", 200, 1000, 10);
//		 as.searchTermByRange("content", "java", "tomcat", 10);//英文可以
		// as.searchByPrefix("filename", "东", 10);
		// as.searchByWildCard("filename", "*巴", 10);
//		 as.searchByFuzzy("filename", "巴西", 10);
		//as.searchByBoolean("content", "filename", "东", "文化", 10);
	}

}
